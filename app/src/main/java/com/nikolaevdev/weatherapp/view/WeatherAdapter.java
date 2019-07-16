package com.nikolaevdev.weatherapp.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.databinding.WeatherItemBinding;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;

import java.util.LinkedList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    private List<SimpleWeather> items = new LinkedList<>();

    public void setData(List<SimpleWeather> data) {
        items.clear();
        items.addAll(data);
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WeatherItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.weather_item, parent, false);
        return new WeatherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}