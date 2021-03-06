/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {

    WEB_DEV_MODE = (typeof android == 'undefined');

    // For Web dev only, a mock to userdata storage
    USER_DATA = new Object()

    USER_DATA_CACHE = new Object()

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
            var req = Sun.createrequest("material", "get", type, id, undefined, Sun.getuserid());
            var resp = android.requestData(JSON.stringify(req));
            var material = Sun.createMaterial(JSON.parse(resp), type);
            return material;
        },
        createMaterial: function (json, type) {
            return MATERIAL_TYPES[type](json);
        },

        fetch: function (type, options, callback, refresh) {
            options = (options == null) ? {} : options
            // Only if the type is subjects, options can be undefined
            var id = (typeof options == "undefined") ? 'subjects' : options['id']

            // Enable cache
            if (refresh != true) {
                var cached = MATERIAL_CACHE[id]
                if (cached != undefined) {
//                    console.log("[CACHEDFETCHCOST]" + (new Date().getTime() - start))
                    if (callback != undefined) {
                        eval(callback)(cached, options)
                    }
                    return cached
                }
            }

            var ret = undefined
            options["target"] = type
            if (typeof android == "undefined") {
                // web dev mode, request data from sundata server
                Log.i("[WEB]fetch," + type + "," + JSON.stringify(options))

//                mockMaterial = "http://42.121.65.247:9000/api/material"
                mockMaterial = "http://127.0.0.1:9000/api/material"
                $.getJSON(mockMaterial + "?callback=?",
                    options,
                    function (data) {
                        Log.i("[WEB]fetched," + JSON.stringify(data))
                        ret = Sun.createMaterial(data, type)
                        // Remove this blog to disable cache
                        MATERIAL_CACHE[id] = ret;
                        if (callback != undefined) {
                            eval(callback)(ret, options)
                        }
                    }
                )
            } else {
                // android dev mode
                ret = Sun.requestMaterial(type, options["id"]);
                if (callback != undefined) {
                    MATERIAL_CACHE[id] = ret;
                    console.log('cached,' + options["id"]);
                    eval(callback)(ret, options)
                }
            }
            return ret
        },

        setuserdata: function (type, id, options, callback) {
            // "post" method means set user data
            options['event_created'] = (new Date()).getTime();
            var req = Sun.createrequest(
                "user_data",
                "post",
                type,
                id,
                JSON.stringify(options),
                Sun.getuserid())
            if (typeof android == "undefined") {
                // web dev mode
                USER_DATA[id] = JSON.stringify(options);
            } else {
                // android dev mode
                var reqText = JSON.stringify(req);
                android.requestUserData(reqText);
            }
            console.log('set userdata cached,' + id);

            USER_DATA_CACHE[id] = options;
            var cached = MATERIAL_CACHE[id];
            if (cached != undefined) {
                cached.set('userdata', options);
            }
            if (callback != undefined) {
                eval(callback)(type, id, options)
            }
        },

        adduserdata: function (type, id, key, value) {
            // "post" method means set user data
            var userdata = Sun.getuserdata(type, id);
            userdata[key] = value;
            userdata['event_created'] = (new Date()).getTime();

            var req = Sun.createrequest(
                "user_data",
                "post",
                type,
                id,
                JSON.stringify(userdata),
                Sun.getuserid());
            if (typeof android == "undefined") {
                // web dev mode
                USER_DATA[id] = JSON.stringify(userdata);
            } else {
                // android dev mode
                var reqText = JSON.stringify(req);
                android.requestUserData(reqText);
            }
            USER_DATA_CACHE[id] = userdata;
            MATERIAL_CACHE[id].set('userdata', userdata);
        },

        getuserdata: function (type, id) {
            // "get" method means get user data
            if (USER_DATA_CACHE[id] != undefined) {
                Log.i("[CACHEUSERDATA]" + type + "," + id)
                return USER_DATA_CACHE[id]
            }
            req = Sun.createrequest("user_data", "get", type, id, undefined, Sun.getuserid())
            var userdata = undefined
            if (typeof android == "undefined") {
                // web dev mode
                Log.d("[WEB]get user data," + type + "," + id)
                userdata = USER_DATA[id]
            } else {
                // android dev mode
                Log.d("[ANDROID]get user data," + type + "," + id)
                userdata = android.requestUserData(JSON.stringify(req))
            }
            userdata = (typeof userdata == "undefined") ? "{}" : userdata
            var ret = JSON.parse(userdata)
            USER_DATA_CACHE[id] = ret
            return ret
        },

        getmedia: function (id) {
            var ret = undefined
            if (typeof android == "undefined") {
                // web dev mode
                Log.i("[WEB]get media," + id)
                ret = new Media({"id": "03354928-9820-11e2-b307-00163e011797", "file_id": "1", "path": "http://shuwu.sunshine-library.org/system/files/1377/original/888a9f9d06ada3dfe4842149de51f61987c77a81.JPG"})
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

        setcomplete: function (type, id, options, callback) {
            var userdata = Sun.getuserdata(type, id)
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
            var userdata = Sun.getuserdata(type, id)
            return userdata['current'] == "EOF"
        },

        isCollectionDownloaded: function (id) {
        }
    }

    Interfaces = {
        backpage: function () {
            url = window.location.href;
            console.log("backpage," + url);
            if (url.lastIndexOf("#") < 0 || url.indexOf("subject") >= 0) {
                // not in route, like index.html
                // or at subject page, like index.html#subject/123456
                console.log("backpage," + url);
                android.showExitDialog()
            } else {
                goUpstairs();
            }
        },

        onSyncStart: function () {
            Log.i("onSyncStart")
            addRefreshBtn()
            disableRefresh()
        },

        onJsonReceived: function () {
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
            removeRefreshBtn()
            enableRefresh();
        },

        onCollectionProgress: function (collectionId, percentage) {
            console.log("onCollectionProgress," + collectionId + "," + percentage);
            changeDownloadProgress(collectionId, percentage * 100)
        },

        onCollectionDownloaded: function (lessonId, downloaded) {
//          Sun.adduserdata('lesson', lessonId, 'downloaded', true)
            if (typeof android == "undefined") {
                Log.i("[WEB]onCollectionDownloaded," + lessonId + "," + downloaded)
            } else {
                Log.i("[ANDROID]onCollectionDownloaded," + lessonId + "," + downloaded)
            }
            if (downloaded == "true") {
                changeDownloadBtn(lessonId, '1')
            } else {
                changeDownloadBtn(lessonId, 'failed')
            }
        },

        loadUrl: function (route) {
            app_router.navigate(route, {trigger: true, replace: true})
        },

        sync: function () {
            if (typeof android == "undefined") {
                setTimeout(function () {
                    setTimeout(function () {
                        Interfaces.onJsonParsed()
                        removeRefreshBtn()
                        enableRefresh()
                    }, 2000)
                    Interfaces.onJsonReceived()
                    addRefreshBtn()
                    disableRefresh()
                }, 1000)
            } else {
                android.sync()
            }
        },

        download: function (id) {
            if (typeof android == "undefined") {
                Log.i("[WEB]download," + id)
                setTimeout(function () {
                    setTimeout(function () {
                        setTimeout(function () {
                            setTimeout(function () {
                                Interfaces.onCollectionDownloaded(id, "true")
                            }, 1000)
                            changeDownloadProgress(id, 100)
                        }, 1000)
                        changeDownloadProgress(id, 60)
                    }, 1000)
                    changeDownloadProgress(id, 30)
                }, 1000)
            } else {
                Log.i("[ANDROID]download," + id)
                $('#lessonbox_download_' + id).hide('disabled');
                $('#lessonbox_download_' + id).attr('onclick', '').unbind('click');
                android.download(id)
            }
        },

        onReady: function () {
            if (typeof android == "undefined") {
                Log.i("[WEB]onReady");
            } else {
                console.log("[ANDROID]onReady");
                android.onReady();
            }
        },

        onVideoComplete: function (id) {
            Log.i('video completed,' + id)
            $("#nextButton").removeAttr("disabled")
        },

        onAudioComplete: function (id) {
            onAudioStop(id);
        },

        stopAudio: function () {
            if (!WEB_DEV_MODE) {
                android.stopAudio();
            }
        },

        openThirdPartyApp: function (path, id, fileType) {
            if (WEB_DEV_MODE) {
//                    Log.i("[WEB]openThirdPartyApp")
            } else {
//                    Log.i("[Android]openThirdPartyApp")
                android.openThirdPartyApp(path, fileType)
            }
        },

        deletePlayLog: function () {
            if (typeof android != "undefined") {
                android.deletePlayLog()
            }
        },

        playMultimedia: function (type, audioPath) {
            console.log('playMultimedia,' + type + "," + audioPath);
        }
    }

    DEBUG = true

    Log = {
        d: function (content) {
//            if (DEBUG) {
//                content = "[DEBUG]" + content
//                console.log(content)
//                log(content)
//            }
        },
        i: function (content) {
//            if (DEBUG) {
//                content = "[INFO]" + content
//                console.log(content)
//                log(content)
//            }
        },
        e: function (content) {
//            if (DEBUG) {
//                content = "[ERROR]" + content
//                console.log(content)
//                log(content)
//            }
        },
        w: function (content) {
//            if (DEBUG) {
//                content = "[WARNING]" + content
//                console.log(content)
//                log(content)
//            }
        }
    }

    /* load = {
     rmDialog: function(callback){

     //if (typeof android == "undefined") {
     setTimeout(function(){
     $('#progress').remove()
     },500)
     //}else{
     //    $('#progress').remove()
     //}
     },
     addDialog: function(id){
     $('body').append('<div id="progress">正在努力加载页面...</div>')
     window.open('#subject/' + id, '_self')
     }
     }*/
})

