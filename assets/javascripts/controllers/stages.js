jQuery(function($) {
    var classNames=["nth1","nth2","nth3"];
    var refineData=function(data){
        if(data[0]){	
            if(!data[0].name)data[0].name="基础巩固";
            if(!data[0].body)data[0].body="30-60分钟复习新学的知识点";
        }
        if(data[1]){
            if(!data[1].name)data[1].name="融会贯通";
            if(!data[1].body)data[1].body="你解锁了扩展练习题";
        }
        return data;
    }
    var moveToStep=function(step){
        var index = step-1;
        for(var i=0;i< classNames.length;i++){
            if(i != index){
                $("#intro-circles").removeClass(classNames[i]);
            }else{
                $("#intro-circles").addClass(classNames[i]);
            }
        }
        $(".circle.active").removeClass("active");
        $(".circle."+classNames[index]).addClass("active");

    }
    var switchToStage = function(id, step) {
        if(step==null){
            step = 1;
        }
        if(step>classNames.length)step=classNames.length;
        var container = $("#intro-circles");
        var currentId = container.attr("data_id");
        if(currentId==id){
            var currentStep = container.attr("data_step");
            if(currentStep != step){
                moveToStep(step);
                container.attr("data_step", step);
            }
        }else{
            Sun.fetchStages(function(str){
                var stages = new Stages(refineData(JSON.parse(str)));
                var currentId = container.attr("data_id");
                var stagesView = new StagesView({
                    model: stages,
                    container: container
                });
                stagesView.render();
                container.attr("data_id", id);
                moveToStep(step);
                container.attr("data_step", step);
            }, id);
        }
        $("#intro-btn-select").attr("href","#section/"+step);
    }

    var StagesPage = Backbone.Router.extend({
        routes: {
            "stage/:id(/:step)":  "routeSwitchToStage",
            "section/:id": "clickSection"
        },

        initPage: function() {

        },

        routeSwitchToStage: function(id, step) {
            switchToStage(id, step);
        },

        clickSection: function(){
            var container = $("#intro-circles");
            var currentId = container.attr("data_id");
            var currentStep = container.attr("data_step");
            alert("click on Stage #"+currentId+", Section #"+currentStep);
        }
    });

    var page = new StagesPage();
    page.initPage();

    Backbone.history.start();
});
