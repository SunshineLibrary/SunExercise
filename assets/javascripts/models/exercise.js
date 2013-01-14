jQuery(function($) {
    
	var PTYPE = ["选择题", "判断题"]
	Problem = Backbone.Model.extend();
	
	Choice = Backbone.Model.extend();
	
	Choices = Backbone.Collection.extend({
        model: Choice
    });
	
});
