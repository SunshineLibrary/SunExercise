/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:44
 * To change this template use File | Settings | File Templates.
 */
/**
 * Created with JetBrains WebStorm.
 * User: fxp
 * Date: 13-3-18
 * Time: AM11:15
 * To change this template use File | Settings | File Templates.
 */

jQuery(function () {
    Page = Backbone.Model.extend()

    DAYS_OF_WEEK = ["日", "一", "二", "三", "四", "五", "六"]

    /**
     * Subject
     */
    Subject = Backbone.Model.extend({
        initialize: function (options) {
            this.lessons = new Lessons(options.lessons)
            var monthLessons = this.lessons.groupBy(function (lesson) {
                return lesson.get("year") * 100 + lesson.get("month")
            })
            this.set({monthLessons: monthLessons})
        }
    })
    Subjects = Backbone.Collection.extend({
        model: Subject,
        currentSubjectId: undefined
    })

    /**
     * Lesson
     */
    Lesson = Backbone.Model.extend({
        initialize: function (options) {
            var date = new Date()
            date = new Date(Date.parse(options.time))
            this.set({
                day_week: "星期" + DAYS_OF_WEEK[date.getDay()],
                day: date.getDate(),
                month: date.getMonth() + 1,
                year: date.getYear() + 1900
            })
        }
    })
    Lessons = Backbone.Collection.extend({
        model: Lesson
    })

    /**
     * Stage
     */
    Stage = Backbone.Model.extend({
        initialize: function (options) {
            this.set({sections: new Sections(options.sections)})
        }
    })
    Stages = Backbone.Collection.extend({model: Stage})

    /**
     * Section
     */
    Section = Backbone.Model.extend({
        initialize: function (options) {
            this.set({activities: new Activities(options.activities)})
        },
        isComplete: function () {
            return isComplete(this)
        }
    })
    Sections = Backbone.Collection.extend({model: Section})

    /**
     * Activity
     */
    Activity = Backbone.Model.extend({
        initialize: function (options) {
            if (options["problems"] != undefined) {
                this.set({problems: new Problems(options.problems)})
            }
        },
        isComplete: function () {
            return isComplete(this)
        }
    })
    Activities = Backbone.Collection.extend({model: Activity})

    /**
     * Problem
     */
    Problem = Backbone.Model.extend({
        initialize: function (options) {
            fillUp(options, this)
        },
        isComplete: function () {
            return isComplete(this)
        }
    })
    Problems = Backbone.Collection.extend({model: Problem})

    function fillUp(options, target) {
        if (options["user_data"] == undefined) {
            target.set("user_data", [])
        } else {
            target.user_data = JSON.parse(options["user_data"])
        }
    }

    function isComplete(target) {
        var user_data = Sun.getuserdata(target.get("id"))
        var completed = user_data["completed"]
        return completed == true;
    }

    UserData = Backbone.Model.extend({})

})