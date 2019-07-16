package com.nikolaevdev.weatherapp.view.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.databinding.FragmentCurrentWeatherBinding;
import com.nikolaevdev.weatherapp.helper.Constants;
import com.nikolaevdev.weatherapp.helper.Utils;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.network.ApiClient;
import com.nikolaevdev.weatherapp.presenter.WeatherPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment implements WeatherContract.WeatherView {

    private FragmentCurrentWeatherBinding binding;
    private WeatherPresenter weatherPresenter;
    private final int GPS_PERMISSION_CODE = 0;
    private ProgressBar progressBar;


    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


         binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_current_weather, container, false);
        View view = binding.getRoot();
        progressBar = view.findViewById(R.id.progressBar);

        weatherPresenter = new WeatherPresenter(this,getContext());
        weatherPresenter.showCurrentWeather();

        if (Utils.permissionGranted()){
            weatherPresenter.getUserLocation();
        }else {

            showProgress();
            grantPermission();
        }



        return view;
    }


    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(SimpleWeather weather) {

        if (weather != null){
            hideProgress();
            binding.setWeather(weather);
        }

    }

    private void grantPermission(){
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                GPS_PERMISSION_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    weatherPresenter.getUserLocation();
                } else {
                    weatherPresenter.getDataForDefaultLocation();
                }
                return;
            }

        }
    }

}
