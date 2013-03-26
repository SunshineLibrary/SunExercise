package org.sunshinelibrary.exercise.app.interfaces;

import org.sunshinelibrary.support.utils.sync.StateMachine;

/**
 * @author linuo
 * @version 1.0
 * @date 3/26/13
 */
public interface AndroidInterface extends AndroidDataInterface, AndroidUIInterface, StateMachine {
    public void setUIInterface(AndroidUIInterface a);
}
