package com.nikolaevdev.weatherapp.model;

import android.content.Context;

import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.helper.App;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;

import java.util.List;

public class SavedWeatherModel implements WeatherContract.SavedWeatherModel {


    public SavedWeatherModel() {
    }

    @Override
    public List<SimpleWeather> getSavedWeather() {
        return SqlLiteDataHelper.getInstance(App.getAppContext()).getWeatherList();
    }
}
