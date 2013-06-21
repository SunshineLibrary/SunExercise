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
            lesson: _.template($("#th_lesson").html()),
            problem: _.template($("#th_problem").html()),
            summary: _.template($("#th_summary").html())
        },
        Content: {
            subject: _.template($("#tc_subject").html()),
            month: _.template($("#tc_month").html()),
            lesson: _.template($("#tc_lesson").html()),
            stages: _.template($("#tc_stages").html()),
            activity: _.template($("#tc_activity").html()),
            section: _.template($("#tc_section").html()),
            summary: _.template($("#tc_summary").html()),
            problem_sc: _.template($("#tc_problem_singlechoice").html()),
            problem_sf: _.template($("#tc_problem_singlefilling").html()),
            problem_mc: _.template($("#tc_problem_multichoice").html()),
            problem_img: _.template($("#tc_problem_img").html()),
            multiMedia: _.template($("#tc_multiMedia").html())
        },
        Footer: {
            lesson: _.template($("#tf_lesson").html()),
            subject: _.template($("#tf_subject").html()),
            problem_sc: _.template($("#tf_problem_singlechoice").html())
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
//            if (this.model.footer != undefined) {
//                $("#footer").html(this.model.footer.render().el)
//            }
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
    ProblemHeaderView = Backbone.View.extend({
        template: Templates.Header.problem,

        render: function () {
            this.el = this.template({
                section: this.options['section'],
                stage: this.options['stage']
            })
            return this
        }
    })


    var RenderProblem = function(problemView){
        var problem = problemView.model;
        var activity = problemView.options['activity'];
        var userdata = problem.get('userdata');
        var gradingResult = GRADING_RESULT(activity, userdata, problem);
        this.el = problemView.template({
            target: problem,
            problemId: problem.get('id'),
            content: problem.get('body'),
            choices: problem.get('choices'),
            userdata: userdata,
            media: problem.get('media'),
            activity: activity,
            activity_type: activity.get('type'),
            gradingResult: gradingResult
        });
        return this
    };

    SingleChoiceProblemView = Backbone.View.extend({
        template: Templates.Content.problem_sc,
        render: function () {
            return RenderProblem(this);
        }
    });

    MultiChoiceProblemView = Backbone.View.extend({
        template: Templates.Content.problem_mc,
        render: function () {
            return RenderProblem(this);
        }
    })

    SingleFillingProblemView = Backbone.View.extend({
        template: Templates.Content.problem_sf,
        render: function () {
            return RenderProblem(this);
        }
    })

    ImageChoiceProblemView = Backbone.View.extend({
        template: Templates.Content.problem_img,
        render: function () {
            return RenderProblem(this);
        }
    })

    SummaryHeaderView = Backbone.View.extend({
        template: Templates.Header.problem,
        render: function () {
            this.el = this.template({
                section: this.options['section'],
                stage: this.options['stage']
            })
            return this
        }
    })

    SummaryView = Backbone.View.extend({
        template: Templates.Content.summary,
        render: function () {
            this.el = this.template({target: this.model})
            return this
        }
    })

    /**
     * Activity
     */
    MultiMediaView = Backbone.View.extend({
        template: Templates.Content.multiMedia,
        media: undefined,
        render: function () {
            this.el = this.template({target: this.model, media: this.options['media']})
            return this
        }
    })

    MultiMediaHeaderView = Backbone.View.extend({
        template: Templates.Header.problem,
        render: function () {
            this.el = this.template({
                activity: this.options['activity'],
                section: this.options['section'],
                stage: this.options['stage']
            })
            return this
        }
    })
})