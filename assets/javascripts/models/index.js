jQuery(function($) {
    Subject = Backbone.Model.extend();

    Subjects = Backbone.Collection.extend({
        model: Subject
    });

    var DAYS_OF_WEEK = ["日", "一", "二", "三", "四", "五", "六"]
    Lesson = Backbone.Model.extend({
        initialize: function(options) {
            var date = new Date();
			date.setTime(options.time);
            this.set({
                day_week: "星期" + DAYS_OF_WEEK[date.getDay()],
                day: date.getDate(),
                month: date.getMonth() + 1,
                year: date.getYear() + 1900
            })
        }
    });

    Lessons = Backbone.Collection.extend({
        model: Lesson,
    });
});
