package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.ApiUriBuilder;
import org.sunshinelibrary.support.api.download.*;
import org.sunshinelibrary.support.api.file.FileInfo;
import org.sunshinelibrary.support.api.file.FileManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Bowen Sun
 * @version 1.0
 */
public class SyncFileRequest {


    Downloader mDownloader;
    Executor mExecutor;
    FileManager mFileManager;
    ContentResolver mResolver;
    Context mContext;


    public SyncFileRequest() {
        mContext = ExerciseApplication.getApplication();
        mDownloader = ApiManager.getInstance(mContext).getDownloader();
        mFileManager = ApiManager.getInstance(mContext).getFileManager();
        mResolver = mContext.getContentResolver();
        mExecutor = Executors.newSingleThreadExecutor();
    }


    public void startDownload(final int id) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Uri uri = ApiUriBuilder.getMediaDownloadUri(String.valueOf(id));
                DownloadMonitor monitor = mDownloader.createNewDownload(new DownloadRequest(uri));
                monitor.setProgressListener(new DownloadProgressListener() {
                    @Override
                    public boolean handleFinishedDownload(final DownloadStatus downloadStatus, final boolean isSuccess) {
                        long id = downloadStatus.getID();
                        if (isSuccess) {
                            String dmPath = downloadStatus.getFilePath();
                            FileInfo fileInfo = mFileManager.createFile(dmPath);


                            ContentValues values = new ContentValues();
                            values.put(MetadataContract.Media._STRING_ID, id);
                            values.put(MetadataContract.Media._FILE_ID, fileInfo.getId());
                            values.put(MetadataContract.Media._PATH, fileInfo.getAbsolutePath());
                            mResolver.insert(MetadataContract.Media.CONTENT_URI, values);
                        }
                        return true;
                    }
                });
            }
        });
    }
}
