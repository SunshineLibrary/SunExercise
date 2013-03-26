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
                    console.log("stage," + JSON.stringify(stage))
                    userdata = Sun.getuserdata("stage", stage.get("id"))
                    userdata["current"] = id
                    Sun.setuserdata("stage", stage.get("id"), userdata)
                })
            })
        })
    }
})