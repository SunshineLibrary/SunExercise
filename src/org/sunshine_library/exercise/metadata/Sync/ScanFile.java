package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import android.database.Cursor;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-3
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
public class ScanFile {

    ContentResolver mResolver;

    public ScanFile() {
        this.mResolver = ExerciseApplication.getApplication().getContentResolver();
    }


    public List<Integer> getAllFileIDNeedToDownload() {

        List<Integer> allExistFileID = getAllExistFileID();
        List<Integer> allActivityFileID = getAllActivityFile();
        List<Integer> allProblemFileID = getAllProblemFile();
        List<Integer> fileNeedToDownload = new ArrayList<Integer>();

        for (Integer fileID : allActivityFileID) {
            if (!allExistFileID.contains(fileID)) {
                fileNeedToDownload.add(fileID);
            }
        }

        for (Integer fileID : allProblemFileID) {
            if (!allExistFileID.contains(fileID)) {
                fileNeedToDownload.add(fileID);
            }
        }

        return fileNeedToDownload;

    }


    public List<Integer> getAllExistFileID() {
        List<Integer> allFile = new ArrayList<Integer>();
        Cursor cursor = mResolver.query(MetadataContract.Medias.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Medias._IDENTIFIER));
            allFile.add(id);
        }
        return allFile;
    }


    public List<Integer> getAllActivityFile() {
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mResolver.query(MetadataContract.Activities.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int fileID = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID));
            list.add(fileID);
        }
        cursor.close();
        return list;
    }


    public List<Integer> getAllProblemFile() {
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mResolver.query(MetadataContract.Problems.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int fileID = cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._MEDIA_ID));
            list.add(fileID);
        }
        cursor.close();
        return list;
    }
}
