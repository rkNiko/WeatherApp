package com.nikolaevdev.weatherapp.view;

import android.support.v7.widget.RecyclerView;

import com.nikolaevdev.weatherapp.databinding.WeatherItemBinding;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    WeatherItemBinding binding;

    public WeatherViewHolder(WeatherItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(SimpleWeather weather) {
        binding.setWeather(weather);
        binding.executePendingBindings();
    }

}