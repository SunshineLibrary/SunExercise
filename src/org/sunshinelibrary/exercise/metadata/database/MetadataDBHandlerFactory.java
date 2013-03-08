package org.sunshinelibrary.exercise.metadata.database;

import android.content.Context;
import org.sunshinelibrary.exercise.metadata.database.tables.*;
import org.sunshinelibrary.support.utils.database.DBHandler;

public class MetadataDBHandlerFactory {

    private static final int DB_VERSION = 100;
    private static final String DB_NAME = "metadata";

    public static DBHandler newMetadataDBHandler(Context context) {
        DBHandler dbHandler = new DBHandler(context, DB_NAME, DB_VERSION);
        initNormalTables(dbHandler);
        return dbHandler;
    }
    
    private static void initNormalTables(DBHandler dbHandler) {
        dbHandler.addTable(SubjectTable.TABLE_NAME, new SubjectTable(dbHandler));
        dbHandler.addTable(LessonTable.TABLE_NAME, new LessonTable(dbHandler));
        dbHandler.addTable(StageTable.TABLE_NAME, new StageTable(dbHandler));
        dbHandler.addTable(SectionTable.TABLE_NAME, new SectionTable(dbHandler));
        dbHandler.addTable(ActivityTable.TABLE_NAME, new ActivityTable(dbHandler));
        dbHandler.addTable(ProblemTable.TABLE_NAME, new ProblemTable(dbHandler));
        dbHandler.addTable(ProblemChoiceTable.TABLE_NAME, new ProblemChoiceTable(dbHandler));
        dbHandler.addTable(MediaTable.TABLE_NAME, new MediaTable(dbHandler));

    }
}
