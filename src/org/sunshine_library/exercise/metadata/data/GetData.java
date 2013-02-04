package org.sunshine_library.exercise.metadata.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.ViewDebug;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;


/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-20
 * Time: 下午12:46
 */


public class GetData {

    public static final String NOT_FOUND = "not found";
    public static final String WRONG_MESSAGE = "can not parse json";
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


    private JSONObject ask;
    private ContentResolver resolver;
    private Gson gson = new Gson();


    public GetData() {
        resolver = ExerciseApplication.getApplication().getApplicationContext().getContentResolver();
    }

    public String get(String reqJson) {

        try {
            ask = new JSONObject(reqJson);
            int req_id = ask.getInt(REQ_ID);
            switch (req_id) {
                case 201:
                    return get201();
                case 211:
                    int subject_id = ask.getInt(SUBJECT_ID);
                    return get211(subject_id);
                case 301:
                    int lesson_id = ask.getInt(LESSON_ID);
                    return get301(lesson_id);
                case 401:
                    int activity_id = ask.getInt(ACTIVITY_ID);
                    int stage_id = ask.getInt(STAGE_ID);
                    return get401(activity_id, stage_id);
                case 411:
                    stage_id = ask.getInt(STAGE_ID);
                    activity_id = ask.getInt(ACTIVITY_ID);
                    return get411(activity_id, stage_id);
                default:
                    return NOT_FOUND;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return WRONG_MESSAGE;
        }
    }

    public String get201() {
        JsonObject answer = new JsonObject();

        int first_subject_id = 0;
        Cursor cursor = resolver.query(MetadataContract.Subjects.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            first_subject_id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Subjects._IDENTIFIER));
        }

        answer.addProperty(REQ_ID, "201");
        answer.add(SUBJECTS, getAllSubject());

        answer.add(LESSONS, getLessonsBySubject(first_subject_id));
        return gson.toJson(answer);

