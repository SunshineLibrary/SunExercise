jQuery(function($) {
    showSubjects = function(str) {
        var subjects = new Subjects(JSON.parse(str));
        var container = $("#subjects");
        var subjectsView = new SubjectsView({
            collection: subjects,
            container: container
        });
        subjectsView.render();
    }

    showLessons = function(str) {
        var lessons = new Lessons(JSON.parse(str));
        var container = $(".container");
        var lessonsView = new LessonsView({
            collection: lessons,
            container: container,
        });
        lessonsView.render();
    }
	
    IndexPage = Backbone.Router.extend({
        routes: {
            "subject/:id":  "showSubject",
            "lesson/:id":   "showLesson"
        },

        initPage: function() {
            Sun.fetchSubjects("showSubjects");
        },

        showSubject: function(id) {
            Sun.fetchLessons("showLessons", id);
        },

        showLesson: function(id) {
            alert("Requested to show lesson " + id);
        }
    });

    indexPage = new IndexPage();
    indexPage.initPage();

    Backbone.history.start();
});
