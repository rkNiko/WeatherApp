package com.nikolaevdev.weatherapp.model;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.WeatherContract;
import com.nikolaevdev.weatherapp.helper.Constants;
import com.nikolaevdev.weatherapp.helper.Log;
import com.nikolaevdev.weatherapp.helper.SharedPrefHelper;
import com.nikolaevdev.weatherapp.helper.Utils;
import com.nikolaevdev.weatherapp.model.pojo.PreviousWeatherValues;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.model.pojo.WeatherResponse;
import com.nikolaevdev.weatherapp.network.ApiClient;
import com.nikolaevdev.weatherapp.network.EndPointInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrentWeatherModel implements WeatherContract.CurrentWeatherModel {

    private final String TAG = getClass().getSimpleName();



    @Override
    public void sendWeatherRequest(final double lat, final double lon, final Context context,
                                   final ApiClient.onWeatherUpdateListener listener) {

        if (Utils.isNetworkAvailable()){



        final SharedPrefHelper helper = new SharedPrefHelper(context);
        long lastUpdated = helper.getLastWeatherTime();

        if (Utils.getTimeStamp() - lastUpdated >= Constants.UPDATE_OFFSET){

            EndPointInterface endPointInterface =
                    ApiClient.getClient().create(EndPointInterface.class);

            Call<WeatherResponse> call = endPointInterface.getcurrentWeather(lat,lon, "metric",Constants.API_KEY);
            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    WeatherResponse weather = response.body();
                    Log.deb(TAG, "Weather for city " + weather.getName());
                    SqlLiteDataHelper dataHelper = SqlLiteDataHelper.getInstance( context);
                    SimpleWeather simpleWeather = Utils.getSimpleWeather(weather);
                    dataHelper.insertWeather(simpleWeather);
                    listener.onWeatherUpdated(setPreviousValues(simpleWeather, context));
                    helper.setLastWeatherTime(Utils.getTimeStamp());

                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.err(TAG, t.toString());

                }
            });

        }else {

            listener.onWeatherUpdated(getWeatherWithPrevValues(context));
        }

        }else if (getSavedWeather(context) == null){


            showDialog( lat, lon,  context, listener);
        }else {
            Toast.makeText(context, R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
        }

    }

    private void showDialog(final double lat, final double lon, final Context context,
                            final ApiClient.onWeatherUpdateListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_text);
        builder.setCancelable(false);

        builder.setPositiveButton(
                context.getString(R.string.dialog_positive_btn),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        sendWeatherRequest(lat, lon,  context, listener);

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public SimpleWeather getSavedWeather(Context context) {
       return getWeatherWithPrevValues(context);
    }
@Nullable
private SimpleWeather setPreviousValues(SimpleWeather weather, Context context){
        if (weather == null ) return weather;
    PreviousWeatherValues prevValues = new PreviousWeatherValues();
    SimpleWeather prevWeather = SqlLiteDataHelper.getInstance(context).getPreviousWeather();
    if (prevWeather == null || prevWeather.getId() == weather.getId())return weather;
    prevValues.setHumidity(prevWeather.getHumidity());
    prevValues.setPressure(prevWeather.getPressure());
    prevValues.setSunrise(prevWeather.getSunrise());
    prevValues.setSunset(prevWeather.getSunset());
    prevValues.setTemp(prevWeather.getTemp());
    prevValues.setWindSppeed(prevWeather.getWindSppeed());

    weather.setPreviousWeatherValues(prevValues);
    return weather;
}

private SimpleWeather getWeatherWithPrevValues(Context context){
    SimpleWeather weather = SqlLiteDataHelper.getInstance(context).getCurrentWeather();
    return setPreviousValues(weather, context);
}

}
