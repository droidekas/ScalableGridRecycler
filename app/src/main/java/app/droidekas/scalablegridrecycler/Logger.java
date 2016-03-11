package app.droidekas.scalablegridrecycler;

import android.util.Log;

/**
 * Created by Satyarth on 11/03/16.
 */
public class Logger {
    public static final String LOG_TAG = "scalable-grid";


    public static void logger(String s) {
        Log.d(LOG_TAG, s);
    }

    public static void logger(int s) {
        logger(s + "");
    }

}
