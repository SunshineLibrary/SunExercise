/**
 * Created with IntelliJ IDEA.
 * User: fxp
 * Date: 13-3-21
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 */
jQuery(function () {

    subjects_resp = '{"subjects":[{"id":"su574256","name":"Mathematics"},{"id":"su943492","name":"Chinese"}]}'

    subject_resp = '{"id":"su574256","name":"Mathematics","lessons":[{"id":"l113613","subject_id":"su574256","name":"Lesson1","seq":"0","time":"1363587564469","user_progress":"","user_result":"0","download_finish":"0"},{"id":"l117514","subject_id":"su574256","name":"Lesson1","seq":"0","time":"1363587564469","user_progress":"","user_result":"0","download_finish":"0"},{"id":"l117615","subject_id":"su574256","name":"Lesson1","seq":"0","time":"1363587564469","user_progress":"","user_result":"0","download_finish":"0"},{"id":"l117616","subject_id":"su574256","name":"Lesson1","seq":"0","time":"1363587564469","user_progress":"","user_result":"0","download_finish":"0"},{"id":"l117617","subject_id":"su574256","name":"Lesson1","seq":"0","time":"1363587564469","user_progress":"","user_result":"0","download_finish":"0"},{"id":"l728350","subject_id":"su574256","name":"Lesson2","seq":"1","time":"1313587564469","user_progress":"","user_result":"0","download_finish":"1"}]}'

    lesson_resp = '{"id":"l117615","subject_id":"su574256","name":"Lesson1","seq":"0","time":"2013-1-1","user_progress":"","user_result":"0","download_finish":"0","stages":[{"id":"st864147","lesson_id":"l117615","seq":"0","type":"0","user_progress":"","user_percentage":"0"},{"id":"st995524","lesson_id":"l117615","seq":"1","type":"1","user_progress":"","user_percentage":"0"}]}'

    stage_resp = '{"id":"st864147","lesson_id":"l117615","seq":"0","type":"0","user_progress":"","user_percentage":"0","sections":[{"id":"se769476","stage_id":"st864147","seq":"0","name":"Section1"},{"id":"se154978","stage_id":"st864147","seq":"1","name":"Section2"}]}'

    section_resp = '{"id":"se769476","stage_id":"st864147","seq":"0","name":"Section1","activities":[{"id":"a573585","section_id":"se769476","seq":"0","type":"4","name":"Activity1","body":"","jump_condition":"","user_progress":"","user_duration":"0","user_correct":"0","user_score":"0","media_id":""},{"id":"a376806","section_id":"se769476","seq":"1","type":"2","name":"Activity2","body":"","jump_condition":"","user_progress":"","user_duration":"0","user_correct":"0","user_score":"0","media_id":"ma100167"}]}'

    activity_with_problem_resp = '{"id":"a573585","section_id":"se769476","seq":"0","type":"7","name":"Activity1","body":"","jump_condition":"","user_progress":"","user_duration":"0","user_correct":"0","user_score":"0","media_id":"","problems":[{"id":"p310416","activity_id":"a573585","seq":"0","type":"0","body":"Single choice problem, please choice:","analysis":"","user_duration":"0","user_correct":"0","media_id":"mp996645"},{"id":"p264912","activity_id":"a573585","seq":"1","type":"2","body":"Singe filling blank, please fill:","analysis":"","user_duration":"0","user_correct":"0","media_id":"mp24876"}]}'

    problem_resp = '{"id":"p310416","activity_id":"a573585","seq":"0","type":"0","body":"Single choice problem, please choice:","analysis":"","user_duration":"0","user_correct":"0","media_id":"mp996645","choices":[{"id":"pc314259","problem_id":"p310416","seq":"0","display_text":"wrong choice1","answer":"no","user_choice":""},{"id":"pc184950","problem_id":"p310416","seq":"1","display_text":"wrong choice2","answer":"no","user_choice":""},{"id":"pc968102","problem_id":"p310416","seq":"2","display_text":"correct choice","answer":"yes","user_choice":""},{"id":"pc354181","problem_id":"p310416","seq":"3","display_text":"wrong choice4","answer":"no","user_choice":""}]}'

    fake_user_id = "fake_user_id"

    get_user_data_0 = {}

    get_user_data_1 = {"user_process": 30}

    get_user_data_2 = {"user_process": 100}

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
        requestMaterialMock: function () {
//            console.log("request mock,"+)
        },
        requestMaterial: function (type, id) {
            return Sun.request("material", "get", type, id, "testuser")
        },
        fetch: function (target, callback, options) {
            console.log("try to fetch a material," + target + "," + JSON.stringify(options))

//            testreq= {"api":"","method":"get","param":{"type":"section","id":"undefined"},"user_id":"testuser"}



            if (target == "subjects") {
                var resp = Sun.requestMaterial("subjects", "")
                subjects = new Subjects(resp.subjects)
                eval(callback)(subjects, options)
            } else if (target == "subject") {
                var resp = Sun.requestMaterial("subject", options["id"])
                subject = new Subject(resp)
                eval(callback)(subject, options)
            } else if (target == "lesson") {
                var resp = Sun.requestMaterial("lesson", options["id"])
                lesson = new Lesson(resp)
                eval(callback)(lesson, options)
            } else if (target == "stage") {
                var resp = Sun.requestMaterial("stage", options["id"])
                stage = new Stage(resp)
                eval(callback)(stage, options)
            } else if (target == "section") {
                var resp = Sun.requestMaterial("section", options["id"])
                section = new Section(resp)
                eval(callback)(section, options)
            } else if (target == "activity") {
                var resp = Sun.requestMaterial("activity", options["id"])
                activity = new Activity(resp)
                eval(callback)(activity, options)
            } else if (target == "problem") {
                var resp = Sun.requestMaterial("problem", options["id"])
                problem = new Problem(resp)
                eval(callback)(problem, options)
            } else {
                console.log("no such object defined," + JSON.stringify(target))
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

    SunTest = {
        testSetData: function () {

        }
    }

})


