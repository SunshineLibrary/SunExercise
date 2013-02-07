package org.sunshine_library.exercise.metadata.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-4
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public class JsonInterface {

    public static final String REQ_ID = "req_id";
    public static final String SUCESS =  "{ \"status\" : \"success\" }";
    public static final String FAILD =    "{ \"status\" : \"failed\" }";

    //TODO 处理多个json请求、查询

    public static  String ask(String jsonString) throws JSONException{
        JSONObject jsonObject = new JSONObject(jsonString);
        int req = jsonObject.getInt(REQ_ID);
        if (req < 1000)
            return new GetData().get(jsonObject);
        else if (req < 10000) {
             new DataBack().onRecieve(jsonObject);
             return SUCESS;
        }else{
             SyncRequest.sync();
             return SUCESS;
        }
    }

}
