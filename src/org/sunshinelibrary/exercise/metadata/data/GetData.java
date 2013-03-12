package org.sunshinelibrary.exercise.metadata.data;

import android.content.ContentResolver;
import android.database.Cursor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.MetadataContract;
import org.sunshinelibrary.support.utils.CursorUtils;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-20
 * Time: 下午12:46
 */


public class GetData {


    public static final String REQ_ID = "req_id";
    public static final String SUBJECT_ID = "subject_id";
    public static final String LESSON_ID = "lesson_id";
    public static final String ACTIVITY_ID = "activity_id";
    public static final String STAGE_ID = "stage_id";
    public static final String LESSONS = "lessons";
    public static final String PROBLEMS = "problems";
    public static final String ACTIVITY = "activity";
    public static final String ACTIVITYS = "activities";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SUBJECTS = "subjects";
    public static final String TIME = "time";
    public static final String STAGE_PERCENTAGE = "stage_percentage";
    public static final String USER_PROGRESS = "user_progress";
    public static final String STAGE_COUNT = "stage_count";
    public static final String USER_RESULT = "user_result";
    public static final String SEQ = "seq";
    public static final String USER_PRECENTAGE = "user_precentage";
    public static final String TYPE = "type";
    public static final String SECTION_ID = "section_id";
    public static final String USER_DURATION = "user_duration";
    public static final String USER_SCORE = "user_score";
    public static final String USER_CORRECT = "user_correct";
    public static final String SECTION_NAME = "section_id";
    public static final String JUNP_CONDITION = "jump_condition";
    public static final String ANALYSIS = "analysis";
    public static final String BODY = "body";
    private static final String CHOICES = "choice";
    private static final String ANSWER = "answer";
    private static final String USER_CHOICE = "user_choice";
    public static final String PATH = "path";


    private ContentResolver mResovler;



    public GetData() {
        mResovler = ExerciseApplication.getInstance().getApplicationContext().getContentResolver();
    }

    public String get(JSONObject mRequestJson)  {
        try {
            int req_id = mRequestJson.getInt(REQ_ID);
            switch (req_id) {
                case 201:
                    return get201();
                case 211:
                    int subject_id = mRequestJson.getInt(SUBJECT_ID);
                    return get211(subject_id);
                case 301:
                    int lesson_id = mRequestJson.getInt(LESSON_ID);
                    return get301(lesson_id);
                case 401:
                    int activity_id = mRequestJson.getInt(ACTIVITY_ID);
                    int stage_id = mRequestJson.getInt(STAGE_ID);
                    return get401(activity_id, stage_id);
                case 411:
                    stage_id = mRequestJson.getInt(STAGE_ID);
                    activity_id = mRequestJson.getInt(ACTIVITY_ID);
                    return get411(activity_id, stage_id);
                default:
                    return JsonInterface.FAILED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return JsonInterface.FAILED;
        }
    }

    public String get201() throws JSONException{
        JSONObject answer = new JSONObject();

        int first_subject_id = 0;
        Cursor cursor = mResovler.query(MetadataContract.Subjects.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            first_subject_id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Subjects._STRING_ID));
        }
        cursor.close();

        answer.put(REQ_ID, "201");
        answer.put(SUBJECTS, getAllSubject());
        answer.put(LESSONS, getLessonsBySubject(first_subject_id));
        return answer.toString();

