package com.nikolaevdev.weatherapp;

import android.content.Context;

import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.network.ApiClient;

import java.util.List;

public class WeatherContract {

    public interface CurrentWeatherModel{

        void sendWeatherRequest(double lat, double lon,Context context, ApiClient.onWeatherUpdateListener listener);
         SimpleWeather getSavedWeather(Context context);


    }

    public interface SavedWeatherModel{

        List<SimpleWeather> getSavedWeather();

    }


    public interface WeatherView{

        void showProgress();

        void hideProgress();

        void showWeather(SimpleWeather weather);


    }

    public interface WeatherListView{


        void setListToAdapter(List<SimpleWeather> weatherList);


    }


    public interface WeatherPresenter{

        void onDestroy();

        void showCurrentWeather( );
        void onLocationChanged(double lat, double lon );
        void getDataforDefaultLocation( );


    }
    public interface SavedWeatherPresenter{

        void onDestroy();

        void showSavedWeather();

    }
}
