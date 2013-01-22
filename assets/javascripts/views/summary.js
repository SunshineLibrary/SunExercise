jQuery(function($){
	DialogBadView = Backbone.View.extend({
		
	});
	
	DialogGoodView = Backbone.View.extend({
		
	});
	
	DialogView = Backbone.View.extend({
		tagName : "div",
		id : "dialog",
		template : Templates.dialog_good_template,
		
		render : function(){
			this.$el.html(this.template(this.model.toJSON()));
			return this;
		}
	});
	
	ResultItemView = Backbone.View.extend({
		tagName : "td",
		template : Templates.result_item,
		
		render : function(){
			this.$el.html(this.template(this.model.toJSON()));
			return this;
		}
	});
	
	ResultsView = Backbone.View.extend({
		el : $("#results"),
		
		render : function(){
			this.collection.each(function(resultItem){
				this.$el.append(new ResultItemView({model:resultItem}).render().el);
			},this);
			return this;
		}
	});
});


