function changeDownloadProgress(id, percentage) {
    $('#lessonbox_download_' + id).hide()
    $('#lessonbox_download_progress_' + id).show()
    $('#lessonbox_download_progress_' + id + " >div").css('width', percentage + "%")
}

function changeDownloadBtn(id, downloaded) {
    if (downloaded == '1') {
        $('#lessonbox_download_progress_' + id).hide()
        $('.well.' + id).addClass('downloaded');
        $('.well.' + id).click(function (e) {
            $('.lesson_label >img.' + id).hide();
            window.open('#lesson/' + id, '_self');
        });
    } else if (downloaded == 'downloading') {
        $('#lessonbox_download_' + id).addClass('disabled')
        $('#lessonbox_download_' + id).attr('onclick', '').unbind('click')
    } else {
        $('#lessonbox_download_' + id).show()
        $('#lessonbox_progress_' + id).hide()
        $('#lessonbox_download_progress_' + id).hide()
    }
}

function addRefreshBtn() {
    $('.nav>li>img.icon').addClass('icon-spin');
}

function removeRefreshBtn() {
    $('.nav>li>img.icon').removeClass('icon-spin');
}

function disableRefresh() {
    $('.nav>li>img.icon').attr('onclick', '').unbind('click')
}

function enableRefresh() {
    $('.nav>li>img.icon').attr('onclick', '').bind('click', function () {
        Interfaces.sync()
    });
}