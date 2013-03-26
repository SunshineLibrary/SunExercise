/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-26
 * Time: PM3:14
 * To change this template use File | Settings | File Templates.
 */

jQuery(function () {
//    var AppRouter = Backbone.Router.extend({
//        routes: {
//            "": "subjects",
//            "subject/:id": "subject",
//            "lesson/:id": "lesson",
//            "stage/:id": "stage",
//            "section/:id": "section",
//            "activity/:id": "activity",
//            "problem/:id": "problem",
//            "summary/:aid": "summary"
//        }
//    })

    // Instantiate the router
//    var app_router = new AppRouter
//    app_router.on('route:subjects', function () {
//        console.log("subjects")
//        Sun.fetch('subjects', null, function (subjects, options) {
//            console.log("got subjects," + JSON.stringify(subjects))
//            $("#content").html("")
//            $.each(subjects.models, function (number, subject) {
//                console.log("subject," + JSON.stringify(subject))
//                $("#content").append("<div>" + subject.get('id') + "</a>")
//            })
//        })
//    })
//    app_router.on('route:subject', function (id) {
//        console.log("subject," + id)
//    })
//    app_router.on('route:lesson', function (id) {
//        console.log("lesson," + id)
//    })
//    app_router.on('route:stage', function (id) {
//        console.log("stage," + id)
//    })
//    app_router.on('route:section', function (id) {
//        console.log("section," + id)
//    })
//    app_router.on('route:activity', function (id) {
//        console.log("activity," + id)
//    })
//    app_router.on('route:problem', function (id) {
//        console.log("problem," + id)
//    })
//    app_router.on('route:summary', function (id) {
//        console.log("summary," + id)
//    })
//    Backbone.history.start()

    // All subjects
    Sun.fetch('subjects', null, function (subjects, options) {
        Sun.fetch('lesson', {id: subjects.models[0].id}, lesson)
    })

    lesson = function (lesson) {
        console.log('lesson,' + lesson)
        Sun.fetch('stage', lesson.stages)
    }

})