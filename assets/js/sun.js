/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {

    MATERIAL_TYPES = {
        "subjects": function (json) {
            return new Subjects(json.subjects)
        },
        "subject": function (json) {
            return new Subject(json)
        },
        "lesson": function (json) {
            return new Lesson(json)
        },
        "section": function (json) {
            return new Section(json)
        },
        "stage": function (json) {
            return new Stage(json)
        },
        "activity": function (json) {
            return new Activity(json)
        },
        "problem": function (json) {
            return new Problem(json)
        }
    }

    Sun = {
        request: function (api, method, type, id, user_id) {
            var requestContent = JSON.stringify({
                "api": "",
                "method": "get",
                "param": {
                    "type": type,
                    "id": id
                },
                "user_id": user_id})
            return JSON.parse(android.requestData(requestContent))
        },
        requestMaterial: function (type, id) {
            var json = Sun.request("material", "get", type, id, "testuser")
            return Sun.createMaterial(json, type)
        },
        createMaterial: function (json, type) {
            return MATERIAL_TYPES[type](json)
        },

        fetch: function (type, options, callback) {
            if (options == undefined) {
                options = {}
            }

            options["target"] = type
            if (typeof android == "undefined") {
                // web dev mode, request data from sundata server
                console.log("[WEB]try to fetch a material," + type + "," + JSON.stringify(options))

                mockMaterial = "http://42.121.65.247:9000/api/material"
                $.getJSON(mockMaterial + "?callback=?",
                    options,
                    function (data) {
                        result = Sun.createMaterial(data, type)
                        eval(callback)(result, options)
                    }
                )
            } else {
                // android dev mode
                console.log("[ANDROID]try to fetch a material," + type + "," + JSON.stringify(options))

                var result = Sun.requestMaterial(type, options["id"])
                eval(callback)(result, options)
            }

        },

        setuserdata: function (callback, target, type, options) {
            console.log("setuserdata," + target + "," + type + "," + JSON.stringify(options))
            eval(callback)()
        },

        getuserdata: function (target, type) {
            console.log("getuserdata," + target + "," + type)
            return get_user_data_1
        }
    }
})


