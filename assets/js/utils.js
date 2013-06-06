/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-26
 * Time: PM4:12
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {
    checkin = function (type, id) {
        console.log("checkin," + type + "," + id)
        Sun.fetch("activity", {id: id}, function (activity) {
            Sun.fetch("section", {id: activity.get("section_id")}, function (section) {
                Sun.fetch("stage", {id: section.get("stage_id")}, function (stage) {
                    var userdata = Sun.getuserdata("stage", stage.get("id"))
                    userdata["current"] = section.get('id')
                    Sun.setuserdata("stage", stage.get("id"), userdata)
                })
                var userdata = Sun.getuserdata("section", section.get("id"))
                userdata["current"] = activity.get('id')
                Sun.setuserdata("section", section.get("id"), userdata)
            })
            /*var userdata = Sun.getuserdata("activity", activity.get("id"))
            userdata["current"] = 'undefined'
            Sun.setuserdata("activity", activity.get("id"), userdata)*/
        })
    }

    StageHelper = {
        init: function () {
            this.start_lock = false
            this.is_first = true
        },

        is_just_unlock: function (stage) {
            return (parseInt(stage.user_percentage) < 100);
        },

        get_image_name: function (stage) {
            var userdata = Sun.getuserdata('stage', stage.get('id'))
            var img_name = parseInt(stage.get('type')) + 1 + ""
            if (userdata['current'] == undefined) {
                img_name = "0" + img_name
            } else if (userdata['current'] == undefined) {
                img_name = "00" + img_name
            }
            return img_name
        },

        get_status: function (stage) {
            var userdata = Sun.getuserdata('stage', stage.get('id'))
            return userdata['current']
        }
    }

    endStage = function (aid, callback) {
        Sun.fetch("activity", {id: aid}, function (activity) {
            Sun.fetch("section", {id: activity.get("section_id")}, function (section) {
                Sun.fetch("stage", {id: section.get("stage_id")}, function (stage) {
                    Sun.setcomplete('stage', stage.get("id"))
                    if (callback != undefined) {
                        eval(callback)(stage.get('lesson_id'))
                    }
                })
            })
        })
    }
    clearViewed = function (id, callback) {
        Sun.fetch("stage", {id: id}, function (stage) {
            Sun.setunviewed('stage', stage.get('id'))
            $.each(stage.get('sections').models, function (index, se) {
                Sun.setunviewed('section', se.get('id'))
                Sun.fetch("section", {id: se.get('id')}, function (section) {
                    $.each(section.get('activities').models, function (index, ac) {
                        Sun.setunviewed('activity', ac.get('id'))
                        Sun.fetch("activity", {id: ac.get('id')}, function (activity) {
                            if (activity.get("type") == 4 || activity.get("type") == 7) {
                                for (var i = 0; i < activity.get("problems").models.length; i++) {
                                    var problem = activity.get("problems").models[i]
                                    Sun.setunviewed('problem', problem.get('id'))
                                }
                            }
                        })
                    })
                })
            })
        })
    }
})