package org.sunshinelibrary.exercise.metadata.provider;

import android.content.UriMatcher;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.AUTHORITY;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/9/13
 */
public class Matcher {
    /*
     * Defining constants for matching the content URI
     */

    public static final int SUBJECTS = 200;
    public static final int SUBJECTS_WITH_USER_DATA = 201;
    public static final int LESSONS = 300;
    public static final int LESSONS_WITH_USER_DATA = 301;
    public static final int STAGES = 400;
    public static final int STAGES_WITH_USER_DATA = 401;
    public static final int SECTIONS = 500;
    public static final int SECTIONS_WITH_USER_DATA = 501;
    public static final int ACTIVITIES = 600;
    public static final int ACTIVITIES_WITH_USER_DATA = 601;
    public static final int PROBLEMS = 700;
    public static final int PROBLEMS_WITH_USER_DATA = 701;
    public static final int PROBLEM_CHOICES = 800;
    public static final int PROBLEM_CHOICES_WITH_USER_DATA = 801;
    public static final int USER_DATA = 900;
    public static final int MEDIA = 1000;
    public static final int DELETE_UNUSED_FILE = 1100;

    public static class Factory {

        public static UriMatcher getMatcher() {
            UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
            
            matcher.addURI(AUTHORITY, "subjects", SUBJECTS);
//            matcher.addURI(AUTHORITY, "subjects_user", SUBJECTS_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "lessons", LESSONS);
//            matcher.addURI(AUTHORITY, "lessons_user", LESSONS_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "stages", STAGES);
//            matcher.addURI(AUTHORITY, "stages_user", STAGES_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "sections", SECTIONS);
//            matcher.addURI(AUTHORITY, "sections_user", SECTIONS_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "activities", ACTIVITIES);
//            matcher.addURI(AUTHORITY, "activities_user", ACTIVITIES_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "problems", PROBLEMS);
//            matcher.addURI(AUTHORITY, "problems_user", PROBLEMS_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "problem_choices", PROBLEM_CHOICES);
//            matcher.addURI(AUTHORITY, "problem_choices_user", PROBLEM_CHOICES_WITH_USER_DATA);
            matcher.addURI(AUTHORITY, "media", MEDIA);
            matcher.addURI(AUTHORITY, "delete", DELETE_UNUSED_FILE);
            matcher.addURI(AUTHORITY, "user_data", USER_DATA);
            return matcher;
        }
    }
}
