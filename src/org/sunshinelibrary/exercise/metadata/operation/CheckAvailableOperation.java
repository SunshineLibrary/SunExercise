package org.sunshinelibrary.exercise.metadata.operation;

import android.database.Cursor;
import android.util.Log;
import org.sunshinelibrary.exercise.metadata.MetadataContract;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.database.Contract;
import static org.sunshinelibrary.support.utils.database.Contract.DOWNLOAD_STATUS;

import java.io.File;
import java.util.ArrayList;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Media;


/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/25/13
 * Trello:
 */
public class CheckAvailableOperation extends ExerciseOperation {

    private static final String TAG = "CheckAvailableOperation";

    ArrayList<String> mLessonIDs;
    boolean mAvailable = false;

    public CheckAvailableOperation() {
        mLessonIDs = new ArrayList<String>();
    }

    public void CheckAvailableOperation(ArrayList<String> lessonIDs) {
        mLessonIDs = lessonIDs;
    }

    public void addAll() {
        mLessonIDs = CollectLessonIDs(AllSubjectIDs());
    }

    public void add(String lessonId) {
        if (lessonId != null && !lessonId.equals("") && !mLessonIDs.contains(lessonId))
            mLessonIDs.add(lessonId);
    }

    public boolean available(){
        return mAvailable;
    }


    // 接在Json数据处理完成后执行，执行后直接通知前端，必须同步执行。
    public void execute() {

        if (mLessonIDs == null || mLessonIDs.size() <= 0) {
            return;
        }

        ArrayList<String> mediaIDs;
        ArrayList<String> deleted = new ArrayList<String>();
        Cursor cursor;

        for (String lessonId : mLessonIDs) {

            mediaIDs = this.CollectMediaIDs(lessonId);
//           	Log.i(TAG, "check mediaIDs" + mediaIDs.toString());
            if (mediaIDs.size() <= 0) {
                updateDownloadStatusById(MetadataContract.Lessons.CONTENT_URI, lessonId, DOWNLOAD_STATUS.DOWNLOADED);
                continue;
            }

            mAvailable = true;

            cursor = mContext.getContentResolver().query(Media.CONTENT_URI, new String[]{Media._PATH},
                    CursorUtils.generateSelectionStringForStringID(Media._STRING_ID, mediaIDs), null, null);
            cursor.moveToFirst();
            if(mediaIDs.size() != cursor.getCount()){
                mAvailable = false;
            }
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                int pathIndex = cursor.getColumnIndex(Media._PATH);
                String path = cursor.getString(pathIndex);
                File f = new File(path);
                if (!f.exists()) {
                    Log.i(TAG, "file not exist:" + path);
                    deleted.add(path);
                    mAvailable = false;
                }
            }
            cursor.close();
            updateDownloadStatusById(MetadataContract.Lessons.CONTENT_URI, lessonId,
                    mAvailable?DOWNLOAD_STATUS.DOWNLOADED:DOWNLOAD_STATUS.NONE);
        }

        int count = mContext.getContentResolver().delete(Media.CONTENT_URI,
                CursorUtils.generateSelectionStringForStringID(Media._PATH, deleted), null);
        Log.i(TAG, "Total " + count + " record(s) deleted in media table");
    }
}
