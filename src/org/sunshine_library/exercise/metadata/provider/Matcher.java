package org.sunshine_library.exercise.metadata.provider;

import android.content.UriMatcher;

import static org.sunshine_library.exercise.metadata.MetadataContract.AUTHORITY;

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
    //    public static final int COURSES = 201;
//    public static final int CHAPTERS = 202;
    public static final int LESSONS = 500;
    public static final int SECTIONS = 600;
    public static final int ACTIVITIES = 700;
    public static final int ACTIVITIES_ID = 801;
    public static final int PROBLEMS = 800;
    public static final int PROBLEMS_ID = 801;
    public static final int PROBLEM_CHOICES = 900;
    public static final int FILES = 1000;
    public static final int DIALOGS = 1100;


    public static class Factory {

        public static UriMatcher getMatcher() {
            UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

            matcher.addURI(AUTHORITY, "subjects", SUBJECTS);
//            matcher.addURI(AUTHORITY, "courses", COURSES);
//            matcher.addURI(AUTHORITY, "chapters", CHAPTERS);
            matcher.addURI(AUTHORITY, "lessons", LESSONS);
            matcher.addURI(AUTHORITY, "sections", SECTIONS);
            matcher.addURI(AUTHORITY, "activities", ACTIVITIES);
            matcher.addURI(AUTHORITY, "activities/#", ACTIVITIES_ID);
            matcher.addURI(AUTHORITY, "problems", PROBLEMS);
            matcher.addURI(AUTHORITY, "problems/#", PROBLEMS_ID);
            matcher.addURI(AUTHORITY, "problem_choices", PROBLEM_CHOICES);
            matcher.addURI(AUTHORITY, "files", FILES);
            matcher.addURI(AUTHORITY, "dialogs", DIALOGS);

            return matcher;
        }
    }
}
