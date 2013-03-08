package org.sunshinelibrary.exercise.app.application;

import android.app.Application;

import org.sunshinelibrary.exercise.metadata.database.MetadataDBHandlerFactory;
import org.sunshinelibrary.support.utils.ApplicationInterface;
import org.sunshinelibrary.support.utils.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/9/13
 * Usage: Global environment
 */
public class ExerciseApplication  extends Application {
    private DBHandler metadataDBHandler;
    static  ExerciseApplication application;

    public ExerciseApplication() {
        super();
        application = this;
        ApplicationInterface.setApplication(this);
    }

    public synchronized DBHandler getMetadataDBHandler() {
        if (metadataDBHandler == null) {
            metadataDBHandler = MetadataDBHandlerFactory.newMetadataDBHandler(this);
        }
        return metadataDBHandler;
    }


    @Override
    public void onCreate() {
        super.onCreate();
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

    public static Application getInstance(){
        return application;
    }


}
