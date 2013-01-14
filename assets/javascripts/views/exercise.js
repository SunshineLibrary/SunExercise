jQuery(function($) {
		
	ProblemView= Backbone.View.extend({
		tagName: "div",
        className: "problem",
		template: Templates.problem,
		
		render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        }
	});


});
