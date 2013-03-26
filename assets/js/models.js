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
            var lessons = new Lessons(options.lessons)
            var monthLessons = lessons.groupBy(function (lesson) {
                return lesson.get("year") * 100 + lesson.get("month")
            })
            this.set({
                lessons: lessons,
                monthLessons: monthLessons
            })
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
            this.set({
                sections: new Sections(options.sections),
                userdata: Sun.getuserdata("stage", options.id)
            })
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
            this.set("userdata", Sun.getuserdata("problem", options.id))
            if (this.get('choices') != undefined) {
                var ANSWERS = {0: "A", 1: "B", 2: "C", 3: "D", 4: "E", 5: "F"}
                var correct_answers = []
                for (var i = 0; i < this.get('choices').length; i++) {
                    var choice = this.get('choices')[i]
                    if (choice['answer'] == "yes") {
                        correct_answers.push(ANSWERS[i])
                    }
                }
                this.correct_answers = correct_answers
            }
        },
        isComplete: function () {
            return isComplete(this)
        }
    })
    Problems = Backbone.Collection.extend({model: Problem})


    function isComplete(target) {
        var user_data = Sun.getuserdata("problem", target.get("id"))
        var completed = user_data["completed"]
        return completed == true;
    }

    UserData = Backbone.Model.extend({})

})