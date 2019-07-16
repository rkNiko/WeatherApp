package com.nikolaevdev.weatherapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPrefHelper(Context context) {

        preferences = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDefaultMetric(int metric){

        editor.putInt( Constants.METRIC, metric).apply();
    }

    public int getDefaultMetric(){

        return preferences.getInt( Constants.METRIC, Constants.METRIC_CELSIUS);
    }

    public void setLastWeatherTime(long timestamp){
        editor.putLong(Constants.WEATHER_UPDATED, timestamp).apply();
    }

    public long getLastWeatherTime(){
        return preferences.getLong(Constants.WEATHER_UPDATED, 0);
    }
}
