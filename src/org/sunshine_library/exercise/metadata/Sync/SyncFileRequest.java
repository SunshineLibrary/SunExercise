package org.sunshine_library.exercise.metadata.sync;

import android.net.Uri;
import android.util.Log;

import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.ApiUriBuilder;
import org.sunshinelibrary.support.api.download.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Bowen Sun
 * @version 1.0
 */
public class SyncFileRequest{


    Downloader downloader;
    Executor mExecutor;



    public SyncFileRequest() {
        downloader = ApiManager.getInstance(ExerciseApplication.getApplication()).getDownloader();
        mExecutor = Executors.newSingleThreadExecutor();
    }


    public void startDownload(final int id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Uri uri = ApiUriBuilder.getMediaDownloadUri(id);
                DownloadMonitor monitor = downloader.createNewDownload(new DownloadRequest(uri));
                monitor.setProgressListener(new DownloadProgressListener() {
                    @Override
                    public boolean handleFinishedDownload(final DownloadStatus downloadStatus, final boolean isSuccess) {
                        String str;
                        if (isSuccess) {
                            str = "Done downloading: " + downloadStatus.getSourceUri().getPath();
                        } else {
                            str = "Failed downloading: " + downloadStatus.getSourceUri().getPath();
                        }
                        Log.d("TAG", str);
                        return true;
                    }
                });
            }
        });
    }
}
