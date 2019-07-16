package com.nikolaevdev.weatherapp.view.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
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
import com.nikolaevdev.weatherapp.helper.Log;
import com.nikolaevdev.weatherapp.helper.Utils;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.network.ApiClient;
import com.nikolaevdev.weatherapp.presenter.WeatherPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment implements WeatherContract.WeatherView, LocationListener {

    private FragmentCurrentWeatherBinding binding;
    private WeatherContract.WeatherPresenter weatherPresenter;
    private final int GPS_PERMISSION_CODE = 0;
    private ProgressBar progressBar;
    private LocationManager locationManager;


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
            getUserLocation();
        }else {

            showProgress();
            grantPermission();
        }



        return view;
    }

    public void getUserLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 60, 1000, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 60, 1000, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
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
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
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
                   getUserLocation();
                } else {
                  weatherPresenter.getDataforDefaultLocation();
                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

        weatherPresenter.onLocationChanged(location.getLatitude(), location.getLongitude());


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

        Log.err("",s);
    }

    @Override
    public void onProviderDisabled(String s) {

        Log.err("",s);

    }

}