        //TODO stage_count
    }

    private JSONObject getSubject(Cursor cursor)throws JSONException {
            /*
            {
                "id"  : "subject1", // 科目ID
                "name": "数学",     // 科目名
             }
             */
        JSONObject subject = new JSONObject();

        int columeID = cursor.getColumnIndex(MetadataContract.Subjects._STRING_ID);
        int columeName = cursor.getColumnIndex(MetadataContract.Subjects._NAME);

        int id = cursor.getInt(columeID);
        String name = cursor.getString(columeName);

        subject.put(ID, id);
        subject.put(NAME, name);

        return subject;

    }

    private JSONArray getAllSubject() throws JSONException{
        JSONArray subjects = new JSONArray();
        Cursor cursor = mResovler.query(MetadataContract.Subjects.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            subjects.put(getSubject(cursor));

        }
        cursor.close();
        return subjects;

    }


    private JSONArray getLessonsBySubject(int subject_id) throws JSONException{

        JSONArray lessons = new JSONArray();
        Cursor cursor = mResovler.query(MetadataContract.Lessons.CONTENT_URI, null,
                MetadataContract.Lessons._PARENT_ID + " = " + subject_id, null, null);
        while (cursor.moveToNext()) {
            lessons.put(getLesson(cursor));
        }
        cursor.close();
        return lessons;
    }

    private JSONObject getLesson(Cursor cursor) throws JSONException{


        JSONObject lesson = new JSONObject();


        int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Lessons._STRING_ID));
        int subject_id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Lessons._PARENT_ID));
        String name = cursor.getString(cursor.getColumnIndex(MetadataContract.Lessons._NAME));
        String datatime = cursor.getString(cursor.getColumnIndex(MetadataContract.Lessons._TIME));
        int userProgress = cursor.getInt(cursor.getColumnIndex(MetadataContract.Lessons._USER_PROGRESS));
        float userResult = cursor.getFloat(cursor.getColumnIndex(MetadataContract.Lessons._USER_RESULT));


        Cursor stageCursor = mResovler.query(MetadataContract.Stages.CONTENT_URI, null,
                MetadataContract.Stages._PARENT_ID + " = " + id, null,
                MetadataContract.Stages._SEQUENCE);
        int stageCount = stageCursor.getCount();
        stageCursor.close();

        lesson.put(ID, id);
        lesson.put(NAME, name);
        lesson.put(TIME, datatime);
        lesson.put(USER_PROGRESS, userProgress);
        lesson.put(USER_RESULT, userResult);
        lesson.put(STAGE_COUNT, stageCount);
        lesson.put(STAGE_PERCENTAGE, userResult);


        /*
            {
            "id": "l1", // 课程ID
            "name": "有理数的四则运算", // 课程名
            "time": "1353412800", // 课程时间
            "user_progress": "", // 课程进展，指向下级某个阶段，空表示该课程可以显示为”新“，“done”表示该课程已做完。
            "user_result": "",
            "stage_count": 2, // 阶段总数，用于首屏整体进度条显示
            "stage_percentage": // 0 进度，用于首屏当前进度显示
            },
        */

        return lesson;
    }

    public String get211(int subject_id)throws JSONException {
        JSONObject answer = new JSONObject();

        answer.put(REQ_ID, "211");
        answer.put(SUBJECT_ID, subject_id);
        answer.put(LESSONS, getLessonsBySubject(subject_id));

        return answer.toString();

    }

    public String get301(int lesson_id) throws JSONException{

        JSONObject answer = new JSONObject();


        answer.put(REQ_ID, "301");
        answer.put(LESSON_ID, lesson_id);
        answer.put(LESSONS, getStagesByLesson(lesson_id));

        return answer.toString();
    }

    private JSONArray getStagesByLesson(int lesson_id)throws JSONException {
        Cursor cursor = mResovler.query(MetadataContract.Stages.CONTENT_URI, null,
                MetadataContract.Stages._PARENT_ID + " = " + String.valueOf(lesson_id), null,
                MetadataContract.Stages._SEQUENCE);
        JSONArray stages = new JSONArray();
        while (cursor.moveToNext()) {
            stages.put(getStage(cursor));
        }
        cursor.close();
        return stages;
    }

    private JSONObject getStage(Cursor cursor) throws JSONException{
        JSONObject stage = new JSONObject();

        int columeID = cursor.getColumnIndex(MetadataContract.Stages._STRING_ID);
        int columeSequence = cursor.getColumnIndex(MetadataContract.Stages._SEQUENCE);
        int columnType = cursor.getColumnIndex(MetadataContract.Stages._TYPE);
        int columnUserProgress = cursor.getColumnIndex(MetadataContract.Stages._USER_PROGRESS);


        int id = cursor.getInt(columeID);
        int sequence = cursor.getInt(columeSequence);
        int type = cursor.getInt(columnType);
        int userProgress = cursor.getInt(columnUserProgress);


        float userPrecentage = cursor.getFloat(cursor.getColumnIndex(MetadataContract.Stages._USER_PERCENTAGE));


        stage.put(ID, id);
        stage.put(SEQ, sequence);
        stage.put(TYPE, type);
        stage.put(USER_PROGRESS, userProgress);
        stage.put(USER_PRECENTAGE, userPrecentage);


        /*
            "id": "stage2",
            "seq": 2,
            "type": 2,
            "user_percentage": 0.5, // 用户的进度百分比，目前是下级所有section下所有activity的完成百分比
            "user_progress": "activity3" // 当前进度，目前section没有单独的展示页面，直接指向activityID，done表示已完成，空表示未开始。
         */
        return stage;
    }


    public String get401(int activity_id, int stage_id)throws JSONException {
        JSONObject answer = new JSONObject();

        answer.put(REQ_ID, "401");
        answer.put(STAGE_ID, stage_id);
        answer.put(ACTIVITY_ID, activity_id);
        answer.put(ACTIVITYS, getActivitiesByStage(stage_id));
        answer.put(ACTIVITY, getActivityDetail(activity_id));
        answer.put(PROBLEMS, getProblemsByActivityForType4or7(activity_id));
        return answer.toString();
    }

    private JSONArray getActivitiesByStage(int stage_id) throws JSONException{
        JSONArray activitys = new JSONArray();
        Cursor cursorSection = mResovler.query(MetadataContract.Sections.CONTENT_URI, null,
                MetadataContract.Sections._STRING_ID + " = " + String.valueOf(stage_id), null,
                MetadataContract.Sections._SEQUENCE);
        while (cursorSection.moveToNext()) {
            int sectionID = cursorSection.getInt(cursorSection.getColumnIndex(MetadataContract.Sections._STRING_ID));
            Cursor cursorActivity = mResovler.query(MetadataContract.Activities.CONTENT_URI, null,
                    MetadataContract.Activities._STRING_ID + " = " + String.valueOf(sectionID), null,
                    MetadataContract.Activities._SEQUENCE);
            while (cursorActivity.moveToNext()) {
                activitys.put(getActivityEssential(cursorActivity));
            }
            cursorActivity.close();
        }
        cursorSection.close();
        return activitys;
    }

    private JSONObject getActivityEssential(Cursor cursor)throws JSONException {
        /*
            {
            "id": "activity11", // 活动ID
            "section_id": "section1", // 活动所属环节ID
            "section_name": "加减运算综合", / 活动所属环节名称
            "seq": 0, // 顺序，默认按此排列
            "type": 7, // 活动类型
            "name": "诊断",  // 活动名
            "user_progress": "done" // 活动阶段，是跳过还是完成
        },

         */

        JSONObject activity = new JSONObject();

        int columeID = cursor.getColumnIndex(MetadataContract.Activities._STRING_ID);
        int columnSection = cursor.getColumnIndex(MetadataContract.Activities._PARENT_ID);
        int columeSeq = cursor.getColumnIndex(MetadataContract.Activities._SEQUENCE);
        int columeName = cursor.getColumnIndex(MetadataContract.Activities._NAME);
        int columnType = cursor.getColumnIndex(MetadataContract.Activities._TYPE);
        int columnUserProgress = cursor.getColumnIndex(MetadataContract.Activities._USER_PROGRESS);

        int id = cursor.getInt(columeID);
        int sectionId = cursor.getInt(columnSection);
        int seq = cursor.getInt(columeSeq);
        int type = cursor.getInt(columnType);
        String name = cursor.getString(columeName);
        int userProgress = cursor.getInt(columnUserProgress);

        String sectionName = new String();
        Cursor sectionCousur = mResovler.query(MetadataContract.Sections.CONTENT_URI, null, MetadataContract.Sections._STRING_ID + " = " + String.valueOf(sectionId), null, null);
        if (sectionCousur.moveToFirst()) {
            int columnSectionName = cursor.getColumnIndex(MetadataContract.Sections._NAME);
            sectionName = cursor.getString(columnSectionName);
        }

        activity.put(ID, id);
        activity.put(SECTION_ID, sectionId);
        activity.put(SECTION_NAME, sectionName);
        activity.put(SEQ, seq);
        activity.put(TYPE, type);
        activity.put(NAME, name);
        if  (!cursor.isNull(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID))){
            activity.put(PATH, cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID)));
        }

        return activity;
    }

    private JSONObject getActivityDetail(int activity_id)throws JSONException {
        Cursor cursor = mResovler.query(MetadataContract.Activities.CONTENT_URI, null, MetadataContract.Activities._STRING_ID + " + " + activity_id, null, null);
        cursor.moveToFirst();
        return getActivityDetail(cursor);
    }

    private JSONObject getActivityDetail(Cursor cursor)throws JSONException {

        JSONObject activity = new JSONObject();


        int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._STRING_ID));
        int sectionId = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._PARENT_ID));
        int seq = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._SEQUENCE));
        int type = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._NAME));
        String name = cursor.getString(cursor.getColumnIndex(MetadataContract.Activities._TYPE));
        int userProgress = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._USER_PROGRESS));

        String sectionName = new String();
        Cursor sectionCousur = mResovler.query(MetadataContract.Sections.CONTENT_URI, null,
                MetadataContract.Sections._STRING_ID + " = " + String.valueOf(sectionId), null,
                MetadataContract.Sections._SEQUENCE);
        if (sectionCousur.moveToFirst()) {
            int columnSectionName = cursor.getColumnIndex(MetadataContract.Sections._NAME);
            sectionName = cursor.getString(columnSectionName);
        }



        activity.put(ID, id);
        activity.put(SECTION_ID, sectionId);
        activity.put(SECTION_NAME, sectionName);
        activity.put(SEQ, seq);
        activity.put(TYPE, type);
        activity.put(NAME, name);
        if  (!cursor.isNull(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID))){
            activity.put(PATH, cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID)));
        }
        activity.put(JUNP_CONDITION, cursor.getString(cursor.getColumnIndex(MetadataContract.Activities._JUMP_CONDITION)));
        activity.put(USER_PROGRESS, userProgress);
        activity.put(USER_DURATION, cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._USER_DURATION)));
        activity.put(USER_CORRECT, cursor.getFloat(cursor.getColumnIndex(MetadataContract.Activities._USER_CORRECT)));
        activity.put(USER_SCORE, cursor.getFloat(cursor.getColumnIndex(MetadataContract.Activities._USER_SCORE)));
        return activity;

          /*
        "activity": {
        "id": "activity13",
        "section_id": "section1",
        "section_name": "加减运算综合",
        "seq": 2,
        "type": 4,
        "name": "练习题",
        "body": "请看视频",
        "path": ""
        "weight": 1,
        "jump_condition": [

        ],
        "user_progress": "problem2",
        "user_duration": "",
        "user_correct": 0,
        "user_score": 0,
       */
    }

    private JSONArray getProblemsByActivityForType4or7(int activity_id) throws JSONException{
        JSONArray problems = new JSONArray();
        String select = MetadataContract.Problems._PARENT_ID + " = " + String.valueOf(activity_id) + " AND " +
                "(" + MetadataContract.Problems._TYPE + " = " + 4 + " OR " + MetadataContract.Problems._TYPE + " = " + 7 + " )";

        Cursor cursor = mResovler.query(MetadataContract.Problems.CONTENT_URI, null, select, null, MetadataContract.ProblemChoices._SEQUENCE);
        while (cursor.moveToNext()) {
            problems.put(getProblem(cursor));

        }
        cursor.close();

        return problems;
    }

    private JSONObject getProblem(Cursor cursor)throws JSONException {

        JSONObject problem = new JSONObject();

        int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._STRING_ID));

        problem.put(ID, id);
        problem.put(SEQ, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._SEQUENCE)));
        problem.put(TYPE, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._TYPE)));
        problem.put(BODY, cursor.getString(cursor.getColumnIndex(MetadataContract.Problems._BODY)));

        if  (!cursor.isNull(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID))){
            problem.put(PATH, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._MEDIA_ID)));
        }

        problem.put(ANALYSIS, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._ANALYSIS)));
        problem.put(USER_DURATION, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._USER_DURATION)));
        problem.put(USER_CORRECT, cursor.getFloat(cursor.getColumnIndex(MetadataContract.Problems._USER_CORRECT)));
        problem.put(CHOICES, getProblemChoiceByProblem(id));

        return problem;
          /*
         "problems": [
        {
            "id": "problem131",
            "seq": 0,
            "type": 0,
            "body": "下列式子中属于单项式的是",
            "path" : xxxxx
            "analysis": "",
            "user_duration": 0,
            "user_correct": 0,
            "choices": [
                {
                    "id": "choice1",
                    "seq": 0,
                    "body": "a + 1",
                    "answer": 1,
                    "user_choice": 1
                }
            ],
         */


    }

    private JSONArray getProblemChoiceByProblem(int problems_id) throws JSONException{
        Cursor choiceCursor = mResovler.query(MetadataContract.ProblemChoices.CONTENT_URI, null,
                MetadataContract.ProblemChoices._PARENT_ID + " = " + problems_id, null, MetadataContract.ProblemChoices._SEQUENCE);
        JSONArray choices = new JSONArray();
        while (choiceCursor.moveToNext()) {
            choices.put(getProblemChoice(choiceCursor));
        }
        choiceCursor.close();
        return choices;
    }

    private JSONObject getProblemChoice(Cursor cursor)throws JSONException {
        JSONObject choice = new JSONObject();

        choice.put(ID, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._STRING_ID)));
        choice.put(SEQ, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._SEQUENCE)));
        choice.put(BODY, cursor.getString(cursor.getColumnIndex(MetadataContract.ProblemChoices._DISPLAY_TEXT)));
        choice.put(ANSWER, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._ANSWER)));
        choice.put(USER_CHOICE, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._USER_CHOICE)));

        return choice;

        /*
                    "id": "choice1",
                    "seq": 2,
                    "body": "axy",
                    "answer": 0,
                    "user_choice": 0
         */
    }

    public String get411(int activity_id, int stage_id) throws JSONException{
        JSONObject answer = new JSONObject();


        answer.put(REQ_ID, "411");
        answer.put(ACTIVITY_ID, activity_id);
        answer.put(ACTIVITY, getActivityDetail(activity_id));
        answer.put(PROBLEMS, getProblemsByActivityForType4or7(activity_id));
        /*
            "problems": [ // 活动内所有题目，类型为4 7时使用
            {
                    "id": "1", // 题目id
                    "seq": 0, // 题目展示顺序，默认按此排列
                    "type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
                    "body": "下列式子中属于单项式的是", // 题干内容
                    "analysis": "", // 解答
                    "user_duration": 0, // 用户停留时间
                    "user_correct": 0, // 用户是否做对
                    "choices": [] // 选项或者填空
            }
         */

        return answer.toString();

    }

    private String getMediaPath(int id){
        Cursor cursor = mResovler.query(MetadataContract.Media.CONTENT_URI, null, MetadataContract.Media._STRING_ID + " = " + id, null,   null);
        cursor.moveToFirst();
        if (!cursor.isNull(cursor.getColumnIndex(MetadataContract.Media._PATH)))
            return CursorUtils.getString(cursor, MetadataContract.Media._PATH);
        else
            return "";
    }
}



