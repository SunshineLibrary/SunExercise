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
        var container = $("#lessons");
        container.empty();

        if (!lessons.isEmpty()) {
	        lessonGroups = lessons.groupBy(function(lesson) {
	            return lesson.get("year") * 100 + lesson.get("month");
	        });
	
	        var keys = Object.keys(lessonGroups);
	
	        for (var i = keys.length - 1; i-- ; i>=0) {
	            var key = keys[i]
	            var month_lessons = new Lessons(lessonGroups[key]);
	            var lessonsView = new MonthLessonsView({
	                collection: month_lessons,
	                month: key % 100
	            });
	            container.append(lessonsView.render().el);
	        }
        }
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
