jQuery(function($) {
    
	var PTYPE = ["选择题", "判断题"]
	
	Problem = Backbone.Model.extend({
		initialize: function(options) {
            this.set({
                type: PTYPE[options.type]
            })
        }
	});
	
	/* Problem = Backbone.Model.extend({
		initialize: function(options) {
			obj = options[0];
            this.set({
                seq: obj.seq,
                body: obj.body,
                type: PTYPE[obj.type]
            })
        }
	}); */
	
	Choice = Backbone.Model.extend();
	
	Choices = Backbone.Collection.extend({
        model: Choice
    });
	
	Dialog = Backbone.Model.extend();
	
	Dialogs = Backbone.Collection.extend({
        model: Dialog
    });
	
});
