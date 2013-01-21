jQuery(function($) {
	
	showProblem = function(str) {
        var problem = new Problem(JSON.parse(str));
        var containerProblem = $("#problem");
        var problemView = new ProblemView({
			model: problem,
			container: containerProblem
		});
        problemView.render();
		
		var choices = new Choices(problem.toJSON().choices);
		var containerChoices = $("#choices");
		var choicesView = new ChoicesView({
			collection: choices,
			container: containerChoices
		});
		choicesView.render();
    }
	
	showFooter = function(str) {
		var dialogs = new Dialogs(JSON.parse(str));
		var container = $("#footer");
		var footerView = new FooterView({
			collection: dialogs,
			container: container
		});
		footerView.render();
	}

    IndexPage = Backbone.Router.extend({
        routes: {
			"problem/:seq": "showProblem"
        },

        initPage: function() {
            Sun.fetchProblem(showProblem, 1);
			Sun.fetchDialogs(showFooter);
        },
		
		showProblem: function(seq) {
            Sun.fetchProblem(showProblem, seq);
			Sun.fetchDialogs(showFooter);
        }
    });

    indexPage = new IndexPage();
    indexPage.initPage();

    Backbone.history.start();
});
