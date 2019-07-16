package com.nikolaevdev.weatherapp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.helper.Constants;
import com.nikolaevdev.weatherapp.helper.Log;
import com.nikolaevdev.weatherapp.model.CurrentWeatherModel;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.network.ApiClient;


public class WeatherPresenter implements WeatherContract.WeatherPresenter {

    private final String TAG = getClass().getSimpleName();

    private WeatherContract.WeatherView view;

    private final CurrentWeatherModel weatherModel;
    private LocationManager locationManager;

    private final Context context;

    public WeatherPresenter(final WeatherContract.WeatherView view, final Context context) {
        this.view = view;
        weatherModel = new CurrentWeatherModel();
        this.context = context;


    }




    @Override
    public void onDestroy() {

    }

    @Override
    public void showCurrentWeather() {

       view.showWeather(weatherModel.getSavedWeather(context));
    }

    @Override
    public void onLocationChanged(double lat, double lon) {
        weatherModel.sendWeatherRequest(lat, lon,context, new ApiClient.onWeatherUpdateListener() {
            @Override
            public void onWeatherUpdated(SimpleWeather weather) {
                view.showWeather(weather);
                Log.deb(TAG, "new weather received " + weather.getDescription() );
            }
        });
    }

    @Override
    public void getDataforDefaultLocation() {
        weatherModel.sendWeatherRequest(Constants.DEFAULT_LATTITUDE,
                Constants.DEFAULT_LONGITUDE,context, new ApiClient.onWeatherUpdateListener() {
                    @Override
                    public void onWeatherUpdated(SimpleWeather weather) {
                        view.showWeather(weather);
                        Toast.makeText(context, context.getString(R.string.no_gps_permission_granted), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
