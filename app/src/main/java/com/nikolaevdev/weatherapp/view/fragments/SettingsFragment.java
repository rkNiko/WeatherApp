package com.nikolaevdev.weatherapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.helper.Constants;
import com.nikolaevdev.weatherapp.helper.SharedPrefHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch temperatureSwitch = view.findViewById(R.id.temperature_metrick_switch);
        final SharedPrefHelper helper = new SharedPrefHelper(getContext());
        temperatureSwitch.setChecked(isSwitchChecked(helper.getDefaultMetric()));
        temperatureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    helper.setDefaultMetric(Constants.METRIC_FARENGHEIT);
                }else {
                    helper.setDefaultMetric(Constants.METRIC_CELSIUS);
                }
            }
        });

        return view;
    }

    private boolean isSwitchChecked(int metricType){

        if (metricType == Constants.METRIC_CELSIUS){
            return false;
        }else {
            return true;
        }

    }

}
