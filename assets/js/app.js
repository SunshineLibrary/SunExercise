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
                    console.log("subjects," + JSON.stringify(subjects))
                    app_router.navigate("subject/" + subjects.models[0].id, {trigger: true, replace: true})
                })
            })
            app_router.on('route:subject', function (id) {
                console.log("subject " + id)
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
                console.log("lesson " + id)
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
                console.log("stage " + id)
                Sun.fetch("stage", {id: id}, function (stage) {
                    var userdata = Sun.getuserdata("stage", id)
                    var sections = stage.get("sections").models

                    // Check if stage completed
                    if (currentMode == MODE.NORMAL) {
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
                console.log("activity " + id)
                Sun.fetch("activity", {id: id}, function (activity) {
                        var userdata = Sun.getuserdata("activity", id)
                        if (currentMode == MODE.NORMAL) {
                            // activity with problems
                            if (activity.get("type") == 4 || activity.get("type") == 7) {
                                var completed = true
                                for (var i = 0; i < activity.get("problems").length; i++) {
                                    console.log("problems:" + JSON.stringify(activity.get("problems")))
                                    var problem = activity.get("problems").models[i]
                                    if (!problem.isComplete()) {
                                        app_router.navigate("problem/" + problem.id, {trigger: true, replace: true})
                                        completed = false
                                        break;
                                    }
                                }
                                if (completed) {
                                    Sun.setcomplete("activity", problem.get("activity_id"))
                                    app_router.navigate("summary/" + problem.get("activity_id"), {trigger: true, replace: true})
                                }
                            } else if (activity.get("type") == 2) {
                                console.log("video activity type 2")
                                setBody(new VideoView({model: activity}))
                                reloadPage()
                            } else {
                                // TODO other acitivities, like video
                                console.log("unsupported activity," + JSON.stringify(activity))
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
                                console.log("video activity type 2")
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
                    console.log("summary for activity," + JSON.stringify(activity))

                    var correctCount = 0
                    $.each(activity.get("problems").models, function (number, problem) {
                        if (problem.get('userdata')['correct'] == true) {
                            correctCount++
                        }
                        console.log("p," + number + "," + JSON.stringify(problem.get("userdata")))
                    })

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
            Backbone.history.start()

            function loadProblem(id) {
                Sun.fetch("problem", {id: id}, function (problem) {
                    console.log("problem," + problem.get("type"))

                    if (currentMode == MODE.VIEW_ONLY) {
                        Sun.setviewed('problem', id)
                    }

                    if (problem.get("type") == "0") {
                        setBody(new SingleChoiceProblemView({model: problem}))
                        setFooter(new SingleChoiceProblemFooterView({model: problem}))
                    } else if (problem.get("type") == "1") {
                        setBody(new MultiChoiceProblemView({model: problem}))
                    } else if (problem.get("type") == "2") {
                        setBody(new SingleFillingProblemView({model: problem}))
                    } else {
                        console.log("unsupported problem," + id + "," + problem.get("type"))
                    }
                    reloadPage()
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
                        console.log("complete video," + id)
                        activity.complete(null, function () {
                            app_router.navigate("section/" + activity.get("section_id"), {trigger: true, replace: true})
                        })
                    }
                })
            }

            grading = function (problemId) {
                Sun.fetch("problem", {id: problemId}, function (problem) {
                    if (problem.get("type") == 0 || problem.get("type") == 1) {
                        console.log("grading problem," + problem.get("id"))
                        var completeOk = true
                        var grading_result = $("#grading_result")

                        for (var i = 0; i < problem.get('choices').length; i++) {
                            choice = problem.get('choices')[i]
                            var choiceId = "#" + choice['id']
                            var answer = $(choiceId)
                            console.log("problemchoice," + choiceId + ","
                                + answer.attr("id") + ","
                                + answer[0].checked + ","
                                + choice['answer'])
                            if (choice['answer'] == "yes") {
                                if (answer[0].checked == true) {
                                    console.log(choiceId + " right")
                                } else {
                                    console.log(choiceId + " wrong")
                                    completeOk = false
                                }
                            } else if (answer[0].checked != true && choice['answer'] == "no") {
                                console.log(choiceId + " right")
                            } else {
                                console.log(choiceId + " wrong")
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
                        problem.complete({
                            correct: true
                        }, function () {
                            loadProblem(problem.get('id'))
                        })
                    } else {
                        // TODO add different problem grading code
                        console.log("unsupported problem grading")
                    }
                })
            }

            viewStage = function (id) {
                currentMode = MODE.VIEW_ONLY
                app_router.navigate("stage/" + id, {trigger: true, replace: true})
            }

            Logger.show()
        }

        currentMode = MODE.NORMAL
        currentPage = new Page()
        currentPageView = new PageView({model: currentPage, el: $("body")})

        initRoute()

    }

)
