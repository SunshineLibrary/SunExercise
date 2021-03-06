package org.sunshinelibrary.exercise.metadata.operation;

import android.content.ContentValues;
import android.database.Cursor;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.Media;

import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.MetadataContract;
import org.sunshinelibrary.exercise.metadata.MetadataContract.Lessons;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.database.Contract;
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
            updateDownloadStatusById(MetadataContract.Lessons.CONTENT_URI, mLessonId,
                    Contract.DOWNLOAD_STATUS.DOWNLOADED);
            ExerciseApplication.getInstance().getSyncManager().notifyCollectionDownloaded(mLessonId, true);
            return;
        }
        FileRequest fr = new FileRequest(ApiManager.getInstance(ExerciseApplication.getInstance().getBaseContext()),
                this, Media.CONTENT_URI);
        fr.addAll(mediaIDs);

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
                fr.remove(id);
                continue;
            }
        }
        cursor.close();

        fr.requestDownload();
    }

    @Override
    public void onProgressUpdate(float percentage) {
        ContentValues values = new ContentValues();
        values.put(Lessons._DOWNLOAD_FINISH, String.valueOf(percentage));
        mContext.getContentResolver().update(Lessons.CONTENT_URI, values, Lessons._STRING_ID + "=?",
                new String[]{mLessonId});
        ExerciseApplication.getInstance().getSyncManager().notifyCollectionDownloadProgress(mLessonId, percentage);
    }

    @Override
    public void onAllComplete() {
        CheckAvailableOperation co = new CheckAvailableOperation();
        co.add(mLessonId);
        co.execute();
        ExerciseApplication.getInstance().getSyncManager().notifyCollectionDownloaded(mLessonId, co.available());
    }
}
