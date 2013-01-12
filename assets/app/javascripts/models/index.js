jQuery(function($) {
    Subject = Backbone.Model.extend();

    Subjects = Backbone.Collection.extend({
        model: Subject
    });

    Lesson = Backbone.Model.extend();

    Lessons = Backbone.Collection.extend({
        model: Lesson
    });
});
