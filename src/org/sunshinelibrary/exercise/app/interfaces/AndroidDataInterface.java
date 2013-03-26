package org.sunshinelibrary.exercise.app.interfaces;

import org.sunshinelibrary.support.utils.sync.StateMachine;

public interface AndroidDataInterface {

    // upload/send user data
    // so far will return the parameter.
    // plz refer to the json.Request class
    public String requestUserData(String userData);

    // request sever data
    // plz refer to json.Request class, the return value will fill the "param" field of data.
    public String requestData(String data);
}
