/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:16
 * To change this template use File | Settings | File Templates.
 */

jQuery(function () {
        function initRoute() {
            var AppRouter = Backbone.Router.extend({
                routes: {
                    "": "subjects",
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
            var app_router = new AppRouter
            app_router.on('route:subjects', function () {
                Sun.fetch("subjects", null, function (subjects) {
                    Log.i("subjects," + JSON.stringify(subjects))
                    for (var i = 0; i < subjects.length; i++) {
                        var s = subjects.at(i)
                        Sun.fetch("subject", {id: s.get('id')}, function (subject) {
                            if (subject.get('lessons').length > 0) {
                                app_router.navigate("subject/" + subject.id, {trigger: true, replace: true})
                            }
                        })
                    }
                })
            })
            app_router.on('route:subject', function (id) {
                Log.i("subject " + id)
                Sun.fetch("subjects", null, function (subjects) {
                    Sun.fetch("subject", {id: id}, function (subject) {
                        setHeader(
                            new SubjecsHeaderView({
                                model: subjects,
                                currentSubjectId: id
                            }))
                        setBody(
                            new SubjectContentView({
                                model: subject
                            })
                        )
                        reloadPage()
                    })
                })
            })
            app_router.on('route:lesson', function (id) {
                Log.i("lesson " + id)
                Sun.fetch("lesson", {id: id}, function (lesson) {
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
                Log.i("stage " + id)
                Sun.fetch("stage", {id: id}, function (stage) {
                    var userdata = Sun.getuserdata("stage", id)
                    var sections = stage.get("sections").models

                    // Check if stage completed
                    if (currentMode == MODE.NORMAL) {
                        if (sections.length == 0) {
                            Sun.setcomplete('stage', id)
//                            stage.complete(null, function () {
//                                Log.e("no sections in this stage")
//                                app_router.navigate("lesson/" + section.get("lesson_id"), {trigger: true, replace: true})
//                            })
                        }

                        if (!Sun.iscomplete("stage", id)) {
                            var current = userdata['current']
                            if (current == undefined) {
                                app_router.navigate("section/" + sections[0].get('id'), {trigger: true, replace: true})
                            } else {
                                app_router.navigate("section/" + current, {trigger: true, replace: true})
                            }
                        } else {
                            app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                        }
                    } else {
                        // View only mode
                        if (Sun.isviewed("stage", id)) {
                            clearViewed(stage.get('id'))
                            app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                        }
                        var completed = true
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
                    }
                })
            })
            app_router.on('route:section', function (id) {
                Sun.fetch("section", {id: id}, function (section) {
                    var userdata = Sun.getuserdata("section", id)
                    var activities = section.get("activities").models
                    if (currentMode == MODE.NORMAL) {
                        if (activities.length == 0) {
                            Sun.setcomplete('section', id)
                            section.complete(null, function () {
                                Log.e("no activities in this section")
                                app_router.navigate("stage/" + section.get("stage_id"), {trigger: true, replace: true})
                            })
                        }

                        if (!Sun.iscomplete("section", id)) {
                            var current = userdata['current']
                            if (current == undefined) {
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
                Log.i("activity " + id)
                Sun.fetch("activity", {id: id}, function (activity) {
                        var userdata = Sun.getuserdata("activity", id)
                        if (currentMode == MODE.NORMAL) {
                            // activity with problems
                            if (activity.get("type") == 4 || activity.get("type") == 7) {
                                var completed = true
                                for (var i = 0; i < activity.get("problems").length; i++) {
                                    Log.i("problems:" + JSON.stringify(activity.get("problems")))
                                    var problem = activity.get("problems").models[i]
                                    if (!problem.isComplete()) {
                                        app_router.navigate("problem/" + problem.id, {trigger: true, replace: true})
                                        completed = false
                                        break;
                                    }
                                }
                                if (completed) {
//                                    Sun.setcomplete("activity", activity.get("id"))
                                    activity.complete(null, function () {
                                        app_router.navigate("summary/" + activity.get("id"), {trigger: true, replace: true})
                                    })
                                }
                            } else if (activity.get("type") == 2) {
                                var media = Sun.getmedia(activity.get('media_id'))
                                Log.i("video activity type 2," + JSON.stringify(media))
                                setBody(new VideoView({model: activity, media: media}))
                                reloadPage()
                            } else {
                                // TODO other acitivities, like video
                                Log.i("unsupported activity," + JSON.stringify(activity))
                            }
                        } else {
                            // View only mode
                            if (activity.get("type") == 4 || activity.get("type") == 7) {
                                var completed = true
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
                            } else if (activity.get("type") == 2) {
                                Log.i("video activity type 2")
                                setBody(new VideoView({model: activity}))
                                reloadPage()
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
                            Log.i("summary for activity," + JSON.stringify(activity))

                            var correctCount = 0
                            $.each(activity.get("problems").models, function (number, problem) {
                                if (problem.get('userdata')['correct'] == true) {
                                    correctCount++
                                }
                                Log.i("p," + number + "," + JSON.stringify(problem.get("userdata")))
                            })

                            setHeader(new SummaryHeaderView({
                                section: section,
                                stage: stage
                            }))

                            var jump = sample_data.jump_condition
                            if (jump != undefined && jump != "" && jump != null) {
                                if (correctCount >= jump.condition.min && correctCount <= jump.condition.max) {
                                    Log.i("right jump!")
                                    if (jump.to_activity_id == -1) {
                                        endStage(aid, function (id) {
                                            activity.set({next_lesson: id})
                                            setBody(new SummaryView({model: activity}))
                                            reloadPage()
                                        })
                                    } else {
                                        checkin('activity', jump.to_activity_id)
                                        activity.set({next_activity: jump.to_activity_id})
//                                app_router.navigate("activity/" + jump.to_activity_id, {trigger: true, replace: true})
                                        setBody(new SummaryView({model: activity}))
                                        reloadPage()
                                    }
                                } else {
                                    Log.i("don't jump!")
                                    setBody(new SummaryView({model: activity}))
                                    reloadPage()
                                }
                            }
                        })
                    })
                })
            })

            currentSection = undefined
            currentId = undefined
            app_router.bind('all', function (route, section, params) {
                currentSection = section
                if (typeof params != "undefined") {
                    currentId = params[0]
                } else {
                    currentId = undefined
                }
            });

            Logger.hide()
            Backbone.history.start()

            Interfaces.onReady()

            function loadProblem(id) {
                Sun.fetch("problem", {id: id}, function (problem) {
                    Log.i("problem," + problem.get("type"))
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

                                if (problem.get("type") == "0") {
                                    setBody(new SingleChoiceProblemView({model: problem}))
                                    setFooter(new SingleChoiceProblemFooterView({model: problem}))
                                } else if (problem.get("type") == "1") {
                                    setBody(new MultiChoiceProblemView({model: problem}))
                                } else if (problem.get("type") == "2") {
                                    setBody(new SingleFillingProblemView({model: problem}))
                                } else {
                                    Log.i("unsupported problem," + id + "," + problem.get("type"))
                                }
                                reloadPage()
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

            function setFooter(footer) {
                currentPage.footer = footer
            }

            function reloadPage() {
                currentPageView.render()
            }

            completeVideo = function (id) {
                Sun.fetch("activity", {id: id}, function (activity) {
                    if (currentMode == MODE.VIEW_ONLY) {
                        Sun.setviewed('activity', id)
                        app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                    } else {
                        Log.i("complete video," + id)
                        activity.complete(null, function () {
                            app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                        })
                    }
                })
            }

            grading = function (problemId) {
                Sun.fetch("problem", {id: problemId}, function (problem) {
                    if (problem.get("type") == 0 || problem.get("type") == 1) {
                        Log.i("grading problem," + problem.get("id"))
                        var completeOk = true
                        var grading_result = $("#grading_result")

                        for (var i = 0; i < problem.get('choices').length; i++) {
                            choice = problem.get('choices')[i]
                            var choiceId = "#" + choice['id']
                            var answer = $(choiceId)
                            Log.i("problemchoice," + choiceId + ","
                                + answer.attr("id") + ","
                                + answer[0].checked + ","
                                + choice['answer'])
                            if (choice['answer'] == "yes") {
                                if (answer[0].checked == true) {
                                    Log.i(choiceId + " right")
                                } else {
                                    Log.i(choiceId + " wrong")
                                    completeOk = false
                                }
                            } else if (answer[0].checked != true && choice['answer'] == "no") {
                                Log.i(choiceId + " right")
                            } else {
                                Log.i(choiceId + " wrong")
                                completeOk = false
                            }
                        }
                        problem.complete({
                            correct: completeOk
                        }, function () {
                            loadProblem(problem.get('id'))
                        })
                    } else if (problem.get("type") == 2) {
                        Log.i("problem type 2")
                        var answer = $('#answer').val()
                        var correct = false
                        if (answer == problem.get('choices')[0]['display_text']) {
                            correct = true
                        }
                        problem.complete({
                            correct: correct
                        }, function () {
                            loadProblem(problem.get('id'))
                        })
                    } else {
                        // TODO add different problem grading code
                        Log.i("unsupported problem grading")
                    }
                })
            }

            makeSelection = function (id) {
                $('.pcontainer').each(function (i, p) {
                    $(p).removeClass('odd')
                })
                $('#' + id).prop('checked', true)
                $('#pcontainer_' + id).addClass('odd')
            }

            viewStage = function (id) {
                currentMode = MODE.VIEW_ONLY
                app_router.navigate("stage/" + id, {trigger: true, replace: true})
            }

            goUpstairs = function () {
                Log.i('get upstairs with current,' + currentSection + "," + currentId)
                if (currentSection == "lesson") {
                    Sun.fetch('lesson', {id: currentId}, function (lesson) {
                        app_router.navigate("subject/" + lesson.get('subject_id'), {trigger: true, replace: true})
                    })
                } else if (currentSection == "stage") {
                    Sun.fetch('stage', {id: currentId}, function (stage) {
                        app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                    })
                } else if (currentSection == "section") {
                    Sun.fetch('section', {id: currentId}, function (section) {
                        app_router.navigate("stage/" + section.get('stage_id'), {trigger: true, replace: true})
                    })
                } else if (currentSection == "activity") {
                    Sun.fetch('activity', {id: currentId}, function (activity) {
                        Sun.fetch('section', {id: activity.get('section_id')}, function (section) {
                            Sun.fetch('stage', {id: section.get('stage_id')}, function (stage) {
                                app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                            })
                        })
                    })
                } else if (currentSection == "problem") {
                    Sun.fetch('problem', {id: currentId}, function (problem) {
                        Sun.fetch('activity', {id: problem.get('activity_id')}, function (activity) {
                            Sun.fetch('section', {id: activity.get('section_id')}, function (section) {
                                Sun.fetch('stage', {id: section.get('stage_id')}, function (stage) {
                                    app_router.navigate("lesson/" + stage.get('lesson_id'), {trigger: true, replace: true})
                                })
                            })
                        })
                    })
                } else {
                    Log.e('unsupported type to go upstairs,' + currentSection + "," + currentId)
                }
            }
        }

        currentMode = MODE.NORMAL
        currentPage = new Page()
        currentPageView = new PageView({model: currentPage, el: $("body")})

        initRoute()

    }

)
