jQuery(function($) {
    SubjectView = Backbone.View.extend({
        tagName: "li",
        className: "subject",
        template: Templates.subject,
		
        render: function() {
            this.$el.html(this.template(this.model.toJSON()))
            return this
        },
    });

    SubjectsView = Backbone.View.extend({
        tagName: "ul",
        className: "nav",

        render : function() {
//            alert("in");
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
		
		/*event : {
			"click " : "getProblems"
		},
		
		getProblems : function(){
			android.getProblems();//Ȼ���ȥ����problems
		}
		*/
		
        render: function() {
			var progress = this.model.get("user_progress");
			var stage_count = this.model.get("stage_count");
			var stage_percentage = this.model.get("stage_percentage");
			//alert("stage_count="+stage_count+"  stage_percentage"+stage_percentage);
			
			if(stage_count>0){
				var progress = (stage_percentage/stage_count)*100;
				this.model.set("progress",progress);
//				alert("progress:" + progress);
			}
			
			var name;
			var temp_name = this.model.get("name");
			if(temp_name.length > 15){
				var sub_name = temp_name.substring(0,15);
				name = sub_name+"...";
				this.model.set("name",name);
			}
			
		
            this.$el.html(this.template(this.model.toJSON()))
            
			if(progress == ""){
				$(this.el).find(".img-circle").removeClass("hidden");
			}
			
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
