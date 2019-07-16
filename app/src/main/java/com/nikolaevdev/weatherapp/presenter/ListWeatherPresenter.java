package com.nikolaevdev.weatherapp.presenter;

import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.model.SavedWeatherModel;

import java.util.List;

public class ListWeatherPresenter implements WeatherContract.SavedWeatherPresenter {


    private final WeatherContract.WeatherListView view;
    private final SavedWeatherModel weatherModel;

    public ListWeatherPresenter(final WeatherContract.WeatherListView  view) {
        this.view = view;
        weatherModel = new SavedWeatherModel();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void showSavedWeather() {

        view.setListToAdapter(weatherModel.getSavedWeather());
    }
}
