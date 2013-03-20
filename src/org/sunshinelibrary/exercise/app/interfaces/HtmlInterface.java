package org.sunshinelibrary.exercise.app.interfaces;

import org.sunshinelibrary.support.utils.sync.StateMachine;

public interface HtmlInterface extends StateMachine {
    // deprecated
    public String requestJson(String reqJson);

    // deprecated
    public void loadHtml(String page, String reqJson);

    // upload/send user data
    // so far will return the parameter.
    // plz refer to the json.Request class
    public String requestUserData(String userData);

    // request sever data
    // plz refer to json.Request class, the return value will fill the "param" field of data.
    public String requestData(String data);
}
