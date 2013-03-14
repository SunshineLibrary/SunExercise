package org.sunshinelibrary.exercise.metadata;

import android.net.Uri;
import android.provider.BaseColumns;
import org.sunshinelibrary.support.utils.database.Contract;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/9/13
 */
public class MetadataContract extends Contract {
    public static final String AUTHORITY = "org.sunshinelibrary.exercise.metadata.provider";

    public static final Uri AUTHORITY_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY).build();


    public static Uri getUri(String append){
        return MetadataContract.AUTHORITY_URI.buildUpon().appendPath(append).build();
    }

    static {
        uri = AUTHORITY_URI;
    }

    public static final class Subjects extends TableDefinition {
        public static final String _NAME = Columns._NAME;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("subjects").build();
    }

    public static final class Lessons extends TableDefinition {
        public static final String _PARENT_ID = "subject_id";
        public static final String _NAME = Columns._NAME;
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TIME = "time";
        public static final String _USER_PROGRESS = Columns._USER_PROGRESS;
        public static final String _USER_RESULT = Columns._USER_RESULT;
        public static final String _DOWNLOAD_FINISH = Columns._DOWNLOAD_FINISH;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("lessons").build();
    }

    public static final class Stages extends TableDefinition {
        public static final String _PARENT_ID = "lesson_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TYPE = Columns._TYPE;
        public static final String _USER_PROGRESS = Columns._USER_PROGRESS;
        public static final String _USER_PERCENTAGE = "user_percentage";

        public static final int TYPE_BASIC = 0;
        public static final int TYPE_EXTEND = 1;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("stages").build();
    }

    public static final class Sections extends TableDefinition {
        public static final String _PARENT_ID = "stage_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _NAME = Columns._NAME;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("sections").build();
    }

    public static final class Activities extends TableDefinition {
        public static final String _PARENT_ID = "section_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TYPE = Columns._TYPE;
        public static final String _NAME = Columns._NAME;
        public static final String _BODY = Columns._BODY;
        public static final String _JUMP_CONDITION = "jump_condition";
        public static final String _USER_PROGRESS = Columns._USER_PROGRESS;
        public static final String _USER_DURATION = Columns._USER_DURATION;
        public static final String _USER_CORRECT = Columns._USER_CORRECT;
        public static final String _USER_SCORE = "user_score";
        public static final String _MEDIA_ID = Columns._MEDIA_ID;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("activities").build();

        public static final int TYPE_NONE = -1;
        public static final int TYPE_TEXT = 0;
        public static final int TYPE_AUDIO = 1;
        public static final int TYPE_VIDEO = 2;
        public static final int TYPE_GALLERY = 3;
        public static final int TYPE_PRACTICE = 4;
        public static final int TYPE_HTML = 5;
        public static final int TYPE_PDF = 6;
        public static final int TYPE_DIAGNOSE = 7;
//        public static final int TYPE_FINISH = 99;
    }

    public static final class Problems extends TableDefinition {
        public static final String _PARENT_ID = "activity_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TYPE = Columns._TYPE;
        public static final String _BODY = Columns._BODY;
        public static final String _ANALYSIS = "analysis";
        public static final String _USER_DURATION = Columns._USER_DURATION;
        public static final String _USER_CORRECT = Columns._USER_CORRECT;
        public static final String _MEDIA_ID = Columns._MEDIA_ID;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("problems").build();

        public static final int TYPE_CS = 0;
        public static final int TYPE_CM = 1;
        public static final int TYPE_FBS = 2;
        public static final int TYPE_FBM = 3; // Multiple filling blanks
    }

    public static final class ProblemChoices extends TableDefinition {
        public static final String _PARENT_ID = "problem_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _DISPLAY_TEXT = "display_text";
        public static final String _ANSWER = "answer";
        public static final String _USER_CHOICE = "user_choice";

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("problem_choices").build();
    }

    public static final class Media extends MediaTableDefinition {
        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("media").build();

        public static Uri getMediaDeleteUnusedFileUri() {
            return AUTHORITY_URI.buildUpon().appendPath("delete").build();
        }
    }

    public static final class UserData extends UserDataTableDefinition {
        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("user_data").build();
    }
}
