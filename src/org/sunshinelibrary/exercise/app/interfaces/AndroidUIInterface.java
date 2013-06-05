package org.sunshinelibrary.exercise.app.interfaces;

/**
 * @author linuo
 * @version 1.0
 * @date 3/26/13
 */
public interface AndroidUIInterface {
    public void showExitDialog();

    public void log(int priority, String tag, String msg);

    public void onReady();

    public void openThirdPartyApp(String path, String type);

    public void deletePlayLog();
}
