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
            this.options.container.append(this.el);//这里你为什么不用html()呢？this.options.container.html(this.el);
            this.collection.each(function(m) {
                this.$el.append(new SubjectView({model: m}).render().el)
            }, this);
            return this;
        }
    });

    LessonView = Backbone.View.extend({
        tagName: "div",
        className: "month_lessons",
        template: Templates.lesson,

        render: function() {
            this.$el.html(this.template(this.model.toJSON()))
            return this
        }
    });

    LessonsView = Backbone.View.extend({
        tagName: "div",
		id: "lessons",
		
        render : function() {
            this.options.container.empty();
            this.options.container.append(this.el);
            this.collection.each(function(m) {
                this.$el.append(new LessonView({model: m}).render().el)
            }, this);
            return this;
        }
    });
});
