jQuery(function($){
	showProblem = function(str){
		var myObject = JSON.parse(str);
		var problems = new Problems(myObject.problems);
		var activity = new Activity(myObject.activity);
		
		var problem = problems.at(0);
		var choices = new Choices(problem.get("choices"));
		
		var container = $("#content");
		var buttonCon = $("#button");
		
		container.empty();
		
		var problemView = new ProblemView({
			model : problem
		});
		var choicesView = new ChoicesView({
			collection : choices
		});
		var imageView = new ImageView({
			collection : problems,
			model : activity
		});
		
		container.append(problemView.render().el);
		container.append(choicesView.render().el);
		buttonCon.html(imageView.render().el);
	}
	
	
	Sun.fetchProblem("showProblem");
});