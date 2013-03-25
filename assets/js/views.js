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
 * Time: PM2:47
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {
    Templates = {
        Header: {
            subjects: _.template($("#th_subjects").html()),
            lesson: _.template($("#th_lesson").html())
        },
        Content: {
            subject: _.template($("#tc_subject").html()),
            month: _.template($("#tc_month").html()),
            lesson: _.template($("#tc_lesson").html()),
            stages: _.template($("#tc_stages").html()),
            activity: _.template($("#tc_activity").html()),
            section: _.template($("#tc_section").html()),
            problem_sc: _.template($("#tc_problem_singlechoice").html()),
            problem_sf: _.template($("#tc_problem_singlefilling").html()),
            problem_mc: _.template($("#tc_problem_multichoice").html()),
            video: _.template($("#tc_video").html())
        },
        Footer: {
            lesson: _.template($("#tf_lesson").html()),
            subject: _.template($("#tf_subject").html()),
            problem_sc: _.template($("#tf_problem_singlechoice").html()),
        }
    }

    PageView = Backbone.View.extend({
        render: function () {
            if (this.model.header != undefined) {
                $("#header").html(this.model.header.render().el)
            }
            if (this.model.body != undefined) {
                $("#content").html(this.model.body.render().el)
            }
            if (this.model.footer != undefined) {
                $("#footer").html(this.model.footer.render().el)
            }
            return this
        }
    })

    /**
     * Lesson
     */
    LessonHeaderView = Backbone.View.extend({
        template: Templates.Header.lesson,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

    LessonContentView = Backbone.View.extend({
        template: Templates.Content.lesson,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

    /**
     * Subject
     */
    SubjecsHeaderView = Backbone.View.extend({
        template: Templates.Header.subjects,
        initialize: function (options) {
            this.currentSubjectId = options.currentSubjectId
        },
        render: function () {
            this.el = this.template({target: this.model, currentSubjectId: this.currentSubjectId})
            return this
        }
    })

    SubjectContentView = Backbone.View.extend({
        template: Templates.Content.subject,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

    /**
     * Problem
     */
    SingleChoiceProblemView = Backbone.View.extend({
        template: Templates.Content.problem_sc,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

    SingleChoiceProblemFooterView = Backbone.View.extend({
        template: Templates.Footer.problem_sc,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

    SingleFillingProblemView = Backbone.View.extend({
        template: Templates.Content.problem_sf,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

})