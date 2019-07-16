package com.nikolaevdev.weatherapp.network;

import com.nikolaevdev.weatherapp.model.pojo.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndPointInterface {

    @GET("data/2.5/weather")
    Call<WeatherResponse> getcurrentWeather(@Query("lat") double lat, @Query("lon") double lon
            ,@Query("units") String units, @Query("APPID") String apiKey);

}