        //TODO tage_count
    }

    private JsonObject getSubject(Cursor cursor) {
            /*
            {
                "id"  : "subject1", // 科目ID
                "name": "数学",     // 科目名
             }
             */
        JsonObject subject = new JsonObject();

        int columeID = cursor.getColumnIndex(MetadataContract.Subjects._IDENTIFIER);
        int columeName = cursor.getColumnIndex(MetadataContract.Subjects._NAME);

        int id = cursor.getInt(columeID);
        String name = cursor.getString(columeName);

        subject.addProperty(ID, id);
        subject.addProperty(NAME, name);

        return subject;
    }

    private JsonArray getAllSubject() {
        JsonArray subjects = new JsonArray();
        Cursor cursor = resolver.query(MetadataContract.Subjects.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            subjects.add(getSubject(cursor));

        }
        return subjects;

    }


    private JsonArray getLessonsBySubject(int subject_id) {

        JsonArray lessons = new JsonArray();
        Cursor cursor = resolver.query(MetadataContract.Lessons.CONTENT_URI, null,
                MetadataContract.Lessons._PARENT_IDENTIFIER + " = " + subject_id, null, null);
        while (cursor.moveToNext()) {
            lessons.add(getLesson(cursor));
        }
        return lessons;
    }

    private JsonObject getLesson(Cursor cursor) {


        JsonObject lesson = new JsonObject();


        int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Lessons._IDENTIFIER));
        int subject_id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Lessons._PARENT_IDENTIFIER));
        String name = cursor.getString(cursor.getColumnIndex(MetadataContract.Lessons._NAME));
        String datatime = cursor.getString(cursor.getColumnIndex(MetadataContract.Lessons._TIME));
        int userProgress = cursor.getInt(cursor.getColumnIndex(MetadataContract.Lessons._USER_PROGRESS));
        float userResult = cursor.getFloat(cursor.getColumnIndex(MetadataContract.Lessons._USER_RESULT));


        Cursor stageCursor = resolver.query(MetadataContract.Stages.CONTENT_URI, null,
                MetadataContract.Stages._PARENT_IDENTIFIER + " = " + id, null,
                MetadataContract.Stages._SEQUENCE);
        int stageCount = stageCursor.getCount();

        lesson.addProperty(ID, id);
        lesson.addProperty(NAME, name);
        lesson.addProperty(TIME, datatime);
        lesson.addProperty(USER_PROGRESS, userProgress);
        lesson.addProperty(USER_RESULT, userResult);
        lesson.addProperty(STAGE_COUNT, stageCount);
        lesson.addProperty(STAGE_PERCENTAGE, userResult);


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

    public String get211(int subject_id) {
        JsonObject answer = new JsonObject();

        answer.addProperty(REQ_ID, "211");
        answer.addProperty(SUBJECT_ID, subject_id);
        answer.add(LESSONS, getLessonsBySubject(subject_id));

        return gson.toJson(answer);

    }

    public String get301(int lesson_id) {

        JsonObject answer = new JsonObject();


        answer.addProperty(REQ_ID, "301");
        answer.addProperty(LESSON_ID, lesson_id);
        answer.add(LESSONS, getStagesByLesson(lesson_id));

        return gson.toJson(answer);
    }

    private JsonArray getStagesByLesson(int lesson_id) {
        Cursor cursor = resolver.query(MetadataContract.Stages.CONTENT_URI, null,
                MetadataContract.Stages._PARENT_IDENTIFIER + " = " + String.valueOf(lesson_id), null,
                MetadataContract.Stages._SEQUENCE);
        JsonArray stages = new JsonArray();
        while (cursor.moveToNext()) {
            stages.add(getStage(cursor));
        }
        return stages;
    }

    private JsonObject getStage(Cursor cursor) {
        JsonObject stage = new JsonObject();

        int columeID = cursor.getColumnIndex(MetadataContract.Stages._IDENTIFIER);
        int columeSequence = cursor.getColumnIndex(MetadataContract.Stages._SEQUENCE);
        int columnType = cursor.getColumnIndex(MetadataContract.Stages._TYPE);
        int columnUserProgress = cursor.getColumnIndex(MetadataContract.Stages._USER_PROGRESS);


        int id = cursor.getInt(columeID);
        int sequence = cursor.getInt(columeSequence);
        int type = cursor.getInt(columnType);
        int userProgress = cursor.getInt(columnUserProgress);


        float userPrecentage = cursor.getFloat(cursor.getColumnIndex(MetadataContract.Stages._USER_PERCENTAGE));


        stage.addProperty(ID, id);
        stage.addProperty(SEQ, sequence);
        stage.addProperty(TYPE, type);
        stage.addProperty(USER_PROGRESS, userProgress);
        stage.addProperty(USER_PRECENTAGE, userPrecentage);


        /*
            "id": "stage2",
            "seq": 2,
            "type": 2,
            "user_percentage": 0.5, // 用户的进度百分比，目前是下级所有section下所有activity的完成百分比
            "user_progress": "activity3" // 当前进度，目前section没有单独的展示页面，直接指向activityID，done表示已完成，空表示未开始。
         */
        return stage;
    }


    public String get401(int activity_id, int stage_id) {
        JsonObject answer = new JsonObject();

        answer.addProperty(REQ_ID, "401");
        answer.addProperty(STAGE_ID, stage_id);
        answer.addProperty(ACTIVITY_ID, activity_id);
        answer.add(ACTIVITYS, getActivitiesByStage(stage_id));
        answer.add(ACTIVITY, getActivityDetail(activity_id));
        answer.add(PROBLEMS, getProblemsByActivityForType4or7(activity_id));
        return gson.toJson(answer);
    }

    private JsonArray getActivitiesByStage(int stage_id) {
        JsonArray activitys = new JsonArray();
        Cursor cursorSection = resolver.query(MetadataContract.Sections.CONTENT_URI, null,
                MetadataContract.Sections._IDENTIFIER + " = " + String.valueOf(stage_id), null,
                MetadataContract.Sections._SEQUENCE);
        while (cursorSection.moveToNext()) {
            int sectionID = cursorSection.getInt(cursorSection.getColumnIndex(MetadataContract.Sections._IDENTIFIER));
            Cursor cursorActivity = resolver.query(MetadataContract.Activities.CONTENT_URI, null,
                    MetadataContract.Activities._IDENTIFIER + " = " + String.valueOf(sectionID), null,
                    MetadataContract.Activities._SEQUENCE);
            while (cursorActivity.moveToNext()) {
                activitys.add(getActivityEssential(cursorActivity));
            }
        }
        return activitys;
    }

    private JsonObject getActivityEssential(Cursor cursor) {
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

        JsonObject activity = new JsonObject();

        int columeID = cursor.getColumnIndex(MetadataContract.Activities._IDENTIFIER);
        int columnSection = cursor.getColumnIndex(MetadataContract.Activities._PARENT_IDENTIFIER);
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
        Cursor sectionCousur = resolver.query(MetadataContract.Sections.CONTENT_URI, null, MetadataContract.Sections._IDENTIFIER + " = " + String.valueOf(sectionId), null, null);
        if (sectionCousur.moveToFirst()) {
            int columnSectionName = cursor.getColumnIndex(MetadataContract.Sections._NAME);
            sectionName = cursor.getString(columnSectionName);
        }

        activity.addProperty(ID, id);
        activity.addProperty(SECTION_ID, sectionId);
        activity.addProperty(SECTION_NAME, sectionName);
        activity.addProperty(SEQ, seq);
        activity.addProperty(TYPE, type);
        activity.addProperty(NAME, name);

        return activity;
    }

    private JsonObject getActivityDetail(int activity_id) {
        Cursor cursor = resolver.query(MetadataContract.Activities.CONTENT_URI, null, MetadataContract.Activities._IDENTIFIER + " + " + activity_id, null, null);
        cursor.moveToFirst();
        return getActivityDetail(cursor);
    }

    private JsonObject getActivityDetail(Cursor cursor) {

        JsonObject activity = new JsonObject();


        int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._IDENTIFIER));
        int sectionId = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._PARENT_IDENTIFIER));
        int seq = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._SEQUENCE));
        int type = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._NAME));
        String name = cursor.getString(cursor.getColumnIndex(MetadataContract.Activities._TYPE));
        int userProgress = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._USER_PROGRESS));

        String sectionName = new String();
        Cursor sectionCousur = resolver.query(MetadataContract.Sections.CONTENT_URI, null,
                MetadataContract.Sections._IDENTIFIER + " = " + String.valueOf(sectionId), null,
                MetadataContract.Sections._SEQUENCE);
        if (sectionCousur.moveToFirst()) {
            int columnSectionName = cursor.getColumnIndex(MetadataContract.Sections._NAME);
            sectionName = cursor.getString(columnSectionName);
        }


        activity.addProperty(ID, id);
        activity.addProperty(SECTION_ID, sectionId);
        activity.addProperty(SECTION_NAME, sectionName);
        activity.addProperty(SEQ, seq);
        activity.addProperty(TYPE, type);
        activity.addProperty(NAME, name);
        activity.addProperty(JUNP_CONDITION, cursor.getString(cursor.getColumnIndex(MetadataContract.Activities._JUMP_CONDITION)));
        activity.addProperty(USER_PROGRESS, userProgress);
        activity.addProperty(USER_DURATION, cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._USER_DURATION)));
        activity.addProperty(USER_CORRECT, cursor.getFloat(cursor.getColumnIndex(MetadataContract.Activities._USER_CORRECT)));
        activity.addProperty(USER_SCORE, cursor.getFloat(cursor.getColumnIndex(MetadataContract.Activities._USER_SCORE)));
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
        "weight": 1,
        "jump_condition": [

        ],
        "user_progress": "problem2",
        "user_duration": "",
        "user_correct": 0,
        "user_score": 0,
       */
    }

    private JsonArray getProblemsByActivityForType4or7(int activity_id) {
        JsonArray problems = new JsonArray();
        String select = MetadataContract.Problems._PARENT_IDENTIFIER + " = " + String.valueOf(activity_id) + " AND " +
                "(" + MetadataContract.Problems._TYPE + " = " + 4 + " OR " + MetadataContract.Problems._TYPE + " = " + 7 + " )";

        Cursor cursor = resolver.query(MetadataContract.Problems.CONTENT_URI, null, select, null, MetadataContract.ProblemChoices._SEQUENCE);
        while (cursor.moveToNext()) {
            problems.add(getProblem(cursor));

        }

        return problems;
    }

    private JsonObject getProblem(Cursor cursor) {

        JsonObject problem = new JsonObject();

        int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._IDENTIFIER));

        problem.addProperty(ID, id);
        problem.addProperty(SEQ, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._SEQUENCE)));
        problem.addProperty(TYPE, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._TYPE)));
        problem.addProperty(BODY, cursor.getString(cursor.getColumnIndex(MetadataContract.Problems._BODY)));
        problem.addProperty(ANALYSIS, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._ANALYSIS)));
        problem.addProperty(USER_DURATION, cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._USER_DURATION)));
        problem.addProperty(USER_CORRECT, cursor.getFloat(cursor.getColumnIndex(MetadataContract.Problems._USER_CORRECT)));
        problem.add(CHOICES, getProblemChoiceByProblem(id));

        return problem;
          /*
         "problems": [
        {
            "id": "problem131",
            "seq": 0,
            "type": 0,
            "body": "下列式子中属于单项式的是",
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

    private JsonArray getProblemChoiceByProblem(int problems_id) {
        Cursor choiceCursor = resolver.query(MetadataContract.ProblemChoices.CONTENT_URI, null,
                MetadataContract.ProblemChoices._PARENT_IDENTIFIER + " = " + problems_id, null, MetadataContract.ProblemChoices._SEQUENCE);
        JsonArray choices = new JsonArray();
        while (choiceCursor.moveToNext()) {
            choices.add(getProblemChoice(choiceCursor));
        }
        return choices;
    }

    private JsonObject getProblemChoice(Cursor cursor) {
        JsonObject choice = new JsonObject();

        choice.addProperty(ID, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._IDENTIFIER)));
        choice.addProperty(SEQ, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._SEQUENCE)));
        choice.addProperty(BODY, cursor.getString(cursor.getColumnIndex(MetadataContract.ProblemChoices._DISPLAY_TEXT)));
        choice.addProperty(ANSWER, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._ANSWER)));
        choice.addProperty(USER_CHOICE, cursor.getInt(cursor.getColumnIndex(MetadataContract.ProblemChoices._USER_CHOICE)));

        return choice;

        /*
                    "id": "choice1",
                    "seq": 2,
                    "body": "axy",
                    "answer": 0,
                    "user_choice": 0
         */
    }

    public String get411(int activity_id, int stage_id) {
        JsonObject answer = new JsonObject();


        answer.addProperty(REQ_ID, "411");
        answer.addProperty(ACTIVITY_ID, activity_id);
        answer.add(ACTIVITY, getActivityDetail(activity_id));
        answer.add(PROBLEMS, getProblemsByActivityForType4or7(activity_id));
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

        return gson.toJson(answer);

    }
}



