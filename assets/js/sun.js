/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {

    USER_DATA = new Object()

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
        createrequest: function (api, method, type, id, user_data, user_id) {
            return {
                "api": api,
                "method": method,
                "param": {
                    "type": type,
                    "id": id,
                    "user_data": user_data
                },
                "user_id": user_id}
        },
        requestMaterial: function (type, id) {
            var req = Sun.createrequest("material", "get", type, id, undefined, Sun.getuserid())
            var resp = android.requestData(JSON.stringify(req))
            return Sun.createMaterial(JSON.parse(resp), type)
        },
        createMaterial: function (json, type) {
            var result = MATERIAL_TYPES[type](json)
            return result
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
                        console.log("data:" + JSON.stringify(data))
                        result = Sun.createMaterial(data, type)
                        if (callback != undefined) {
                            eval(callback)(result, options)
                        }
                    }
                )
            } else {
                // android dev mode
                console.log("[ANDROID]try to fetch a material," + type + "," + JSON.stringify(options))

                var result = Sun.requestMaterial(type, options["id"])
                if (callback != undefined) {
                    eval(callback)(result, options)
                }
            }
        },

        setuserdata: function (type, id, options, callback) {
            // "post" method means set user data
            var req = Sun.createrequest(
                "user_data",
                "post",
                type,
                id,
                JSON.stringify(options),
                Sun.getuserid())
            if (typeof android == "undefined") {
                // web dev mode
                console.log("[WEB]set user data")
                USER_DATA[id] = JSON.stringify(options)
            } else {
                // android dev mode
                var reqText = JSON.stringify(req)
                console.log("[ANDROID]set user data," + reqText)
                var resp = android.requestUserData(reqText)
                options["result"] = resp
            }
            if (callback != undefined) {
                eval(callback)(type, id, options)
            }
        },

        getuserdata: function (type, id) {
            // "get" method means get user data
            req = Sun.createrequest("user_data", "get", type, id, Sun.getuserid())
            var userdata = undefined
            if (typeof android == "undefined") {
                // web dev mode
                console.log("[WEB]set user data")
                userdata = USER_DATA[id]
            } else {
                // android dev mode
                console.log("[ANDROID]set user data," + JSON.stringify(req))
                userdata = android.requestUserData(JSON.stringify(req))
                console.log("got userdata!!!!!!," + userdata)
            }
            userdata = (userdata == undefined) ? "{}" : userdata
            console.log("got userdata," + id + "," + userdata)
            return JSON.parse(userdata)
        },

        getuserid: function () {
            return "testuser"
        },

        showuserdata: function () {
            console.log("current userdata:" + JSON.stringify(USER_DATA))
        },

        resetuserdata: function () {
            alert("before reset\r\n" + Object.keys(USER_DATA))
            delete USER_DATA
            USER_DATA = new Object()
            alert("reset completed\r\n" + Object.keys(USER_DATA))
        }
    }

    Interfaces = {
        backpage: function () {
            window.history.back()
        },

        onSyncStart: function () {
            console.log("onSyncStart")
        },

        onJsonReceived: function () {
            console.log("onJsonReceived")
        },

        onJsonParsed: function () {
            console.log("onJsonReceived")
        },

        onSyncCompleted: function () {
            console.log("onJsonReceived")
        },

        onCollectionDownloaded: function () {
            console.log("onJsonReceived")
        },

        sync: function () {
            if (typeof android == "undefined") {
                console.log("[WEB]sync")
            } else {
                console.log("[ANDROID]sync")
                android.sync();
            }
        },

        download: function (id) {
            if (typeof android == "undefined") {
                console.log("[WEB]download," + id)
            } else {
                console.log("[ANDROID]download," + id)
                android.download(id);
            }
        }
    }

})


