package com.nikolaevdev.weatherapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.helper.Log;
import com.nikolaevdev.weatherapp.model.SqlLiteDataHelper;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.presenter.WeatherPresenter;
import com.nikolaevdev.weatherapp.view.fragments.CurrentWeatherFragment;
import com.nikolaevdev.weatherapp.view.fragments.SavedWeatherFragment;
import com.nikolaevdev.weatherapp.view.fragments.SettingsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {




    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText(R.string.title_home);
                    showFragment(new CurrentWeatherFragment() , true);
                    return true;
                case R.id.navigation_dashboard:

                    showFragment(new SavedWeatherFragment(), false);
                    return true;
                case R.id.navigation_notifications:
                    showFragment(new SettingsFragment(), true);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            showFragment(new CurrentWeatherFragment(), false);
        }




    }




    // changes fragment in the activity
    public void showFragment(Fragment fragment, boolean addTobackstack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        if (addTobackstack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

}

