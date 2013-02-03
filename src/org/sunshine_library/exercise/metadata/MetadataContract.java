package org.sunshine_library.exercise.metadata;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/9/13
 */
public class MetadataContract {
    public static final String AUTHORITY = "org.sunshine_library.exercise.metadata.provider";

    public static final Uri AUTHORITY_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY).build();


    public static Uri getUri(String append){
        return MetadataContract.AUTHORITY_URI.buildUpon().appendPath(append).build();
    }

    public interface Columns extends BaseColumns {
        String _IDENTIFIER = "id";
        String _NAME = "name";
        String _BODY = "body";
        String _SEQUENCE = "sequence";
        String _USER_PROGRESS = "user_progress";
        String _USER_RESULT = "user_result";
        String _USER_DURATION = "user_duration";
        String _USER_CORRECT = "user_correct";
        String _FILE_ID="file_id";

    }

    public static final class Subjects {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _NAME = Columns._NAME;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("subjects").build();
    }

    public static final class Lessons {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _PARENT_IDENTIFIER = "subject_id";
        public static final String _NAME = Columns._NAME;
        public static final String _TIME = "time";
        public static final String _USER_PROGRESS = Columns._USER_PROGRESS;
        public static final String _USER_RESULT = Columns._USER_RESULT;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("lessons").build();
    }

    public static final class Stages {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _PARENT_IDENTIFIER = "lesson_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TYPE = "type";
        public static final String _USER_PROGRESS = Columns._USER_PROGRESS;
        public static final String _USER_PERCENTAGE = "user_percentage";

        public static final int TYPE_BASIC = 0;
        public static final int TYPE_EXTEND = 1;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("stages").build();


    }

    public static final class Sections {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _PARENT_IDENTIFIER = "stage_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _NAME = Columns._NAME;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("sections").build();
    }

    public static final class Activities {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _PARENT_IDENTIFIER = "section_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TYPE = "type";
        public static final String _NAME = Columns._NAME;
        public static final String _BODY = Columns._BODY;
        public static final String _JUMP_CONDITION = "jump_condition";
        public static final String _USER_PROGRESS = Columns._USER_PROGRESS;
        public static final String _USER_DURATION = Columns._USER_DURATION;
        public static final String _USER_CORRECT = Columns._USER_CORRECT;
        public static final String _USER_SCORE = "user_score";
        public static final String _FILE_ID = Columns._FILE_ID;


        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("activities").build();

        public static final int TYPE_NONE = -1;
        public static final int TYPE_TEXT = 0;
        public static final int TYPE_AUDIO = 1;
        public static final int TYPE_VIDEO = 2;
        public static final int TYPE_GALLERY = 3;
        //        public static final int TYPE_QUIZ = 4;
        public static final int TYPE_TEST = 4;
        public static final int TYPE_HTML = 5;
        public static final int TYPE_PDF = 6;
        // new
        public static final int TYPE_DIGNOSE = 7;
//        public static final int TYPE_FINISH = 99;

    }

    public static final class Problems {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _PARENT_IDENTIFIER = "activity_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        public static final String _TYPE = "type";
        public static final String _BODY = Columns._BODY;
        public static final String _ANALYSIS = "analysis";
        public static final String _USER_DURATION = Columns._USER_DURATION;
        public static final String _USER_CORRECT = Columns._USER_CORRECT;
        public static final String _FILE_ID = Columns._FILE_ID;


        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("problems").build();

        public static final int TYPE_FB = 0;
        public static final int TYPE_SA = 1;
        public static final int TYPE_MA = 2;
        public static final int TYPE_MFB = 3; // Multiple filling blanks


    }

    public static final class ProblemChoices {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _PARENT_IDENTIFIER = "problem_id";
        public static final String _SEQUENCE = Columns._SEQUENCE;
        //public static final String _CHOICE = "choice";
        //public static final String _BODY = Columns._BODY;
        public static final String _DISPLAY_TEXT = "display_text";
        public static final String _ANSWER = "answer";
        public static final String _USER_CHOICE = "user_choice";

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("problem_choices").build();
    }

    public static final class Files {
        public static final String _IDENTIFIER = Columns._IDENTIFIER;
        public static final String _OWNER_IDENTIFIER = "owner_id";
        public static final String _OWNER_TYPE = "owner_type";
        public static final String _MEDIA_TYPE = "media_type";
        public static final String _KEY = "key";

        public static final int TYPE_PROBLEM = 0;
        public static final int TYPE_ACTIVITY = 1;

        public static final Uri CONTENT_URI = AUTHORITY_URI.buildUpon().appendPath("files").build();
    }


}
