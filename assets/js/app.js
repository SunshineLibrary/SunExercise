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
                userdata = Sun.getuserdata("stage", id)
                if (userdata.current == undefined) {
                    console.log("never entered this stage")
                    for (var i = 0; i < stage.get("sections").length; i++) {
                        var section = stage.get("sections").models[i]
                        if (!section.isComplete()) {
                            app_router.navigate("section/" + section.id, {trigger: true, replace: true})
                            break;
                        }
                    }
                } else {
                    console.log("resume activity," + userdata.current)
                    app_router.navigate("activity/" + userdata.current, {trigger: true, replace: true})
                }
            })
        })
        app_router.on('route:section', function (id) {
            Sun.fetch("section", {id: id}, function (section) {
                console.log("call!!!!"+JSON)

                for (var i = 0; i < section.get("activities").length; i++) {
                    var activity = section.get("activities").models[i]
                    var completed = true
                    if (!activity.isComplete()) {
                        app_router.navigate("activity/" + activity.id, {trigger: true, replace: true})
                        completed = false
                        break
                    }
                    if (completed) {

                        app_router.navigate("stage/" + section.get("stage_id"), {trigger: true, replace: true})
                    }
                }
            })
        })
        app_router.on('route:activity', function (id) {
            console.log("activity " + id)
            Sun.fetch("activity", {id: id}, function (activity) {
                // activity with problems
                if (activity.get("type") == 4) {
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
                        app_router.navigate("summary/" + problem.get("activity_id"), {trigger: true, replace: true})
                    }
                } else {
                    // TODO other acitivities, like video
                    console.log("unsupported activity," + JSON.stringify(activity))
                }
            })
        })
        app_router.on('route:problem', function (id) {
            loadProblem(id)
        })
        app_router.on('route:summary', function (aid) {
            Sun.fetch("activity", {id: aid}, function (activity) {
                console.log("summary for activity," + JSON.stringify(activity))
                setBody(new SummaryView({model: activity}))
                $.each(activity.get("problems").models, function (number, problem) {
                    console.log("p," + number + "," + JSON.stringify(problem.get("userdata")))
                })
                reloadPage()
            })
        })
        Backbone.history.start()

        function loadProblem(id) {
            Sun.fetch("problem", {id: id}, function (problem) {
                console.log("problem," + problem.get("type"))

                if (problem.get("type") == "0") {
                    setBody(new SingleChoiceProblemView({model: problem}))
                    setFooter(new SingleChoiceProblemFooterView({model: problem}))
                } else if (problem.get("type") == "2") {
                    setBody(new SingleFillingProblemView({model: problem}))
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

        grading = function (problemId) {
            Sun.fetch("problem", {id: problemId}, function (problem) {
                if (problem.get("type") == 0) {
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
                            }
                        } else if (answer[0].checked != true && choice['answer'] == "no") {
                            console.log(choiceId + " right")
                        } else {
                            console.log(choiceId + " wrong")
                            completeOk = false
                        }
                    }

                    var user_data = Sun.getuserdata("problem", problem.get("id"))
                    user_data["completed"] = true
                    user_data["correct"] = completeOk
                    Sun.setuserdata("problem", problem.get('id'), user_data, function () {
                        checkin("activity", problem.get('activity_id'))
                        problem.set("userdata", user_data)
                        loadProblem(problem.get('id'))
                    })
                } else if (problem.get("type") == 2) {
                    var user_data = Sun.getuserdata("problem", problem.get("id"))
                    user_data["completed"] = true
                    user_data["correct"] = true
                    Sun.setuserdata("problem", problem.get('id'), user_data, function () {
                        checkin("activity", problem.get('activity_id'))
                        problem.set("userdata", user_data)
                        loadProblem(problem.get('id'))
                    })
                } else {
                    // TODO add different problem grading code
                    console.log("not supported problem grading")
                }

            })
        }

        nextMaterial = function (type, id) {
            if (type == "problem") {
                Sun.fetch("problem", {id: id}, function (problem) {
                    app_router.navigate("activity/" + problem.get('activity_id'), {trigger: true, replace: true})
                })
            } else {
                console.log("unsupported material")
            }
        }
    }

    currentPage = new Page()
    currentPageView = new PageView({model: currentPage, el: $("body")})

    initRoute()

})
