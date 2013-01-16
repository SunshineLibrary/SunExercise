jQuery(function($) {
    var strcir = $("#stage_circle_template").html();
    var strline= $("#hline_template").html();
    Templates = {
        stage: _.template(strcir),
        hline: _.template(strline)
    };
});
