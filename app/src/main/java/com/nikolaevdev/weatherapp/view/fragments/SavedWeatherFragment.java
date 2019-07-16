package com.nikolaevdev.weatherapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.presenter.ListWeatherPresenter;
import com.nikolaevdev.weatherapp.view.WeatherAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedWeatherFragment extends Fragment implements WeatherContract.WeatherListView {

    private WeatherAdapter adapter;
    private RecyclerView recyclerView;
    private ListWeatherPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;


    public SavedWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_weather, container, false);

        recyclerView = view.findViewById(R.id.saved_weather_recycler_vie);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        presenter = new ListWeatherPresenter(this);
        presenter.showSavedWeather();
        return view;
    }

    @Override
    public void setListToAdapter(List<SimpleWeather> weatherList) {
        adapter = new WeatherAdapter();
        adapter.setData(weatherList);
        recyclerView.setAdapter(adapter);
    }
}
