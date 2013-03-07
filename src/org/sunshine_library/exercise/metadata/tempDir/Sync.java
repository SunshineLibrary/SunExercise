package org.sunshine_library.exercise.metadata.sync;

import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-5
 * Time: 下午12:54
 * To change this template use File | Settings | File Templates.
 */
public class Sync {

    public void sync(JSONObject jsonObject){
        new JSONHandle().onReceive(jsonObject.toString());
        new SyncFile().sync();
        new CheckLessonAvaliable().check();
    }

}
