jQuery(function($) {
    
	var PTYPE = ["选择题", "判断题"]
	Problem = Backbone.Model.extend({
		initialize: function(options) {
			obj = options[0];
            this.set({
                seq: obj.seq,
                body: obj.body,
                type: PTYPE[obj.type]
            })
        }
	});
	
	Choice = Backbone.Model.extend({
		initialize: function(options) {
            this.set({
                body: options.body,
                choice_id: options.id
            })
        }
	});
	
	Choices = Backbone.Collection.extend({
        model: Choice
    });
	
});
