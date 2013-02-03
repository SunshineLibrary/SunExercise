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
 * To change this template use Media | Settings | Media Templates.
 */
public class ScanFileForDownload {

    ContentResolver mResolver;

    public ScanFileForDownload() {
        this.mResolver = ExerciseApplication.getApplication().getContentResolver();
    }


    public List<Integer> getAllMediaIDNeedToDownload() {

        List<Integer> allExistMediaID = getAllExistMediaID();
        List<Integer> allActivityMediaID = getAllActivityMedia();
        List<Integer> allProblemMediaID = getAllProblemMedia();
        List<Integer> MediaNeedToDownload = new ArrayList<Integer>();

        for (Integer MediaID : allActivityMediaID) {
            if (!allExistMediaID.contains(MediaID)) {
                MediaNeedToDownload.add(MediaID);
            }
        }

        for (Integer MediaID : allProblemMediaID) {
            if (!allExistMediaID.contains(MediaID)) {
                MediaNeedToDownload.add(MediaID);
            }
        }

        return MediaNeedToDownload;

    }


    public List<Integer> getAllExistMediaID() {
        List<Integer> allMedia = new ArrayList<Integer>();
        Cursor cursor = mResolver.query(MetadataContract.Medias.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MetadataContract.Medias._IDENTIFIER));
            allMedia.add(id);
        }
        return allMedia;
    }


    public List<Integer> getAllActivityMedia() {
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mResolver.query(MetadataContract.Activities.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int MediaID = cursor.getInt(cursor.getColumnIndex(MetadataContract.Activities._MEDIA_ID));
            list.add(MediaID);
        }
        cursor.close();
        return list;
    }


    public List<Integer> getAllProblemMedia() {
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mResolver.query(MetadataContract.Problems.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            int MediaID = cursor.getInt(cursor.getColumnIndex(MetadataContract.Problems._MEDIA_ID));
            list.add(MediaID);
        }
        cursor.close();
        return list;
    }
}
