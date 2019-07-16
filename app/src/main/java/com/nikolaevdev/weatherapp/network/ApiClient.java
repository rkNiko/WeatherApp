package com.nikolaevdev.weatherapp.network;

import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://api.openweathermap.org/";
    private static Retrofit retrofit ;


    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface onWeatherUpdateListener  {
        void onWeatherUpdated(SimpleWeather weather);
    }


}
