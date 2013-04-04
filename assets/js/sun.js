/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {

    USER_DATA = new Object()

    MATERIAL_CACHE = new Object()

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
            var material = Sun.createMaterial(JSON.parse(resp), type)
            return material
        },
        createMaterial: function (json, type) {
            var result = MATERIAL_TYPES[type](json)
            return result
        },

        fetch: function (type, options, callback) {
            if (options == undefined) {
                options = {}
            }

            // Remove this blog to disable cache
            if (options["i d"] != undefined) {
                var id = options["id"]
                var cached = MATERIAL_CACHE[id]
                if (cached != undefined) {
                    console.log("[CACHED]id," + id)
                    if (callback != undefined) {
                        eval(callback)(cached, options)
                    }
                }
            }

            options["target"] = type
            if (typeof android == "undefined") {
                // web dev mode, request data from sundata server
                console.log("[WEB]try to fetch a material," + type + "," + JSON.stringify(options))

//                mockMaterial = "http://42.121.65.247:9000/api/material"
                mockMaterial = "http://127.0.0.1:9000/api/material"
                $.ajaxSetup({ "async": false });
                $.getJSON(mockMaterial + "?callback=?",
                    options,
                    function (data) {
                        console.log("data:" + JSON.stringify(data))
                        result = Sun.createMaterial(data, type)
                        // Remove this blog to disable cache
                        MATERIAL_CACHE[id] = result
                        if (callback != undefined) {
                            eval(callback)(result, options)
                        }
                    }
                )
                $.ajaxSetup({ "async": true});
            } else {
                // android dev mode
                console.log("[ANDROID]try to fetch a material," + type + "," + JSON.stringify(options))

                var result = Sun.requestMaterial(type, options["id"])
                console.log("result," + JSON.stringify(result))
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

        adduserdata: function (type, id, key, value) {
            // "post" method means set user data
            var userdata = Sun.getuserdata(type, id)
            userdata[key] = value

            var req = Sun.createrequest(
                "user_data",
                "post",
                type,
                id,
                JSON.stringify(userdata),
                Sun.getuserid())
            if (typeof android == "undefined") {
                // web dev mode
                console.log("[WEB]set user data")
                USER_DATA[id] = JSON.stringify(userdata)
            } else {
                // android dev mode
                var reqText = JSON.stringify(req)
                console.log("[ANDROID]set user data," + reqText)
                android.requestUserData(reqText)
            }
            if (callback != undefined) {
                eval(callback)(type, id)
            }
        },

        getuserdata: function (type, id) {
            // "get" method means get user data
            req = Sun.createrequest("user_data", "get", type, id, undefined, Sun.getuserid())
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
        },

        setcomplete: function (type, id, options, callback) {
            userdata = Sun.getuserdata(type, id)
            userdata['current'] = "EOF"
            if (options != undefined) {
                $.each(Object.keys(options), function (key, value) {
                    userdata[value] = options[value]
                })
            }
            Sun.setuserdata(type, id, userdata)
            if (callback != undefined) {
                eval(callback)(type, id, options)
            }
        },

        setviewed: function (type, id) {
            var userdata = Sun.getuserdata(type, id)
            userdata['current_viewed'] = 'EOF'
            Sun.setuserdata(type, id, userdata)
        },

        setunviewed: function (type, id) {
            console.log('setunviewed,' + type + ',' + id)
            var userdata = Sun.getuserdata(type, id)
            userdata['current_viewed'] = undefined
            Sun.setuserdata(type, id, userdata)
        },

        isviewed: function (type, id) {
            var userdata = Sun.getuserdata(type, id)
            return userdata['current_viewed'] == 'EOF'
        },


        iscomplete: function (type, id) {
            userdata = Sun.getuserdata(type, id)
            return userdata['current'] == "EOF"
        }

    }

    Interfaces = {
        backpage: function () {
            url = window.location.href
            // TODO change it to a "REAL" index judgement
            if (url.lastIndexOf("#") < 0) {
                // not in route, like index.html
                console.log("not in route now, maybe there is no data right now")
                android.showExitDialog()
            } else {
                console.log("back pressed, current url," + url)
                if (url.indexOf("subject") >= 0) {
                    console.log("ready to exit")
                    android.showExitDialog()
                } else {
                    window.history.back()
                }
            }
        },

        onSyncStart: function () {
            console.log("onSyncStart")
        },

        onJsonReceived: function () {
            console.log("onJsonReceived")
        },

        onJsonParsed: function () {
            console.log("onJsonParsed")
        },

        onSyncCompleted: function () {
            console.log("onSyncCompleted")
            location.reload();
        },

        onCollectionProgress: function (collectionId, percentage) {
            console.log("onCollectionProgress," + collectionId + "," + percentage)
        },

        onCollectionDownloaded: function (lessonId, downloaded) {
            console.log("onCollectionDownloaded," + lessonId + "," + downloaded)
        },

        sync: function () {
            if (typeof android == "undefined") {
                console.log("[WEB]sync")
            } else {
                console.log("[ANDROID]sync")
                android.sync()
            }
        },

        download: function (id) {
            if (typeof android == "undefined") {
                console.log("[WEB]download," + id)
            } else {
                console.log("[ANDROID]download," + id)
                android.download(id)
            }
        },

        onReady:function(){
            if (typeof android == "undefined") {
                console.log("[WEB]onReady")
            } else {
                console.log("[ANDROID]onReady")
                android.onReady()
            }
        }
    }

    Log = {
        d: function (content) {
            console.log("[DEBUG]" + content)
        },
        i: function (content) {
            console.log("[INFO]" + content)
        },
        e: function (content) {
            console.log("[ERROR]" + content)
        },
        w: function (content) {
            console.log("[WARNING]" + content)
        }
    }

})


