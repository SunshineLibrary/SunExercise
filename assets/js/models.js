/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:44
 * To change this template use File | Settings | File Templates.
 */

jQuery(function () {

    MODE = {
        NORMAL: 'NORMAL_MODE',
        VIEW_ONLY: 'VIEW_MODE'
    }

    MATERIAL_STATE = {
        COMPLETE: 'complete'
    }

    Page = Backbone.Model.extend()

    DAYS_OF_WEEK = ["日", "一", "二", "三", "四", "五", "六"]

    ANSWERS = {}
    for (var i = 0; i < 25; i++) {
        ANSWERS[i] = String.fromCharCode("A".charCodeAt(0) + i)
    }


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
        currentSubjectId: undefined,
        showSubjects: [],
        initialize: function (options) {
            var self = this
            $.each(options, function (index, subject) {
                Sun.fetch('subject', {id: subject['id']}, function (s) {
                    if (s.get('lessons').length > 0) {
                        self.showSubjects.push(s.get('id'))
                    }
                })
            })
        }
    })

    /**
     * Lesson
     */
    Lesson = Backbone.Model.extend({
        initialize: function (options) {
            var date = new Date(Date.parse(options.time))
            /*var stages = new Stages()
             for(var i =0 ;i < options["stages"].length;i++){
             var stage = new Stage(options["stages"][i])
             var userdata = Sun.getuserdata('stage',stage.get('id'))
             stage.set({
             completed: (userdata.current=="EOF")
             })
             stages.add(stage)
             }*/
            var somestages = new Stages(options["stages"])
            this.set({
                parent_id: options['subject_id'],
                day_week: "星期" + DAYS_OF_WEEK[date.getDay()],
                day: date.getDate(),
                month: date.getMonth() + 1,
                year: date.getYear() + 1900,
                stages: somestages,
                userdata: Sun.getuserdata("lesson", options.id)
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
                parent_id: options['lesson_id'],
                sections: new Sections(options.sections),
                userdata: Sun.getuserdata("stage", options.id)
            })
        },
        isComplete: function () {
            var completed = true
            for (var i = 0; i < this.get('sections').models.length; i++) {
                var section = this.get('sections').models[i]
                if (!Sun.iscomplete("section", section.get('id'))) {
                    completed = false
                    break
                }
            }
            return completed
        },
        complete: function (options, callback) {
            if (this.isComplete()) {
                Sun.setcomplete('stage', this.get('id'))
                if (callback != undefined) {
                    eval(callback)(options)
                }
            } else {
                for (var i = 0; i < this.get('sections').length; i++) {
                    var section = this.get('sections').models[i]
                    if (!Sun.iscomplete("section", section.get('id'))) {
                        var userdata = Sun.getuserdata('stage', this.get('id'))
                        userdata['current'] = section.get('id')
                        Sun.setuserdata('stage', this.get('id'), userdata)
                        if (callback != undefined) {
                            eval(callback)(options)
                        }
                        break
                    }
                }
            }
        }
    })
    Stages = Backbone.Collection.extend({model: Stage})

    /**
     * Section
     */
    Section = Backbone.Model.extend({
        initialize: function (options) {
            this.set({
                parent_id: options['stage_id'],
                activities: new Activities(options.activities)
            })
        },
        isComplete: function () {
            var completed = true
            for (var i = 0; i < this.get('activities').length; i++) {
                var activity = this.get('activities').models[i]
                if (!Sun.iscomplete("activity", activity.get('id'))) {
                    completed = false
                    break
                }
            }
            return completed
        },
        completeRate: function () {
            var completeCount = 0
            for (var i = 0; i < this.get('activities').length; i++) {
                var activity = this.get('activities').models[i]
                if (Sun.iscomplete("activity", activity.get('id'))) {
                    completeCount++
                }
            }
            var rate = completeCount / this.get('activities').length
            Log.i('complete rate:' + rate)
            return rate
        },
        complete: function (options, callback) {
            if (this.isComplete()) {
                Sun.setcomplete('section', this.get('id'))
                Sun.fetch("stage", {id: this.get('stage_id')}, function (stage) {
//                    console.log("DEBUG," + JSON.stringify(options) + "," + JSON.stringify(stage))
                    stage.complete(options, function () {
                        if (callback != undefined) {
                            eval(callback)(options)
                        }
                    })
                })
            } else {
                for (var i = 0; i < this.get('activities').length; i++) {
                    var activity = this.get('activities').models[i]
                    if (!Sun.iscomplete("activity", activity.get('id'))) {
                        var userdata = Sun.getuserdata('section', this.get('id'))
                        userdata['current'] = activity.get('id')
                        Sun.setuserdata('section', this.get('id'), userdata)
                        if (callback != undefined) {
                            eval(callback)(options)
                        }
                        break
                    }
                }
            }
        }
    })
    Sections = Backbone.Collection.extend({model: Section})

    /**
     * Activity
     */
    Activity = Backbone.Model.extend({
        initialize: function (options) {
            if (options["problems"] != undefined) {
                this.set({
                    parent_id: options['section_id'],
                    problems: new Problems(options.problems)
                })
            }
        },
        isComplete: function () {
            var completed = true
            for (var i = 0; i < this.get('problems').length; i++) {
                var problem = this.get('problems').models[i]
                if (!Sun.iscomplete("problem", problem.get('id'))) {
                    completed = false
                    break
                }
            }
            return completed
        },
        complete: function (options, callback) {
            var type = this.get('type')
            var completed = false
            if (type == 4 || type == 7) {
                // activity with problems
                // If all problem has completed, complete this activity
                if (this.isComplete()) {
//                    console.log("completeACTIVITY," + this.get('id'))
                    Sun.setcomplete('activity', this.get('id'))
                    completed = true
                }
            } else if (type == 2) {
                // video activity
                Sun.setcomplete('activity', this.get('id'))
                completed = true
            }
            if (completed) {
                Sun.fetch('section', {id: this.get('parent_id')}, function (section) {
                    section.complete(options, callback)
                })
            }
        }
    })
    Activities = Backbone.Collection.extend({model: Activity})

    /**
     * Problem
     */
    Problem = Backbone.Model.extend({
        initialize: function (options) {
            this.set({
                userdata: Sun.getuserdata("problem", options.id),
                parent_id: options['activity_id']
            })
            if (this.get('choices') != undefined) {
                var type = options['type']
                if (type == '2') {
                    var correct_answers = options.choices[0]['display_text']
                    this.set({
                        correct_answers: correct_answers
                    })
                } else if (type == '0' || type == '1') {
                    var correct_answers = []
                    for (var i = 0; i < this.get('choices').length; i++) {
                        var choice = this.get('choices')[i]
                        if (choice['answer'] == "yes") {
                            correct_answers.push(ANSWERS[i])
                        }
                    }
                    this.set({
                        correct_answers: correct_answers
                    })
                }
            }
        },
        isComplete: function () {
            var userdata = Sun.getuserdata('problem', this.get('id'))
            return userdata['current'] == 'EOF'
        },
        complete: function (options, callback) {
            Sun.setcomplete('problem', this.get('id'), options)
            Sun.fetch('activity', {id: this.get('parent_id')}, function (activity) {
                activity.complete(options)
            })
            if (callback != undefined) {
                eval(callback)()
            }
        }
    })

    Problems = Backbone.Collection.extend({model: Problem})

    Media = Backbone.Model.extend({})

    Medias = Backbone.Collection.extend({model: Media})

    UserData = Backbone.Model.extend({})

    Error = Backbone.Model.extend({})

})