jQuery(function($) {
	
	var answer = null;
	var choices = null;
	var dialogBodys = null;
	var toNextProblem = false;
	var choiceIndexes=["A", "B", "C", "D", "E", "F", "G"];
	
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
			choices = this.collection.toJSON();
			
            this.collection.each(function(m) {
                this.$el.append(new ChoiceView({model: m}).render().el)
            }, this);
            return this;
        }
	});
	
	getDialog = function(type){
		if(dialogBodys == null) return;
		
		for(var i in dialogBodys){
			if(dialogBodys[i].type == type){
				var count = dialogBodys[i].count;
				var tar = Math.floor(Math.random()*count);
		
				return dialogBodys[i].array[tar].body;
			}
		}		
		return null;
		
	}
	
	showResult = function(isCorrect){
		var footer = $("#footer");
		var dialogSpan = $("#dialog");
		var tipSpan = $("#answer_tip");
		var button = $("#button_next");
		
		if(isCorrect){
			footer.css("background-color", "#B4EEB4");
			dialogSpan.text(getDialog(0));
		}else{
			footer.css("background-color", "#FFC1C1");
			dialogSpan.text(getDialog(1));
			tipSpan.text("正确答案 " + answer);
			answer = null;
		}
		
		//next problem
		button.text("下一题");
		toNextProblem = true;
	};
	
	FooterView = Backbone.View.extend({
		template: Templates.footer,
		
		events : {
            "click" : "onClick"
        },
		
		render : function(){
            this.options.container.empty();
            this.options.container.append(this.el);
			this.options.container.removeAttr("style");
			dialogBodys = this.collection.toJSON();
			toNextProblem=false;
			
			this.$el.html(this.template({body:"", buttonText:"确定"}));
            return this;
        },
		
		//event handlers
        onClick : function(ev){
            var isCorrect = null;
			
			if(toNextProblem){
				//to next problem
				toNextProblem=false;
				location.hash="problem/2";
				return;
			}
			
			var checkedId = $("[name=optionsRadios]:radio:checked").attr("id");
			if(checkedId == null || choices == null) return;
			
			for(var i in choices){
				if(choices[i].answer == 1){
					answer = choiceIndexes[i];
					if(choices[i].id == checkedId){
						isCorrect = true;
					}else{
						isCorrect = false;
					}
					
				}
			}
			if(isCorrect != null) showResult(isCorrect);
			
			//console.debug("answer is:" + isCorrect);
        },
	});
	
	

});
