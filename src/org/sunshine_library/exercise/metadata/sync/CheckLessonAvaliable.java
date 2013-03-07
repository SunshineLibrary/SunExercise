package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-3
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class CheckLessonAvaliable {

    ContentResolver mResovler;

    public CheckLessonAvaliable() {
        mResovler = ExerciseApplication.getApplication().getContentResolver();
    }

    public void check() {
        Cursor lessonCursor = mResovler.query(MetadataContract.Lessons.CONTENT_URI, null, null, null, null);
        while (lessonCursor.moveToNext()) {
            int lesson_id = lessonCursor.getInt(lessonCursor.getColumnIndex(MetadataContract.Lessons._STRING_ID));

            ContentValues values = new ContentValues();
            if (!isLessonAvaliable(lesson_id)) {
                values.put(MetadataContract.Lessons._IS_AVALIBLE, MetadataContract.Lessons.AVALIABLE);
            } else {
                values.put(MetadataContract.Lessons._IS_AVALIBLE, MetadataContract.Lessons.NOT_AVALIABLE);

            }
            mResovler.update(MetadataContract.Lessons.CONTENT_URI, values,
                    MetadataContract.Lessons._STRING_ID + " = " + lesson_id, null);
        }
        lessonCursor.close();
    }

    private boolean isLessonAvaliable(int lesson_id) {
        Cursor stageCursor = mResovler.query(MetadataContract.Stages.CONTENT_URI, null,
                MetadataContract.Stages._PARENT_ID + " = " + lesson_id, null, null);
        while (stageCursor.moveToNext()) {
            int stage_id = stageCursor.getInt(stageCursor.getColumnIndex(MetadataContract.Stages._STRING_ID));
            if (!isStageAvailable(stage_id)) {
                stageCursor.close();
                return false;
            }
        }
        stageCursor.close();
        return true;
    }


    private boolean isStageAvailable(int stage_id) {
        Cursor sectionCursor = mResovler.query(MetadataContract.Sections.CONTENT_URI, null,
                MetadataContract.Sections._PARENT_ID + " = " + stage_id, null, null);
        while (sectionCursor.moveToNext()) {
            int section_id = sectionCursor.getInt(sectionCursor.getColumnIndex(MetadataContract.Sections._STRING_ID));
            if (!isSectionAvailable(section_id)){
                sectionCursor.close();
                return false;
            }
        }
        sectionCursor.close();
        return true;
    }


    private boolean isSectionAvailable(int section_id) {
        Cursor cursor = mResovler.query(MetadataContract.Activities.CONTENT_URI, null,
                MetadataContract.Activities._PARENT_ID + " = " + section_id, null, null);
        while (cursor.moveToNext()) {
            if (!isActivityAvailable(cursor)){
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }

    private boolean isActivityAvailable(Cursor activityCursor) {
        // 在Contract的所有表中Media_ID的定义上设置了默认为""的空字符串作为默认值
        if (activityCursor.isNull(activityCursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID)))          //TODO
            return false;
        int activity_id = activityCursor.getInt(activityCursor.getColumnIndex(MetadataContract.Activities._STRING_ID));
        Cursor problemCursor = mResovler.query(MetadataContract.Problems.CONTENT_URI, null,
                MetadataContract.Problems._PARENT_ID + " = " + activity_id, null, null);
        while (problemCursor.moveToNext()) {
            if (problemCursor.isNull(problemCursor.getColumnIndex(MetadataContract.Problems._MEDIA_ID)))   //TODO
                return false;
        }
        return true;
    }
}
