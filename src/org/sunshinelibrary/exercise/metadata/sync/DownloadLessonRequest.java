package org.sunshinelibrary.exercise.metadata.sync;

import org.sunshinelibrary.exercise.metadata.operation.DownloadLessonOperation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/29/13
 * Trello:
 */
public class DownloadLessonRequest extends Thread {
    ArrayList<String> mFolderIDs = new ArrayList<String>();

    public DownloadLessonRequest(ArrayList<String> folderIDs) {
        mFolderIDs.addAll(folderIDs);
    }

    @Override
    public void run() {
        for (String folderId : mFolderIDs) {
            DownloadLessonOperation dfo = new DownloadLessonOperation(folderId);
            dfo.execute();
        }
    }
}
