package akilliyazilim.justhoy.activity;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class HoyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,"APP_ID","CLIENT_KEY");
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
       activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}
