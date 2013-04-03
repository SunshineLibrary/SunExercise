package org.sunshinelibrary.exercise.metadata.sync;

import android.database.Cursor;
import android.util.Log;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.MetadataContract;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.operation.DeleteFileOperation;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/30/13
 * Trello:
 */
public class DeleteUnusedFilesRequest {
    public int request() {
        Cursor cursor = ExerciseApplication.getInstance().getContentResolver()
                .query(MetadataContract.Media.getMediaDeleteUnusedFileUri(), null, null, null, null);
        DeleteFileOperation dfo = new DeleteFileOperation(ApiManager.getInstance(ExerciseApplication.getInstance()
            .getBaseContext()).getFileManager());
        Log.i("DeleteUnusedFilesRequest", "count :" + cursor.getCount());
        while (cursor.moveToNext()) {
            int fileId = cursor.getInt(cursor.getColumnIndex(MetadataContract.Media._FILE_ID));
            dfo.add(fileId);
        }
        cursor.close();
        // TODO: test code
        dfo.start();
        return ExerciseApplication.getInstance().getContentResolver().delete(MetadataContract.Media.CONTENT_URI,
                CursorUtils.generateSelectionStringForIntegerID(MetadataContract.Media._FILE_ID, dfo.getFileIDs()), null);
    }
}
