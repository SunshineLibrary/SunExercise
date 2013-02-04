package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.database.DBHandler;
import org.sunshine_library.exercise.metadata.MetadataContract.ProblemChoices;


/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午5:5
 */
public class ProblemChoiceTable extends  AbstractTable{
    public static final String TABLE_NAME = "problem_choices";

    public static final String[] ALL_COLUMNS = {
            ProblemChoices._IDENTIFIER,
            ProblemChoices._PARENT_IDENTIFIER,
            ProblemChoices._SEQUENCE,
            ProblemChoices._DISPLAY_TEXT,
            ProblemChoices._ANSWER,
            ProblemChoices._USER_CHOICE
    };



    public static final String[][] COLUMN_DEFINITIONS = {
        {ProblemChoices._IDENTIFIER, "INTEGER PRIMARY KEY"},
        {ProblemChoices._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
        {ProblemChoices._SEQUENCE, "INTEGER"},
        {ProblemChoices._DISPLAY_TEXT,"TEXT"},
        {ProblemChoices._ANSWER, "TEXT"},
        {ProblemChoices._USER_CHOICE, "TEXT"}
    };


    public ProblemChoiceTable(DBHandler db){
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

}
