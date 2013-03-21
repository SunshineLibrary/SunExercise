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
                "problem/:id": "problem"
            }
        })

        // Instantiate the router
        var app_router = new AppRouter
        app_router.on('route:subjects', function () {
            console.log("subjects")
            Sun.fetch("subjects", function (subjects) {
                app_router.navigate("subject/" + subjects.models[0].id, {trigger: true, replace: true})
            })
        })
        app_router.on('route:subject', function (id) {
            console.log("subject " + id)
            Sun.fetch("subjects", function (subjects) {
                Sun.fetch("subject", function (subject) {
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
                }, {id: id})
            })
        })
        app_router.on('route:lesson', function (id) {
            console.log("lesson " + id)
            Sun.fetch("lesson", function (lesson) {
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
            }, {id: id})
        })
        app_router.on('route:stage', function (id) {
            console.log("stage " + id)
            Sun.fetch("stage", function (stage) {
                for (var i = 0; i < stage.get("sections").length; i++) {
                    var section = stage.get("sections").models[i]
                    if (!section.isComplete()) {
                        app_router.navigate("section/" + section.id, {trigger: true, replace: true})
                        break;
                    }
                }
            }, {id: id})
        })
        app_router.on('route:section', function (id) {
            Sun.fetch("section", function (section) {
                for (var i = 0; i < section.get("activities").length; i++) {
                    var activity = section.get("activities").models[i]
                    if (!activity.isComplete()) {
                        app_router.navigate("activity/" + activity.id, {trigger: true, replace: true})
                        break
                    }
                }
            }, {id: id})
        })
        app_router.on('route:activity', function (id) {
            console.log("activity " + id)
            Sun.fetch("activity", function (activity) {
                // activity with problems
                if (activity.get("type") == 4) {
                    for (var i = 0; i < activity.get("problems").length; i++) {
                        console.log("problems:" + JSON.stringify(activity.get("problems")))
                        var problem = activity.get("problems").models[i]
                        if (!problem.isComplete()) {
                            app_router.navigate("problem/" + problem.id, {trigger: true, replace: true})
                            break;
                        }
                    }
                } else {
                    // TODO other acitivities, like video
                    console.log("unsupported activity," + JSON.stringify(activity))
                }
            }, {id: id})
        })
        app_router.on('route:problem', function (id) {
            Sun.fetch("problem", function (problem) {
                if (problem.get("type") == 0) {
                    setBody(new SingleChoiceProblemView({model: problem}))
                    setFooter(new SingleChoiceProblemFooterView({model: problem}))
                }
                reloadPage()
            }, {id: id})
        })

        Backbone.history.start()

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
            Sun.fetch("problem", function (problem) {
                if (problem.get("type") == 0) {
                    console.log("grading problem," + problem.get("id"))
                    var completeOk = true
                    var user_data = problem.get("user_data")
                    answer_btn = $("#submit_answer")
                    _.each(problem.get('choices'), function (choice) {
                        var choiceId = "#" + choice['id']
                        answer = $(choiceId)
                        if (answer.attr('checked') == "checked" && choice['answer'] == "yes") {
                            console.log(choiceId + " right")
                        } else if (answer.attr('checked') != "checked" && choice['answer'] == "no") {
                            console.log(choiceId + " right")
                        } else {
                            console.log(choiceId + " wrong")
                            completeOk = false
                        }
                    })

                    console.log("grading result:" + completeOk)

                    // After set user data, set button heading to next problem
                    user_data.push({"completed": true})
                    Sun.setuserdata(function () {
                        console.log("complete set userdata")
                        answer_btn.html("下一道题")
                        answer_btn.attr("onclick", "")
                        answer_btn.click(function () {
                            console.log("click answer, next problem")
                            app_router.navigate("activity/" + problem.get('activity_id'), {trigger: true, replace: true})
                        })
                    }, "problem", problem.get('id'), user_data)
                } else {
                    // TODO add different problem grading code
                    console.log("not supported problem grading")
                }

            }, {id: problemId})
        }
    }

    currentPage = new Page()
    currentPageView = new PageView({model: currentPage, el: $("body")})

    initRoute()
})
