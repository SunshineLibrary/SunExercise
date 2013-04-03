/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-29
 * Time: AM11:48
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
        app_router = new AppRouter
        app_router.on('route:subjects', function () {
            Log.i("subjects")
            Sun.fetch("subjects", undefined, function (subjects) {
                if (!(subjects.length > 0)) {
                    Log.i("Cannot found subjects")
                    showError("no subjects")
                } else {
                    app_router.navigate("subject/" + subjects.models[0].id, {trigger: true, replace: true})
                }
            })
        })
        app_router.on('route:subject', function (id) {
            Log.i("subject," + id)
            Sun.fetch("subjects", undefined, function (subjects) {
                Sun.fetch("subject", {id: id}, function (subject) {
                    Log.i("subject," + JSON.stringify(subject))
                    currentPage.header = new SubjecsHeaderView({model: subjects})
                    currentPage.content = new SubjectContentView({model: subject})
                    currentPageView.render()
                })
            })
        })
        app_router.on('route:lesson', function (id) {
            Log.i("lesson," + id)
            Sun.fetch("lesson", {id: id}, function (lesson) {
                Log.i("lesson," + JSON.stringify(lesson))
                currentPage.header = new LessonHeaderView({model: lesson})
                currentPage.content = new LessonContentView({model: lesson})
                currentPageView.render()
            })
        })
        app_router.on('route:stage', function (id) {
            Sun.fetch("stage", {id: id}, function (stage) {
                var userdata = Sun.getuserdata("stage", id)
                var sections = stage.get("sections").models
                if (sections.length == 0) {
                    showError("no sections for this stage," + id)
                } else {
                    Log.i("stage," + id + "," + JSON.stringify(userdata))
                    if (currentMode == MODE.NORMAL) {
                        // MODE NORMAL
                        var current = userdata['current']
                        if (current == undefined) {
                            // user entered this stage first time
                            app_router.navigate("section/" + sections[0].get('id'), {trigger: true, replace: true})
                        } else if (current == "EOF") {
                            // user has completed this stage
                            // TODO prompt a dialog to ask enter VIEW_ONLY mode
                            Log.i("this stage is completed")
                            showError("stage has complete, enter view only mode???")
                        } else {
                            // navigate to this activity
                            Log.i("navigate to activity," + current)
                            app_router.navigate("activity/" + current, {trigger: true, replace: true})
                        }
                    } else if (currentMode == MODE.VIEW_ONLY) {
                        // MODE VIEW_ONLY
                        var current = userdata['view_current']
                        app_router.navigate("activity/" + current, {trigger: true, replace: true})
                    }
                }
            })
        })
        app_router.on('route:section', function (id) {
            Sun.fetch("section", {id: id}, function (section) {
                var userdata = Sun.getuserdata("section", id)
                var activities = section.get("activities").models
                if (activities.length == 0) {
                    showError("no activity for this section," + id)
                } else {
                    if (currentMode == MODE.NORMAL) {
                        var current = userdata['current']
                        if (current == undefined) {
                            app_router.navigate("activity/" + activities[0].get('id'), {trigger: true, replace: true})
                        } else if (current == "EOF") {
                            Log.i("this section is completed")
                            showError("section has complete, enter view only mode???")
                        } else {
                            app_router.navigate("activity/" + current, {trigger: true, replace: true})
                        }
                    } else if (currentMode == MODE.VIEW_ONLY) {
                        // MODE VIEW_ONLY
                        var current = userdata['view_current']
                        app_router.navigate("activity/" + activities[0].get('id'), {trigger: true, replace: true})
                    }
                    Log.i("section," + id)
                }
            })
        })
        app_router.on('route:activity', function (id) {
            Sun.fetch("activity", {id: id}, function (activity) {
                    if (currentMode == MODE.NORMAL) {
                        var userdata = Sun.getuserdata("activity", id)
                        Log.i("activity," + id)
                        var current = userdata['current']
                        if (current == undefined) {
                            showActivity(activity)
                        } else if (current == "EOF") {
                            Log.i("this activity is completed")
                            showError("activity has complete, enter view only mode???")
                        } else {
                            // Only activity with problems has current pointer
                            app_router.navigate("problem/" + current, {trigger: true, replace: true})
                        }
                    } else if (currentMode == MODE.VIEW_ONLY) {
                    }
                }
            )
        })
        app_router.on('route:problem', function (id) {
            Log.i("problem," + id)
            Sun.fetch("problem", {id: id}, function (problem) {
                showProblem(problem)
            })
        })
        app_router.on('route:summary', function (id) {
            Log.i("summary," + id)
        })
        Backbone.history.start()

        showError = function (msg) {
            currentPage.content = new ErrorView({
                model: new Error({msg: msg})
            })
            currentPageView.render()
        }

        showActivity = function (activity) {
            if (activity.get("type") == 4 || activity.get("type") == 7) {
                // activity with problems
                for (var i = 0; i < activity.get("problems").length; i++) {
                    Log.i("problems:" + JSON.stringify(activity.get("problems")))
                    var problem = activity.get("problems").models[i]
                    if (!problem.isComplete()) {
                        app_router.navigate("problem/" + problem.id, {trigger: true, replace: true})
                        break;
                    }
                }
            } else if (activity.get("type") == 2) {
                Log.i("video activity type 2")
            } else {
                // TODO other acitivities, like video
                Log.i("unsupported activity," + JSON.stringify(activity))
            }
        }

        showProblem = function (problem) {
            console.log("problem," + problem.get("type"))
            if (problem.get("type") == "0") {
                currentPage.content = new SingleChoiceProblemView({model: problem})
                currentPageView.render()
            } else if (problem.get("type") == "1") {
                currentPage.content = new MultiChoiceProblemView({model: problem})
                currentPageView.render()
            } else if (problem.get("type") == "2") {
                currentPage.content = new SingleFillingProblemView({model: problem})
                currentPageView.render()
            } else {
                showError("unsupported problem," + problem.get('id'))
            }
        }


    }

    Logger.show()

    currentMode = MODE.NORMAL
    currentPage = new Page()
    currentPageView = new SimplePageView({model: currentPage})

    initRoute()

})