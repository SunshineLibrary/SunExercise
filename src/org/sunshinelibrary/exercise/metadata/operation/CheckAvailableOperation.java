package org.sunshinelibrary.exercise.metadata.operation;

import android.database.Cursor;
import android.util.Log;
import org.sunshinelibrary.support.utils.CursorUtils;

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


    // 接在Json数据处理完成后执行，执行后直接通知前端，必须同步执行。
    public void execute() {

        if (mLessonIDs == null || mLessonIDs.size() <= 0) {
            return;
        }

        boolean available;
        ArrayList<String> mediaIDs;
        Cursor cursor;

        for (String lessonId : mLessonIDs) {

            mediaIDs = this.CollectMediaIDs(lessonId);
//           	Log.i(TAG, "check mediaIDs" + mediaIDs.toString());
            if (mediaIDs.size() <= 0) {
                updateDownloadFinish(lessonId, true);
                continue;
            }

            available = true;

            cursor = mContext.getContentResolver().query(Media.CONTENT_URI, new String[]{Media._PATH},
                    CursorUtils.generateSelectionStringForStringID(Media._STRING_ID, mediaIDs), null, null);
            if(mediaIDs.size() == cursor.getCount()){
                while (cursor.moveToNext()) {
                    int pathIndex = cursor.getColumnIndex(Media._PATH);
                    String path = cursor.getString(pathIndex);
                    File f = new File(path);
                    if (!f.exists()) {
                        Log.i(TAG, "file not exist:" + path);
                        available = false;
                        break;
                    }
                }
            } else {
                available = false;
            }
            cursor.close();
            updateDownloadFinish(lessonId, available);
        }
    }
}
