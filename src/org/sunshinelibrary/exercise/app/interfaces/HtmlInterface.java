package org.sunshinelibrary.exercise.app.interfaces;

public interface HtmlInterface {
    public String requestJson(String reqJson);

    public void loadHtml(String page, String reqJson);

    // upload user data
    // so far will return the parameter.
    // plz refer to the json.Request class
    public String requestUserData(String userData);

    // request sever and user data
    // plz refer to json.Request class, the return value will fill the "param" field of data.
    public String requestData(String data);
}
