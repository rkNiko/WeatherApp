package com.nikolaevdev.weatherapp.helper;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;


public class DataBindingAdapters {


    @BindingAdapter("android:custom_src")
    public static void setImageDrawable(ImageView view, String icon) {
        if (icon == null) icon = "a01n";
        Resources resources = view.getContext().getResources();
        final int resourceId = resources.getIdentifier(icon, "drawable",
                view.getContext().getPackageName());
                 view.setImageDrawable(resources.getDrawable(resourceId));
    }

    @BindingAdapter({"android:show_temperature"})
    public static void setTemperature(TextView view, SimpleWeather weather) {
       String text = Utils.getTemp(weather, view.getContext());
        view.setText(text);
    }



}
