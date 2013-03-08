package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.exercise.metadata.MetadataContract.ProblemChoices;
import org.sunshinelibrary.support.utils.database.DBHandler;


/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午5:5
 */
public class ProblemChoiceTable extends  CustomizedAbstractTable{
    public static final String TABLE_NAME = "problem_choices";

    public static final String[] ALL_COLUMNS = {
            ProblemChoices._STRING_ID,
            ProblemChoices._PARENT_ID,
            ProblemChoices._SEQUENCE,
            ProblemChoices._DISPLAY_TEXT,
            ProblemChoices._ANSWER,
            ProblemChoices._USER_CHOICE
    };

    public static final String[][] COLUMN_DEFINITIONS = {
        {ProblemChoices._STRING_ID, "TEXT NOT NULL"},
        {ProblemChoices._PARENT_ID, "TEXT NOT NULL"},
        {ProblemChoices._SEQUENCE, "TEXT NOT NULL"},
        {ProblemChoices._DISPLAY_TEXT,"TEXT DEFAULT \"\""},
        {ProblemChoices._ANSWER, "TEXT DEFAULT \"\""},
        {ProblemChoices._USER_CHOICE, "TEXT DEFAULT \"\""}
    };

    public ProblemChoiceTable(DBHandler db){
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

}
