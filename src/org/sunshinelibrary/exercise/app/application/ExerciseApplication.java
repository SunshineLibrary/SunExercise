package org.sunshinelibrary.exercise.app.application;

import android.app.Application;

import android.util.Log;
import org.sunshinelibrary.exercise.metadata.MetadataContract;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.Lessons;
import static org.sunshinelibrary.support.utils.database.Contract.DOWNLOAD_STATUS;
import org.sunshinelibrary.exercise.metadata.database.MetadataDBHandlerFactory;
import org.sunshinelibrary.exercise.metadata.sync.ExerciseDeprecatedSyncManager;
import org.sunshinelibrary.exercise.metadata.sync.Proxy;
import org.sunshinelibrary.support.utils.ApplicationInterface;
import org.sunshinelibrary.support.utils.database.Contract;
import org.sunshinelibrary.support.utils.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/9/13
 * Usage: Global environment
 */
public class ExerciseApplication  extends Application {
    private DBHandler metadataDBHandler;
    private Proxy mSyncManager;
    static  ExerciseApplication application;

    public ExerciseApplication() {
        super();
        application = this;
        ApplicationInterface.setApplication(this);
        mSyncManager = new Proxy();
        Contract.setAuthorityUri(MetadataContract.AUTHORITY_URI);
    }

    public synchronized DBHandler getMetadataDBHandler() {
        if (metadataDBHandler == null) {
            metadataDBHandler = MetadataDBHandlerFactory.newMetadataDBHandler(this);
        }
        return metadataDBHandler;
    }

    public synchronized Proxy getSyncManager() {
        return mSyncManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Proxy.updateDownloadFinish(Lessons.CONTENT_URI, DOWNLOAD_STATUS.DOWNLOADING, DOWNLOAD_STATUS.NONE);
        Proxy.updateDownloadFinish(Lessons.CONTENT_URI, DOWNLOAD_STATUS.WAITING, DOWNLOAD_STATUS.NONE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        closeHandler();
    }

    private void closeHandler() {
        if (metadataDBHandler != null) {
            metadataDBHandler.close();
        }
    }

    public static ExerciseApplication getInstance(){
        return application;
    }


}
