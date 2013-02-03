package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import org.sunshine_library.exercise.app.application.ExerciseApplication;

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
        List<Integer> fileNeedToDownload = new ScanFileForDownload().getAllMediaIDNeedToDownload();
        for (Integer mediaID : fileNeedToDownload){
            int downloadID = mediaID;
            mSyncFileRequest.startDownload(downloadID);
        }
    }


}
