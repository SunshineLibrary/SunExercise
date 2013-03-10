package org.sunshinelibrary.exercise.metadata.operation;

import android.content.ContentValues;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.Media;
import org.sunshinelibrary.support.api.file.FileInfo;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/25/13
 * Trello:
 */
public class InsertFileOperation extends ExerciseOperation {

    private FileInfo mFileInfo;
    private String mMediaID;

    public InsertFileOperation(FileInfo fi, String mediaID) {
        mFileInfo = fi;
        mMediaID = mediaID;
    }

    public void execute() {
        ContentValues values = new ContentValues();
        values.put(Media._STRING_ID, mMediaID);
        values.put(Media._FILE_ID, mFileInfo.getId());
        values.put(Media._PATH, mFileInfo.getAbsolutePath());
        mContext.getContentResolver().insert(Media.CONTENT_URI, values);
    }
}
