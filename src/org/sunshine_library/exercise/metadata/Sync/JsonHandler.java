package org.sunshine_library.exercise.metadata.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-4
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 */



public class JsonHandler {



    public static final String CREATED = "created";
    public static final String DELETED = "deleted";
    public static final String UPDATED = "updated";



    public static final String ID = MetadataContract.Columns._IDENTIFIER;
    public static final String ID_EQ = ID + " = ";



    ContentResolver mResovler = ExerciseApplication.getApplication().getContentResolver();
    String mOperation;
    JsonFactory mJsonFactory;
    JsonParser mJsonParser;


    public void onReceive(String json) {
        try {
            mJsonFactory = new JsonFactory();
            mJsonParser  = mJsonFactory.createParser(json);
            mJsonParser.nextToken();
            while (mJsonParser.nextToken() != JsonToken.END_OBJECT){
                mOperation = mJsonParser.getCurrentName();
                mJsonParser.nextToken();
                SyncTables();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  void SyncTables() throws Exception{
        ContentValues values = new ContentValues();
        Uri uri;
        while (mJsonParser.nextToken() != JsonToken.END_OBJECT){
            uri = getUri(mJsonParser.getText());
           // Log.d("TAG-URI", uri.toString());
            mJsonParser.nextToken();
            while(mJsonParser.nextToken() != JsonToken.END_ARRAY){
                JsonToContentValues(values);
                if (mOperation.equals(CREATED)){
                    mResovler.insert(uri, values);
                }else if(mOperation.equals(DELETED)){
                    mResovler.delete(uri, ID_EQ + values.getAsInteger(ID), null);
                }else if(mOperation.equals(UPDATED)){
                    mResovler.update(uri, values, ID_EQ + values.getAsInteger(ID), null);
                }
            }
        }
    }




    public ContentValues JsonToContentValues(ContentValues values) throws Exception{
        values.clear();
        JsonToken jsonToken;
        while (mJsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = mJsonParser.getText();
            jsonToken = mJsonParser.nextToken();
            if(jsonToken == JsonToken.VALUE_STRING){
                values.put(field, mJsonParser.getText());
            } else if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT){
                values.put(field, mJsonParser.getFloatValue());
            } else if (jsonToken == JsonToken.VALUE_NUMBER_INT){
                values.put(field, mJsonParser.getIntValue());

            }
        }
        return values;
    }


    public static Uri getUri(String tableName){
        return MetadataContract.AUTHORITY_URI.buildUpon().appendPath(tableName).build();
    }
}
