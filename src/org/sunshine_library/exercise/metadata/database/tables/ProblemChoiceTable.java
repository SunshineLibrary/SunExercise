package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午5:5
 */
public class ProblemChoiceTable extends  AbstractTable{
    public static final String TABLE_NAME = "problem_choices";

    public static final String[] ALL_COLUMNS = {
            MetadataContract.ProblemChoices._IDENTIFIER,
            MetadataContract.ProblemChoices._PARENT_IDENTIFIER,
            MetadataContract.ProblemChoices._SEQUENCE,
            MetadataContract.ProblemChoices._DISPLAY_TEXT,
            MetadataContract.ProblemChoices._ANSWER,
            MetadataContract.ProblemChoices._USER_CHOICE
    };



    public static final String[][] COLUMN_DEFINITIONS = {
        {MetadataContract.ProblemChoices._IDENTIFIER, "INTEGER PRIMARY KEY"},
        {MetadataContract.ProblemChoices._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
        {MetadataContract.ProblemChoices._SEQUENCE, "INTEGER"},
        {MetadataContract.ProblemChoices._DISPLAY_TEXT,"TEXT"},
        {MetadataContract.ProblemChoices._ANSWER, "TEXT"},
        {MetadataContract.ProblemChoices._USER_CHOICE, "TEXT"}
    };


    public ProblemChoiceTable(DBHandler db){
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

}
