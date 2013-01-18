jQuery(function($) {
    StagesView = Backbone.View.extend({
        circleTemplate: Templates.stage,
        hlineTemplate: Templates.hline,

        render: function() {
            this.options.container.empty();
            var data = this.model.toJSON();
            var width = 100+(data.length-1)*100+(2*data.length-3)*80;
            var html = this.hlineTemplate({width:width});
            this.options.container.append(html);
            for(var k in data){
                this.options.container.append(this.circleTemplate(data[k]));
            }
            return this;
        }
    });
});
