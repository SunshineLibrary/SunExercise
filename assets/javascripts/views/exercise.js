jQuery(function($) {
		
	ProblemView = Backbone.View.extend({
		template: Templates.problem,
		render: function() {
			this.options.container.empty();
			this.options.container.append(this.el);
			
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        }
	});
	
	ChoiceView = Backbone.View.extend({
		template: Templates.choice,
		render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        }
	});
	
	ChoicesView = Backbone.View.extend({
	
		render: function() {
			this.options.container.empty();
            this.options.container.append(this.el);
			
            this.collection.each(function(m) {
                this.$el.append(new ChoiceView({model: m}).render().el)
            }, this);
            return this;
        }
	});

});
