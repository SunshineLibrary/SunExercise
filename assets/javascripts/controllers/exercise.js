jQuery(function($) {
	
	showProblem = function(str) {
        var problem = new Problem(JSON.parse(str));
        var container = $("#problem");
        var problemView = new ProblemView({
			model: problem,
			el: $("#problem")
		});
        problemView.render();
    }


    IndexPage = Backbone.Router.extend({
        routes: {
			"problem/:seq": "showProblem"
        },

        initPage: function() {
			//showProblem();
            Sun.fetchProblem("showProblem");
        }
    });

    indexPage = new IndexPage();
    indexPage.initPage();

    Backbone.history.start();
});
