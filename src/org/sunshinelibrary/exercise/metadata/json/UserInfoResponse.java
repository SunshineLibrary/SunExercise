package org.sunshinelibrary.exercise.metadata.json;

import android.content.SharedPreferences;
import org.sunshinelibrary.support.api.UserInfo;

/**
 * @author linuo
 * @version 1.0
 * @date 3/14/13
 */
public class UserInfoResponse extends JSONObject {

    public String access_token;
    public String user_name;
    public String user_type;
    public String user_birthday;
    public String user_school;
    public String user_grade;
    public String user_class;

    public UserInfoResponse(SharedPreferences pref) {
        access_token = pref.getString(UserInfo.EXTRA_ACCESS_TOKEN, "");
        user_name = pref.getString(UserInfo.EXTRA_USER_NAME, "");
        user_type = pref.getString(UserInfo.EXTRA_USER_TYPE, "");
        user_birthday = pref.getString(UserInfo.EXTRA_USER_BIRTHDAY, "");
        user_school = pref.getString(UserInfo.EXTRA_USER_SCHOOL, "");
        user_grade = pref.getString(UserInfo.EXTRA_USER_GRADE, "");
        user_class = pref.getString(UserInfo.EXTRA_USER_CLASS, "");
    }
}
