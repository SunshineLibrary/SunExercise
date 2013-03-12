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
    public static final int LESSONS = 300;
    public static final int STAGES = 400;
    public static final int SECTIONS = 500;
    public static final int ACTIVITIES = 600;
    public static final int PROBLEMS = 700;
    public static final int PROBLEM_CHOICES = 800;
    public static final int USER_DATA = 900;
    public static final int MEDIA = 1000;
    public static final int DELETE_UNUSED_FILE = 1100;

    public static class Factory {

        public static UriMatcher getMatcher() {
            UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
            
            matcher.addURI(AUTHORITY, "subjects", SUBJECTS);
            matcher.addURI(AUTHORITY, "lessons", LESSONS);
            matcher.addURI(AUTHORITY, "stages", STAGES);
            matcher.addURI(AUTHORITY, "sections", SECTIONS);
            matcher.addURI(AUTHORITY, "activities", ACTIVITIES);
            matcher.addURI(AUTHORITY, "problems", PROBLEMS);
            matcher.addURI(AUTHORITY, "problem_choices", PROBLEM_CHOICES);
            matcher.addURI(AUTHORITY, "media", MEDIA);
            matcher.addURI(AUTHORITY, "delete", DELETE_UNUSED_FILE);
            matcher.addURI(AUTHORITY, "user_data", USER_DATA);
            return matcher;
        }
    }
}
