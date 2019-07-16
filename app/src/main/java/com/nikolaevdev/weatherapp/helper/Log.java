package com.nikolaevdev.weatherapp.helper;

public class Log {



    public static void err(String tag, String string) {
        if (Constants.SHOW_LOG)
            android.util.Log.e(tag, string);

    }

    public static void deb(String tag, String string) {
        if (Constants.SHOW_LOG)
            android.util.Log.d(tag, string);

    }


}