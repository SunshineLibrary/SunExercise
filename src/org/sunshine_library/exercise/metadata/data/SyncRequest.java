package org.sunshine_library.exercise.metadata.data;

import org.sunshine_library.exercise.metadata.sync.SyncSubscription;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-5
 * Time: 下午1:13
 * To change this template use File | Settings | File Templates.
 */
public class SyncRequest {

    public static void sync(){
        new SyncSubscription().sync();
    }
}
