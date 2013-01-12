jQuery(function($) {
    SubjectView = Backbone.View.extend({
        tagName: "li",
        className: "subject",
        template: Templates.subject,

        render: function() {
            this.$el.html(this.template(this.model.toJSON()))
            return this
        }
    });

    SubjectsView = Backbone.View.extend({
        tagName: "ul",
        className: "nav",

        render : function() {
            this.options.container.empty();
            this.options.container.append(this.el);

            this.collection.each(function(m) {
                this.$el.append(new SubjectView({model: m}).render().el)
            }, this);
            return this;
        }
    });

    LessonView = Backbone.View.extend({
        tagName: "div",
        className: "lesson_box_wrapper",
        template: Templates.lesson,

        render: function() {
            this.$el.html(this.template(this.model.toJSON()))
            return this
        }
    });

    MonthLessonsView = Backbone.View.extend({
        tagName: "div",
        className: "month_lessons",
        template: Templates.month,

        render : function() {
            this.$el.append(this.template({month: this.options.month}));
            this.collection.each(function(m) {
                this.$el.append(new LessonView({model: m}).render().el)
            }, this);
            return this;
        }
    });
});
