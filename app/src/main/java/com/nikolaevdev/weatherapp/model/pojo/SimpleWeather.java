package com.nikolaevdev.weatherapp.model.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class SimpleWeather extends BaseObservable {

    private long id;
    private String description;
    private String icon;
    private double temp;
    private double tempMin;
    private double tempMax;
    private int humidity;
    private double pressure;
    private double windSppeed;
    private String country;
    private String city;
    private long sunrise;
    private long sunset;
    private PreviousWeatherValues previousWeatherValues;

    public SimpleWeather() {
    }

    public PreviousWeatherValues getPreviousWeatherValues() {
        return previousWeatherValues;
    }

    public void setPreviousWeatherValues(PreviousWeatherValues previousWeatherValues) {
        this.previousWeatherValues = previousWeatherValues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Bindable
    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    @Bindable
    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Bindable
    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @Bindable
    public double getWindSppeed() {
        return windSppeed;
    }

    public void setWindSppeed(double windSppeed) {
        this.windSppeed = windSppeed;
    }

    @Bindable
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Bindable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Bindable
    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    @Bindable
    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
