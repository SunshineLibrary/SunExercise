/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:16
 * To change this template use File | Settings | File Templates.
 */

jQuery(function () {
        choiceNum = 0
        var AppRouter = Backbone.Router.extend({
            routes: {
                "": "subjects",
                "subjects": "subjects",
                "subject/:id": "subject",
                "lesson/:id": "lesson",
                "stage/:id": "stage",
                "section/:id": "section",
                "activity/:id": "activity",
                "problem/:id": "problem",
                "summary/:aid": "summary"
            }
        })

        // Instantiate the router
        var app_router = new AppRouter;

        ProblemController = {
            currentProblem: undefined,
            currentActivity: undefined,
            type: undefined,
            choices: {},
            userChoices: [],
            userChoice: undefined,
            completeOk: false,
            submitBtn: undefined,
            setup: function (problem, activity) {
                this.currentProblem = problem;
                this.currentActivity = activity;
                this.type = problem.get('type');
                for (var i = 0; i < problem.get('choices').length; i++) {
                    var value = problem.get('choices')[i];
                    var el = document.getElementById('choice_' + value['id']);
                    this.submitBtn = document.getElementById('submit_answer');
                    this.choices[value['id']] = {
                        value: value,
                        correct: value['answer'],
                        el: el
                    };
                }
            },
            setUserChoice: function (choiceId, callback) {
                // Only for single choice problem
                this.completeOk = this.choices[choiceId].correct == 'yes';
                var oldChoice = this.userChoice;
                if (oldChoice != choiceId) {
                    this.choices[choiceId].chosen = false;
                    if (oldChoice != undefined) {
                        this.choices[oldChoice].chosen = false;
                    }
                    this.userChoice = choiceId;
                    eval(callback)(this.choices, choiceId, oldChoice);
                }
            },
            clean: function () {
                this.choices = {};
                this.userChoices = [];
                this.userChoice = undefined;
                this.currentProblem = undefined;
                this.currentActivity = undefined;
                this.type = undefined;
                this.submitBtn = undefined;
                this.completeOk = false;
            }
        };

        loadProblem = function (id) {
            Sun.fetch("problem", {id: id}, function (problem) {
                ProblemController.clean();
                currentMaterial = "problem"
                Sun.fetch("activity", {id: problem.get('activity_id')}, function (activity) {
                    Sun.fetch("section", {id: activity.get('section_id')}, function (section) {
                        Sun.fetch("stage", {id: section.get('stage_id')}, function (stage) {
                            if (currentMode == MODE.VIEW_ONLY) {
                                Sun.setviewed('problem', id)
                            }

                            setHeader(new ProblemHeaderView({
                                problem: problem,
                                activity: activity,
                                section: section,
                                stage: stage
                            }))

                            var pType = problem.get("type");
                            var userdata = Sun.getuserdata("problem", id);
                            if (pType == "0") {
                                setBody(new SingleChoiceProblemView(
                                    {model: problem, activity: activity, userdata: userdata}));
                            } else if (pType == "1") {
                                setBody(new MultiChoiceProblemView(
                                    {model: problem, activity: activity, userdata: userdata}));
                            } else if (pType == "2") {
                                setBody(new SingleFillingProblemView(
                                    {model: problem, activity: activity, userdata: userdata}));
                            } else if (pType == "3") {
                                // Single choice only
                                var choices = problem.get('choices');
                                for (var i = 0; i < choices.length; i++) {
                                    choices[i].media = Sun.getmedia(choices[i].media_id);
                                    console.log("Choice:" + problem.get('choices')[i].media.get('path'));
                                }
                                setBody(new ImageChoiceProblemView(
                                    {model: problem, activity: activity, userdata: userdata}
                                ))
                            } else {
                                Log.i("unsupported problem," + id + "," + problem.get("type"))
                            }
                            reloadPage();
                            // Render all math equations
                            MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathcontent"]);
                            ProblemController.setup(problem, activity);
                        })
                    })
                })
            })
        }


        function setHeader(header) {
            currentPage.header = header
        }

        function setBody(body) {
            currentPage.body = body
        }

        function reloadPage() {
            currentPageView.render();
            Interfaces.stopAudio();
        }


        function initRoute() {
            app_router.on('route:subjects', function () {
                Sun.fetch("subjects", null, function (subjects) {
                    currentMaterial = "subjects";
                    if (subjects.length == 0) {
                        console.log('no subject found');
                    } else {
                        var s = (typeof currentSubject == "undefined") ? subjects.showSubjects[0] : currentSubject.id;
                        Sun.fetch("subject", {id: s}, function (subject) {
                            app_router.navigate("subject/" + subject.id, {trigger: true, replace: true})
                        })
                    }
                })
            })
            app_router.on('route:subject', function (id) {
                Sun.fetch("subjects", null, function (subjects) {
                    Sun.fetch("subject", {id: id}, function (subject) {
                        currentSubject = subject
                        currentMaterial = "subject"
                        setHeader(
                            new SubjecsHeaderView({
                                model: subjects,
                                currentSubjectId: id
                            })
                        );
                        setBody(
                            new SubjectContentView({
                                model: subject
                            })
                        );
                        reloadPage();

                        $.each(subject.get('lessons').models, function (index, lesson) {
//                            changeDownloadBtn(lesson.get('id'), (lesson.get('download_finish') == '1'))
                            changeDownloadBtn(lesson.get('id'), lesson.get('download_finish'))
                        })
                    }, true)
                })
            })
            app_router.on('route:lesson', function (id) {
                Sun.fetch("lesson", {id: id}, function (lesson) {
                    currentLesson = lesson
                    currentMaterial = "lesson"
                    var userdata = Sun.getuserdata('lesson', id);
                    userdata['entered'] = "true";
                    Sun.setuserdata("lesson", id, userdata);

                    setHeader(
                        new LessonHeaderView({
                            model: lesson
                        }))
                    setBody(
                        new LessonContentView({
                            model: lesson
                        })
                    )
                    reloadPage()
                })
            })

            app_router.on('route:stage', function (id) {
                Sun.fetch("stage", {id: id}, function (stage) {
                    var userdata = Sun.getuserdata("stage", id)
                    var sections = stage.get("sections").models
                    var completed = (userdata.current == 'EOF')
                    currentMaterial = "stage"

                    // Check if stage completed
                    if (!completed) {
                        currentMode = MODE.NORMAL
                        if (sections.length == 0) {
                            console.log('empty stage,' + id)
                            Sun.setcomplete('stage', id)
                        }

                        if (!Sun.iscomplete("stage", id)) {
                            var current = userdata['current']
                            if (typeof current == "undefined") {
                                app_router.navigate("section/" + sections[0].get('id'), {trigger: true, replace: true})
                            } else {
                                app_router.navigate("section/" + current, {trigger: true, replace: true})
                            }
                        } else {
                            app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                        }
                    } else if (currentMode == MODE.VIEW_ONLY) {
                        // View only mode
                        if (Sun.isviewed("stage", id)) {
                            clearViewed(stage.get('id'))
                            app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                        }
                        completed = true
                        for (var i = 0; i < sections.length; i++) {
                            var section = sections[i]
                            if (!Sun.isviewed('section', section.get('id'))) {
                                app_router.navigate("section/" + section.id, {trigger: true, replace: true})
                                completed = false
                                break;
                            }
                        }
                        if (completed) {
                            Sun.setviewed("stage", stage.get('id'))
                            app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                        }
                    } else {
                        Log.i("should not be here when complete and not VIEW_ONLY mode")
                        app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                    }
                })
            })

            app_router.on('route:section', function (id) {
                Sun.fetch("section", {id: id}, function (section) {
                    currentMaterial = "section"
                    var userdata = Sun.getuserdata("section", id)
                    var activities = section.get("activities").models
                    if (currentMode == MODE.NORMAL) {
                        if (activities.length == 0) {
                            console.log('empty section,' + id)
                            Sun.setcomplete('section', id)
                            section.complete(null, function () {
                                //console.log('no activities in this section')
                                app_router.navigate("stage/" + section.get("stage_id"), {trigger: true, replace: true})
                            })
                        }

                        if (!Sun.iscomplete("section", id)) {
                            var current = userdata['current']
                            if (typeof current == "undefined") {
                                app_router.navigate("activity/" + activities[0].get('id'), {trigger: true, replace: true})
                            } else {
                                app_router.navigate("activity/" + current, {trigger: true, replace: true})
                            }
                        } else {
                            app_router.navigate("stage/" + section.get("stage_id"), {trigger: true, replace: true})
                        }
                    } else {
                        // View only mode
                        var completed = true
                        for (var i = 0; i < activities.length; i++) {
                            var activity = activities[i]
                            if (!Sun.isviewed('activity', activity.get('id'))) {
                                app_router.navigate("activity/" + activity.id, {trigger: true, replace: true})
                                completed = false
                                break;
                            }
                        }
                        if (completed) {
                            Sun.setviewed("section", section.get('id'))
                            app_router.navigate("stage/" + section.get('stage_id'), {trigger: true, replace: true})
                        }
                    }
                })
            })

            app_router.on('route:activity', function (id) {
                console.log("activity:" + id);
                Sun.fetch("activity", {id: id}, function (activity) {
                        currentMaterial = "activity";
                        var activity_type = activity.get("type");
                        if (currentMode == MODE.NORMAL) {
                            // activity with problems
                            if (activity_type == 4 || activity_type == 41 || activity_type == 42 || activity_type == 7) {
                                var completed = true;
                                for (var i = 0; i < activity.get("problems").length; i++) {
                                    var problem = activity.get("problems").models[i]
                                    if (!problem.isComplete()) {
                                        app_router.navigate("problem/" + problem.id, {trigger: true, replace: true})
                                        completed = false
                                        break;
                                    }
                                }
                                if (completed) {
                                    activity.complete(null, function () {
                                        app_router.navigate("summary/" + activity.get("id"), {trigger: true, replace: true})
                                    })
                                }
                            } else if (activity.get("type") == 2 || activity.get('type') == 6) {
                                if (activity.get('type') == 6 && Sun.iscomplete('activity', id)) {
                                    activity.complete(null, function () {
                                        app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                                    })
                                } else {
                                    var media = Sun.getmedia(activity.get('media_id'))
                                    Sun.fetch("section", {id: activity.get('section_id')}, function (section) {
                                        Sun.fetch("stage", {id: section.get('stage_id')}, function (stage) {
                                            setHeader(new MultiMediaHeaderView({
                                                activity: activity,
                                                section: section,
                                                stage: stage
                                            }))
                                            setBody(new MultiMediaView({model: activity, media: media}))
                                            reloadPage()
                                            if (activity.get('type') == 2) {
                                                Interfaces.deletePlayLog()
                                            }
                                        })
                                    })
                                }
                            } else {
                                console.log("unsupported activity")
                            }
                        } else {
                            // View only mode
                            if (activity_type == 4 || activity_type == 41 || activity_type == 42 || activity_type == 7) {
                                // Activity with problems
                                var completed = true;
                                for (var i = 0; i < activity.get("problems").length; i++) {
                                    var problem = activity.get("problems").models[i]
                                    if (!Sun.isviewed('problem', problem.get('id'))) {
                                        app_router.navigate("problem/" + problem.id, {trigger: true, replace: true})
                                        completed = false
                                        break;
                                    }
                                }
                                if (completed) {
                                    Sun.setviewed("activity", activity.get('id'))
                                    app_router.navigate("section/" + activity.get('section_id'), {trigger: true, replace: true})
                                }
                            } else if (activity_type == 2 || activity_type == 6) {
                                // PDF and video
                                var media = Sun.getmedia(activity.get('media_id'))
                                Sun.fetch("section", {id: activity.get('section_id')}, function (section) {
                                    Sun.fetch("stage", {id: section.get('stage_id')}, function (stage) {
                                        setHeader(new MultiMediaHeaderView({
                                            activity: activity,
                                            section: section,
                                            stage: stage
                                        }))
                                        setBody(new MultiMediaView({model: activity, media: media}))
                                        reloadPage()
                                        if (activity.get('type') == 2) {
                                            Interfaces.deletePlayLog()
                                        }
                                    })
                                })
                            }
                        }
                    }
                )
            })

            app_router.on('route:problem', function (id) {
                loadProblem(id)
            })
            app_router.on('route:summary', function (aid) {
                Sun.fetch("activity", {id: aid}, function (activity) {
                    Sun.fetch("section", {id: activity.get('section_id')}, function (section) {
                        Sun.fetch("stage", {id: section.get('stage_id')}, function (stage) {
                            currentMaterial = "summary"

                            var correctCount = 0
                            $.each(activity.get("problems").models, function (number, problem) {
                                if (problem.get('userdata')['correct'] == true) {
                                    correctCount++
                                }
                            })

                            setHeader(new SummaryHeaderView({
                                section: section,
                                stage: stage
                            }))

                            var jumpText = activity.get('jump_condition')
                            if (typeof jumpText == "undefined" || jumpText == "") {
                                jumpText = "[]"
                            }
                            var showNext = function (currentActivity) {
                                if (currentActivity.get('type') == 42) {
                                    app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                                    console.log("no summary");
                                } else {
                                    setBody(new SummaryView({model: currentActivity}));
                                    reloadPage();
                                    console.log("summary");
                                }
                            }
                            var jumps = JSON.parse(jumpText);
                            if (typeof jumps == "undefined") {
                                showNext(activity);
                            } else {
                                // TODO change to multiple jump condition support
                                var jumpAway = false;
                                for (var i = 0; i < jumps.length; i++) {
                                    var jump = jumps[i];
                                    if (correctCount >= jump.condition.min && correctCount <= jump.condition.max) {
                                        jumpAway = true;
                                        if (jump.to_activity_id == -1) {
                                            console.log("endStage");
                                            endStage(aid, function (id) {
                                                activity.set({next_lesson: id});
                                                showNext(activity);
                                            })
                                        } else {
                                            checkin(activity, jump.to_activity_id);
                                            activity.set({next_activity: jump.to_activity_id});
                                            showNext(activity);
                                        }
                                        break;
                                    }
                                }
                                if (!jumpAway) {
                                    console.log("don't jump!");
                                    showNext(activity);
                                }
                            }
                        })
                    })
                })
            })

            currentSubject = undefined;
            currentLesson = undefined;
            currentMaterial = undefined;

            Backbone.history.start();

            viewStage = function (id) {
                currentMode = MODE.VIEW_ONLY
                app_router.navigate("stage/" + id, {trigger: true, replace: true})
            }


            var DEEP_MATERIALS = ["stage", "section", "activity", "problem", "summary"];

            goUpstairs = function () {
//                console.log('get upstairs with current,' + currentMaterial)
                if (currentMaterial == "lesson" && (typeof currentSubject != "undefined")) {
                    console.log('back from lesson');
                    app_router.navigate("subject/" + currentSubject.get('id'), {trigger: true, replace: true})
                } else if (DEEP_MATERIALS.indexOf(currentMaterial) != -1 && (typeof currentLesson != "undefined")) {
                    console.log('back to lesson');
                    app_router.navigate("lesson/" + currentLesson.get('id'), {trigger: true, replace: true})
                } else {
                    console.log('unsupported type to go upstairs,' + currentMaterial);
                    app_router.navigate("subjects", {trigger: true, replace: true});
                }
            }
        }

        showWaiting = function () {
            $("#submit_answer").hide()
        }

        completeMultiMedia = function (id) {
            Sun.fetch("activity", {id: id}, function (activity) {
                if (currentMode == MODE.VIEW_ONLY) {
                    Sun.setviewed('activity', id)
                    app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                } else {
                    console.log("complete multiMedia," + id)
                    activity.complete(null, function () {
                        if (activity.get('type') == 2) {
                            Interfaces.deletePlayLog()
                        }
                        app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                    })
                }
            })
        }

        /**
         * New choice selection and grading, under test
         * @param self
         */
        choice_select = function (self) {
            var cid = self.getAttribute('cid');
            self.className = 'thumbnail choice_selected';
            ProblemController.setUserChoice(cid, function (choices, newChoiceId, oldChoice) {
                if (oldChoice != undefined) {
                    choices[oldChoice].el.className = 'thumbnail';
                }
                ProblemController.submitBtn.style.display = "block";
            });
        };

        gradingChoice = function () {
            var choices = [];
            choices.push(ProblemController.userChoice);
            var currentProblem = ProblemController.currentProblem;
            console.log("complete problem, correct: " + ProblemController.completeOk + "," + choices);
            ProblemController.currentProblem.complete({
                correct: ProblemController.completeOk,
                checked: choices
            }, function () {
                var currentActivity = ProblemController.currentActivity;
                var activity_type = currentActivity.get('type');
                var activity_id = currentActivity.id;
                if (activity_type == 7 || activity_type == 42) {
                    app_router.navigate("activity/" + activity_id, {trigger: true, replace: true})
                } else {
                    loadProblem(currentProblem.get('id'))
                }
                choiceNum = 0
            })
        }

        grading = function (problemId) {
            Sun.fetch("problem", {id: problemId}, function (problem) {
                Sun.fetch("activity", {id: problem.get('activity_id')}, function (activity) {
                    if (problem.get("type") == 0 || problem.get("type") == 1) {
                        var completeOk = true
                        var checked = []

                        for (var i = 0; i < problem.get('choices').length; i++) {
                            var choice = problem.get('choices')[i]
                            var choiceId = "#" + choice['id']
                            var answer = $(choiceId)
                            var userChecked = answer.prop("checked")
                            var shouldCheck = (choice['answer'] == "yes")
                            if (userChecked == true) {
                                checked.push(choice['id'])
                            }
                            if (shouldCheck ^ userChecked) {
                                completeOk = false
                            }
                        }

                        problem.complete({
                            correct: completeOk,
                            checked: checked
                        }, function () {
                            var activity_type = activity.get('type')
                            if (activity_type == 7 || activity_type == 42) {
                                choiceNum = 0
                                app_router.navigate("activity/" + activity.id, {trigger: true, replace: true})
                            } else {
                                choiceNum = 0
                                loadProblem(problem.get('id'))
                            }
                        })
                    } else if (problem.get("type") == 2) {
                        //console.log("problem type 2")
                        var answer = $('#answer').val()
                        var correct = false
                        if (answer == problem.get('choices')[0]['display_text']) {
                            correct = true
                        }
                        problem.complete({
                            correct: correct,
                            answer: answer
                        }, function () {
                            var activity_type = activity.get('type')
                            if (activity_type == 7 || activity_type == 42) {
                                $('#answer').val("");
                                app_router.navigate("activity/" + activity.id, {trigger: true, replace: true})
                            } else {
                                loadProblem(problem.get('id'));
                            }
                        })
                    }
                })
            })
        }


        makeSelection = function (id, excluded) {
            if (excluded) {
                $('.pcontainer').each(function (i, p) {
                    $(p).removeClass('odd')
                })
            }
            var choice = $('#' + id)
            var checked = choice.prop('checked')
            if (excluded) {
                choice.prop('checked', true)
                $('#pcontainer_' + id).addClass('odd')
                choiceNum = 1
            } else {
                choice.prop('checked', !checked)
                if (!checked) {
                    $('#pcontainer_' + id).addClass('odd')
                    ++choiceNum
                } else {
                    $('#pcontainer_' + id).removeClass('odd')
                    --choiceNum
                }
            }
            activeSubmitBtn(function () {
                judgeChoiceNum()
            })
        }

        activeSubmitBtn = function (judge) {
            $('#submit_answer').show();
        }

        deactiveSubmitBtn = function () {
            $('#submit_answer').addClass('hide')
        }

        judgeChoiceNum = function () {
            if (choiceNum <= 0) {
                deactiveSubmitBtn()
            } else {
                grading(problemId)
            }
        }

        judgeContent = function () {
            var answer = $('#answer').val()
            if (answer == "") {
                deactiveSubmitBtn()
            } else {
                grading(problemId)
            }
        }

        playPdf = function (path, id) {
            $('#nextButton').show();
            Interfaces.openThirdPartyApp(path, id, "pdf");
        }

        playVideo = function (path, id) {
            $('#nextButton').show();
        }

        playAudio = function (mediaPath) {
            console.log('playAudio,' + mediaPath);
            $("#problem_audio_img").attr("src", "img/audio_dynamic.gif");
            if (WEB_DEV_MODE) {
                alert("play audio");
            } else {
                Interfaces.openThirdPartyApp(mediaPath, undefined, "audio");
            }
        }

        onAudioStop = function (id) {
            $("#problem_audio_img").attr("src", "img/audio_static.png");
        }

        waitingDiag = $('#waitingDiag');

        currentMode = MODE.NORMAL;
        currentPage = new Page();
        currentPageView = new PageView({model: currentPage, el: $("body")});

        initRoute()

        Interfaces.onReady();

    }


)
