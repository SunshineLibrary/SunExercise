package org.sunshinelibrary.exercise.metadata.json;

/**
 * @author linuo
 * @version 1.0
 * @date 3/14/13
 */
public class UserRecordResponse extends JSONObject {
    public String success = "true";
    private static UserRecordResponse response = new UserRecordResponse();
    public static final String SUCCESS = response.toJsonString();
}
