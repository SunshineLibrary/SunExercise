jQuery(function($){
	showResult = function(str){
		var problems = new Problems(JSON.parse(str).problems);
		var activity = new Activity(JSON.parse(str).activity);
		var activityName = activity.get("name");
		alert("activityName:"+activityName);
		
		jumpConditions = new JumpConditions(activity.get("jump_condition"));
		var user_score = activity.get("user_score");
		var to_activity_id;
		jumpConditions.each(function(jumpCondition){
			var low = jumpCondition.get("low");
			var high = jumpCondition.get("high");
			if(low <= user_score && high > user_score){
				to_activity_id = jumpCondition.get("to_activity_id");
				alert("你的成绩是"+user_score+"即将跳转到"+to_activity_id);
			}
		});
		
		var dialog = new Dialog();
		dialog.set("activityName",activityName);
		dialog.set("nextActivity",to_activity_id);
		
		var numbers_container = $("#numbers");
		for(var i=0;i<8;i++){
			numbers_container.append("<td>"+i+"</td>");
		}
		
		var wrapper_container = $("#wrapper");
		var resultsView = new ResultsView({
			collection : problems
		});
		resultsView.render();
		
		
		var dialog_container = $("content");
		var dialogView = new DialogView({
			model : dialog
		});
		dialog_container.html(dialogView.render().el);
	}
	
	Sun.fetchResult("showResult");
});
