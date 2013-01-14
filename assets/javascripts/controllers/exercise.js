jQuery(function($) {
	
	showProblem = function(str) {
        var problem = new Problem(JSON.parse(str));
        var container = $("#problem");
        var problemView = new ProblemView({
			model: problem,
			container: container
		});
        problemView.render();
    }
	
	showChoices = function(str) {
		var choices = new Choices(JSON.parse(str));
		var container = $("#choices");
		var choicesView = new ChoicesView({
			collection: choices,
			container: container
		});
		choicesView.render();
	}

    IndexPage = Backbone.Router.extend({
        routes: {
			"problem/:seq": "showProblem"
        },

        initPage: function() {
            Sun.fetchProblem("showProblem", 1);
			Sun.fetchChoices("showChoices", 1);
        },
		
		showProblem: function(seq) {
            Sun.fetchProblem("showProblem", seq);
			Sun.fetchChoices("showChoices", seq);
        }
    });

    indexPage = new IndexPage();
    indexPage.initPage();

    Backbone.history.start();
});
