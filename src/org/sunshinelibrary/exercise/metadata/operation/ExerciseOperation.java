package org.sunshinelibrary.exercise.metadata.operation;

import android.database.Cursor;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.operation.Operation;

import java.util.ArrayList;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.*;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/25/13
 * Trello:
 */
public abstract class ExerciseOperation extends Operation {
    private static final String TAG = "ExerciseOperation";

    void updateDownloadFinish(String folderID, boolean downloadFinish) {
        updateDownloadFinish(Lessons.CONTENT_URI, Lessons._STRING_ID, folderID, downloadFinish);
    }

    public ArrayList<String> AllSubjectIDs() {
        return getCollection(Subjects.CONTENT_URI, Subjects._STRING_ID, null, null);
    }

    public ArrayList<String> CollectLessonIDs(ArrayList<String> subjectIDs) {
        return getChildCollection(subjectIDs, Lessons.CONTENT_URI, Lessons._STRING_ID, Lessons._PARENT_ID,
                Lessons._SEQUENCE, null);
    }

    public ArrayList<String> CollectMediaIDs(String lessonId) {
        ArrayList<String> lessonIDs = new ArrayList<String>(1);
        lessonIDs.add(lessonId);
        ArrayList<String> stageIDs = getChildCollection(lessonIDs, Stages.CONTENT_URI, Stages._STRING_ID,
                Stages._PARENT_ID, Stages._SEQUENCE, null);
        ArrayList<String> sectionIDs = getChildCollection(stageIDs, Sections.CONTENT_URI, Sections._STRING_ID,
                Sections._PARENT_ID, Sections._SEQUENCE, null);
        ArrayList<String> mediaIDs = getChildCollection(sectionIDs, Activities.CONTENT_URI, Activities._MEDIA_ID,
                Activities._PARENT_ID, Activities._SEQUENCE, null);
        ArrayList<String> activityIDs = getChildCollection(sectionIDs, Activities.CONTENT_URI, Activities._STRING_ID,
                Activities._PARENT_ID, Activities._SEQUENCE, new Condition() {
            @Override
            public boolean selected(Cursor cursor) {
                int type = CursorUtils.getInt(cursor, extraColumn());
                return  ( type == Activities.TYPE_PRACTICE || type == Activities.TYPE_DIAGNOSE );
            }

            @Override
            public String extraColumn() {
                return Activities._TYPE;
            }
        });
        ArrayList<String> mediaIDsWithinProblems = getChildCollection(activityIDs, Problems.CONTENT_URI,
                Problems._MEDIA_ID, Problems._PARENT_ID, Problems._SEQUENCE, null);
        return combine(mediaIDs, mediaIDsWithinProblems);
    }
}
