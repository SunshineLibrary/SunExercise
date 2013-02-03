package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import android.database.Cursor;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.data.GetData;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-3
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class CheckAvailable {

    ContentResolver mResovler;




    public CheckAvailable() {
        this.mResovler = ExerciseApplication.getApplication().getContentResolver();
    }

    public void check(){
        Cursor lessonCuror = mResovler.query(MetadataContract.Lessons.CONTENT_URI, null, null, null, null);
        while (lessonCuror.moveToNext()){
            if (!isLessonAvaliable()){
                //TODO
            }
        }
    }

    private boolean isLessonAvaliable() {
        return true //TODO
    }


    private boolean isProblemAcaiable(int id){
        Cursor cursor = mResovler.query(MetadataContract.Problems.CONTENT_URI, null,
                MetadataContract.Problems._IDENTIFIER + " = " + id , null, null);
        if (cursor.moveToFirst()){
            if (cursor.isNull(cursor.getColumnIndex(MetadataContract.Problems._FILE_ID)))
                return false; //TODO 如何判断是否有文件？
        }
        return true;
    }


    private boolean isActivityAcaiable(int id){
        Cursor cursor = mResovler.query(MetadataContract.Activities.CONTENT_URI, null,
                MetadataContract.Activities._IDENTIFIER + " = " + id , null, null);
        if (cursor.moveToFirst()){
            if (cursor.isNull(cursor.getColumnIndex(MetadataContract.Activities._FILE_ID)))
                return false; //TODO 如何判断是否有文件？
        }
        return true;
    }


}
