package com.nikolaevdev.weatherapp.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import com.nikolaevdev.weatherapp.R;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;
import com.nikolaevdev.weatherapp.model.pojo.WeatherResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {


    public static long getTimeStamp() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        return calendar.getTimeInMillis() / 1000;
    }

    public static SimpleWeather getSimpleWeather(WeatherResponse weatherResponse) {

        SimpleWeather simpleWeather = new SimpleWeather();
        simpleWeather.setId(getTimeStamp());
        simpleWeather.setDescription(weatherResponse.getWeather().get(0).getDescription());
        simpleWeather.setIcon("a" + weatherResponse.getWeather().get(0).getIcon());
        simpleWeather.setTemp(weatherResponse.getMain().getTemp());
        if (weatherResponse.getMain().getTempMax() != null)
            simpleWeather.setTempMax(weatherResponse.getMain().getTempMax());
        if (weatherResponse.getMain().getTempMin() != null)
            simpleWeather.setTempMin(weatherResponse.getMain().getTempMin());
        simpleWeather.setHumidity(weatherResponse.getMain().getHumidity());
        simpleWeather.setPressure(weatherResponse.getMain().getPressure());
        simpleWeather.setWindSppeed(weatherResponse.getWind().getSpeed());
        simpleWeather.setCountry(weatherResponse.getSys().getCountry());
        simpleWeather.setCity(weatherResponse.getName());
        simpleWeather.setSunrise(weatherResponse.getSys().getSunrise());
        simpleWeather.setSunset(weatherResponse.getSys().getSunset());

        return simpleWeather;

    }

    public static String getName(SimpleWeather simpleWeather) {
        if (simpleWeather == null) return "";
        return simpleWeather.getCity() + " (" + simpleWeather.getCountry() + " )";
    }

    public static String getHumanTime(long millis, Long prevMillis) {

        DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millis * 1000);
        String result = writeFormat.format(date).split(" ")[1];
        if (prevMillis == null){
            return result;
        }else {
            DateFormat diffFormat = new SimpleDateFormat("HH:mm:ss");
            long diff = millis - (prevMillis + 24 * 60 * 60);

            return result + "( " + (diff > 0? " +" + diff : diff) + " " + App.getAppContext().getString(R.string.second) + " )";
        }

    }

    public static String getTemp(SimpleWeather weather, Context context) {

        if (weather == null) return "";
        SharedPrefHelper helper = new SharedPrefHelper(context);
        int metric = helper.getDefaultMetric();
        double temperature = weather.getTemp();
        String result;
        double diff;

        if (metric == Constants.METRIC_CELSIUS) {
             result = temperature + context.getString(R.string.celsius);
            if (weather.getPreviousWeatherValues() == null) {
                return result;
            } else {
                diff = temperature - weather.getPreviousWeatherValues().getTemp();
                return addPreviousValues(result, diff);
            }


        } else {
            result = getFarengheit(temperature) + " " + context.getString(R.string.farengheit);
            if (weather.getPreviousWeatherValues() == null){
                return result;
            }else {
                 diff = temperature - weather.getPreviousWeatherValues().getTemp();
            }
            return addPreviousValues(result, getFarengheit(diff));
        }


    }

    private static String addPreviousValues(String text, double rawDiff) {

        if (isInt(rawDiff)){
            int diff = (int) rawDiff;
            return text + " (" + (diff > 0 ? "+" + diff : String.valueOf(diff)) + ")";
        }else {

            double diff = Math.floor(rawDiff * 100) / 100;
            return text + " (" + (diff > 0 ? "+" + diff : String.valueOf(diff)) + ")";

        }

    }

    private static boolean isInt(double d){
        return (int) d == d;
    }

    private static double getFarengheit(double celsius) {

        double result =  (9.0 / 5.0) * celsius + 32;
        return Math.floor(result * 100) / 100;
    }

    public static String getHumidity(SimpleWeather weather) {
        if (weather == null) return "";
        String result = weather.getHumidity() + " %";
        if (weather.getPreviousWeatherValues() == null){
            return result;
        }else {
            double diff = weather.getHumidity() - weather.getPreviousWeatherValues().getHumidity();
            return addPreviousValues(result, diff);
        }
    }

    public static String getWindSpeed(SimpleWeather weather) {

        if (weather == null) return "";
        String result = weather.getWindSppeed() + " " + App.getAppContext().getString(R.string.mps);
        if (weather.getPreviousWeatherValues() == null){
            return result;
        }else {
            double diff  = weather.getWindSppeed() - weather.getPreviousWeatherValues().getWindSppeed();
            return addPreviousValues(result, diff);
        }
    }

    public static String getPressure(SimpleWeather weather) {
        if (weather == null) return "";
        String result = weather.getPressure() + " " + App.getAppContext().getString(R.string.pressure_metric);
        if (weather.getPreviousWeatherValues() == null){
            return result;
        }else {
            double diff  = weather.getPressure() - weather.getPreviousWeatherValues().getPressure();
            return addPreviousValues(result, diff);
        }
    }

    public static boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static boolean permissionGranted(){

        return ContextCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ;
    }

}
