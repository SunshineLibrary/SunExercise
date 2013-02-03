package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import android.util.Log;
import android.webkit.DownloadListener;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.support.api.download.DownloadProgressListener;
import org.sunshinelibrary.support.api.download.DownloadStatus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-3
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class SyncFile {


    ContentResolver mResolver;
    SyncFileRequest mSyncFileRequest;




    public SyncFile() {
        this.mResolver = ExerciseApplication.getApplication().getContentResolver();
        mSyncFileRequest = new SyncFileRequest();
    }




    public void sync(){
        List<Integer> fileNeedToDownload = new ScanFile().getAllFileIDNeedToDownload();

        for (Integer fileID : fileNeedToDownload){
            int downloadID = fileID;//TODO downloadID?
            mSyncFileRequest.startDownload(downloadID);
        }
    }



}
