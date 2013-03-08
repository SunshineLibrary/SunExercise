package org.sunshinelibrary.exercise.metadata.operation;

import android.database.Cursor;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.Media;

import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.sync.FileRequest;

import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/25/13
 * Trello:
 */
public class DownloadLessonOperation extends ExerciseOperation implements FileRequest.FileObserver {

    private static final String TAG = "DownloadLessonOperation";

    String mLessonId;

    public DownloadLessonOperation(String lessonId) {
        mLessonId = lessonId;
    }

    public void execute() {

        ArrayList<String> mediaIDs = CollectMediaIDs(mLessonId);

        if (mediaIDs.size() <= 0) {
            updateDownloadFinish(mLessonId, true);
//            PackApplication.getInstance().getSyncManager().notifyFolderComplete(mLessonId);
            return;
        }
//        FileRequest fr = new FileRequest(PackApplication.getInstance().getSyncManager().getApiManager(), this);
//        fr.addAll(mediaIDs);

        Cursor cursor = mContext.getContentResolver().query(Media.CONTENT_URI, new String[]{Media._STRING_ID,
                Media._PATH}, CursorUtils.generateSelectionStringForStringID(Media._STRING_ID, mediaIDs).toString(),
                null, null);
        while (cursor.moveToNext()) {
            int pathIndex = cursor.getColumnIndex(Media._PATH);
            String path = cursor.getString(pathIndex);
            File f = new File(path);
            if (f.exists()) {
                int idIndex = cursor.getColumnIndex(Media._STRING_ID);
                String id = cursor.getString(idIndex);
//                fr.remove(id);
                continue;
            }
        }
        cursor.close();

//        fr.requestDownload();
    }

    @Override
    public void onAllComplete() {
        CheckAvailableOperation co = new CheckAvailableOperation();
        co.add(mLessonId);
        co.execute();
//        PackApplication.getInstance().getSyncManager().notifyFolderComplete(mLessonId);
    }
}
