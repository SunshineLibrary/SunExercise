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
        },
        "media": function (json) {
            return new Media(json)
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
                "user_id": user_id
            }
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
            if (options["id"] != undefined) {
                var id = options["id"]
                var cached = MATERIAL_CACHE[id]
                if (cached != undefined) {
                    Log.d("[CACHED]id," + id)
                    if (callback != undefined) {
                        eval(callback)(cached, options)
                    }
                }
            }

            var ret = undefined
            options["target"] = type
            if (typeof android == "undefined") {
                // web dev mode, request data from sundata server
                Log.i("[WEB]fetch," + type + "," + JSON.stringify(options))

                mockMaterial = "http://42.121.65.247:9000/api/material"
//                mockMaterial = "http://127.0.0.1:9000/api/material"
                $.getJSON(mockMaterial + "?callback=?",
                    options,
                    function (data) {
                        Log.i("[WEB]fetched," + JSON.stringify(data))
                        ret = Sun.createMaterial(data, type)
                        // Remove this blog to disable cache
                        MATERIAL_CACHE[id] = ret
                        if (callback != undefined) {
                            eval(callback)(ret, options)
                        }
                    }
                )
            } else {
                // android dev mode
                Log.i("[ANDROID]fetch," + type + "," + JSON.stringify(options))
                ret = Sun.requestMaterial(type, options["id"])
                Log.i("[ANDROID]fetched," + JSON.stringify(ret))
                if (callback != undefined) {
                    eval(callback)(ret, options)
                }
            }
            return ret
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
                Log.i("[WEB]set user data," + type + "," + id + "," + options)
                USER_DATA[id] = JSON.stringify(options)
            } else {
                // android dev mode
                Log.i("[ANDROID]set user data," + type + "," + id + "," + options)
                var reqText = JSON.stringify(req)
                options["result"] = android.requestUserData(reqText)
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
                Log.i("[WEB]set user data")
                USER_DATA[id] = JSON.stringify(userdata)
            } else {
                // android dev mode
                var reqText = JSON.stringify(req)
                Log.i("[ANDROID]set user data," + reqText)
                android.requestUserData(reqText)
            }
            if (typeof callback != "undefined") {
                eval(callback)(type, id)
            }
        },

        getuserdata: function (type, id) {
            // "get" method means get user data
            req = Sun.createrequest("user_data", "get", type, id, undefined, Sun.getuserid())
            var userdata = undefined
            if (typeof android == "undefined") {
                // web dev mode
                Log.d("[WEB]get user data," + type + "," + id + JSON.stringify(req))
                userdata = USER_DATA[id]
            } else {
                // android dev mode
                Log.d("[ANDROID]set user data," + JSON.stringify(req))
                userdata = android.requestUserData(JSON.stringify(req))
            }
            userdata = (userdata == undefined) ? "{}" : userdata
            return JSON.parse(userdata)
        },

        getmedia: function (id) {
            var ret = undefined
            if (typeof android == "undefined") {
                // web dev mode
                Log.i("[WEB]get media," + id)
                ret = new Media({"id": "03354928-9820-11e2-b307-00163e011797", "file_id": "1", "path": "http://st.xiami.com/res/loop/img/logo.png"})
            } else {
                // android dev mode
                Log.i("[ANDROID]get media," + id)
                ret = Sun.fetch('media', {id: id})
            }
            return ret
        },

        getuserid: function () {
            return "testuser"
        },

        showuserdata: function () {
            Log.i("current userdata:" + JSON.stringify(USER_DATA))
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
            Log.i('setunviewed,' + type + ',' + id)
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
        },

        isCollectionDownloaded: function (id) {
        }
    }

    Interfaces = {
        backpage: function () {
            url = window.location.href
            Log.i("backpage," + url)
            if (url.lastIndexOf("#") < 0 || url.indexOf("subject") >= 0) {
                // not in route, like index.html
                // or at subject page, like index.html#subject/123456
                Log.i("backpage," + url)
                android.showExitDialog()
            } else {
                goUpstairs()
            }
        },

        onSyncStart: function () {
            Log.i("onSyncStart")
        },

        onJsonReceived: function () {
            Log.i("onJsonReceived")
            $('#myModal').modal({
                backdrop: 'static',
                keyboard: false
            })
        },

        onJsonParsed: function () {
            Log.i("onJsonParsed")
            window.location.replace(location.pathname)
        },

        onSyncCompleted: function () {
            Log.i("onSyncCompleted")
        },

        onCollectionProgress: function (collectionId, percentage) {
            Log.i("onCollectionProgress," + collectionId + "," + percentage)
            changeDownloadProgress(collectionId, percentage * 100)
        },

        onCollectionDownloaded: function (lessonId, downloaded) {
            Sun.adduserdata('lesson', lessonId, 'downloaded', true)
            if (typeof android == "undefined") {
                Log.i("[WEB]onCollectionDownloaded," + lessonId + "," + downloaded)
            } else {
                Log.i("[ANDROID]onCollectionDownloaded," + lessonId + "," + downloaded)
            }
            if (downloaded == "true") {
                changeDownloadBtn(lessonId, true)
            } else {
                changeDownloadBtn(lessonId, false)
            }
        },

        loadUrl: function (route) {
            app_router.navigate(route, {trigger: true, replace: true})
        },

        sync: function () {
            if (typeof android == "undefined") {
                Log.i("[WEB]sync")
                setTimeout(function () {
                    setTimeout(function () {
                        Interfaces.onJsonParsed()
                    }, 2000)
                    Interfaces.onJsonReceived()
                }, 1000)
            } else {
                Log.i("[ANDROID]sync")
                android.sync()
            }
        },

        download: function (id) {
            if (typeof android == "undefined") {
                Log.i("[WEB]download," + id)
                setTimeout(function () {
                    setTimeout(function () {
                        Interfaces.onCollectionDownloaded(id, "true")
                    }, 1000)
                    changeDownloadProgress(id, 100)
                }, 1000)
            } else {
                Log.i("[ANDROID]download," + id)
                android.download(id)
            }
        },

        onReady: function () {
            if (typeof android == "undefined") {
                Log.i("[WEB]onReady")
            } else {
                Log.i("[ANDROID]onReady")
                android.onReady()
            }
        },

        onVideoComplete: function (id) {
            Log.i('video completed,' + id)
            $("#nextButton").removeAttr("disabled")
        }
    }

    DEBUG = true

    Log = {
        d: function (content) {
            if (DEBUG) {
                content = "[DEBUG]" + content
                console.log(content)
                log(content)
            }
        },
        i: function (content) {
            if (DEBUG) {
                content = "[INFO]" + content
                console.log(content)
                log(content)
            }
        },
        e: function (content) {
            if (DEBUG) {
                content = "[ERROR]" + content
                console.log(content)
                log(content)
            }
        },
        w: function (content) {
            if (DEBUG) {
                content = "[WARNING]" + content
                console.log(content)
                log(content)
            }
        }
    }
})

function changeDownloadProgress(id, percentage) {
    $('#lessonbox_download_' + id).hide()
    $('#lessonbox_download_progress_' + id).show()
    $('#lessonbox_download_progress_' + id + " >div").css('width', percentage + "%")
}

function changeDownloadBtn(id, downloaded) {
    if (downloaded) {
        $('#lessonbox_download_progress_' + id).hide()
        $('.well.' + id).addClass('downloaded');
        $('.well.'+id).click(function(e){
            $('.lesson_label >img.' + id).hide();
            window.open('#lesson/'+id,'_self');
        });

    } else {
        $('#lessonbox_download_' + id).show()
        $('#lessonbox_progress_' + id).hide()
        $('#lessonbox_download_progress_' + id).hide()
    }
}