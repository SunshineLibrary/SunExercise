package org.sunshine_library.exercise.metadata.database;

import android.content.Context;
import org.sunshine_library.exercise.metadata.database.tables.*;

public class MetadataDBHandlerFactory {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "metadata";

    public static DBHandler newMetadataDBHandler(Context context) {
        DBHandler dbHandler = new DBHandler(context, DB_NAME, DB_VERSION);

        initNormalTables(dbHandler);
        initObservableTables(dbHandler, context);

        return dbHandler;
    }
    
    private static void initNormalTables(DBHandler dbHandler) {
        dbHandler.addTableManager(SubjectTable.TABLE_NAME, new SubjectTable(dbHandler));
        dbHandler.addTableManager(LessonTable.TABLE_NAME, new LessonTable(dbHandler));
        dbHandler.addTableManager(StageTable.TABLE_NAME, new StageTable(dbHandler));
        dbHandler.addTableManager(SectionTable.TABLE_NAME, new SectionTable(dbHandler));
        dbHandler.addTableManager(ActivityTable.TABLE_NAME, new ActivityTable(dbHandler));
        dbHandler.addTableManager(ProblemTable.TABLE_NAME, new ProblemTable(dbHandler));
        dbHandler.addTableManager(ProblemChoiceTable.TABLE_NAME, new ProblemChoiceTable(dbHandler));
        dbHandler.addTableManager(FileTable.TABLE_NAME, new FileTable(dbHandler));

    }

    private static void initObservableTables(DBHandler dbHandler, Context context){

    }
}
