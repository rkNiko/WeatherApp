package com.nikolaevdev.weatherapp.model.pojo;

public class PreviousWeatherValues {


    private Double temp;
    private Integer humidity;
    private Double pressure;
    private Double windSppeed;
    private Long sunrise;
    private Long sunset;

    public PreviousWeatherValues() {

    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSppeed() {
        return windSppeed;
    }

    public void setWindSppeed(Double windSppeed) {
        this.windSppeed = windSppeed;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
}
