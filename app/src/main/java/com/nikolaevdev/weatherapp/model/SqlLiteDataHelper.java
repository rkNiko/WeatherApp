package com.nikolaevdev.weatherapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.nikolaevdev.weatherapp.helper.Utils;
import com.nikolaevdev.weatherapp.model.pojo.SimpleWeather;

import java.util.ArrayList;
import java.util.List;

public class SqlLiteDataHelper extends SQLiteOpenHelper {

    private static SqlLiteDataHelper sInstance;

    private static final String DATABASE_NAME = "weather.db";
    private static final String DATABASE_TABLE = "city_weather";
    private static final int DATABASE_VERSION = 1;


    //private static final String TABLE_NAME = "chunks";
    private final String WEATHER_ID = "_id";
    private final String WEATHER_DESCRIPTION = "description";
    private final String WEATHER_ICON = "icon";
    private final String WEATHER_TEMP = "temperature";
    private final String WEATHER_TEMP_MIN = "temperature_min";
    private final String WEATHER_TEMP_MAX = "temperature_max";
    private final String WEATHER_HUMIDITY = "humidity";
    private final String WEATHER_PRESSURE = "pressure";
    private final String WEATHER_WIND_SPEED = "wind_speed";
    private final String WEATHER_COUNTRY = "country";
    private final String WEATHER_CITY = "city";
    private final String WEATHER_SUNRISE = "sunrise";
    private final String WEATHER_SUNSET = "sunset";


    public static synchronized SqlLiteDataHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SqlLiteDataHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private SqlLiteDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(CREATE_WEATHER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    // Create table SQL query
    private final String CREATE_WEATHER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "("
                    + WEATHER_ID + " LONG ,"
                    + WEATHER_DESCRIPTION + " TEXT,"
                    + WEATHER_ICON + " TEXT,"
                    + WEATHER_TEMP + " REAL,"
                    + WEATHER_TEMP_MIN + " REAL,"
                    + WEATHER_TEMP_MAX + " REAL,"
                    + WEATHER_HUMIDITY + " INTEGER,"
                    + WEATHER_PRESSURE + " INTEGER,"
                    + WEATHER_WIND_SPEED + " REAL,"
                    + WEATHER_COUNTRY + " TEXT,"
                    + WEATHER_CITY + " TEXT,"
                    + WEATHER_SUNRISE + " INTEGER,"
                    + WEATHER_SUNSET + " INTEGER"
                    + ")";


    public boolean insertWeather(SimpleWeather weather) {

        SQLiteDatabase db = this.getWritableDatabase();

        if (getCurrentWeather() == null){
            db.insert(DATABASE_TABLE, null, convertWeatherIntoContentValues(weather));
        }else {
            updateWeather(weather);
        }

        return true;
    }

    private void updateWeather ( SimpleWeather weather) {
        SQLiteDatabase db = this.getWritableDatabase();

        long id = Utils.getTimeStamp();
        db.update(DATABASE_TABLE, convertWeatherIntoContentValues(weather), WEATHER_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    @Nullable
    public SimpleWeather getCurrentWeather ( ) {
        SQLiteDatabase db = this.getReadableDatabase();
        long id = Utils.getTimeStamp();
        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE + " WHERE " + WEATHER_ID + " = ? ", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        return getSimpleWeatherFromCursor(cursor);
    }

    @Nullable
    public SimpleWeather getPreviousWeather ( ) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE + " ORDER BY " + WEATHER_ID  + " DESC limit 2 " , null);
        cursor.moveToLast();

        return getSimpleWeatherFromCursor(cursor);
    }


    public List<SimpleWeather> getWeatherList() {
        List<SimpleWeather> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            array_list.add(getSimpleWeatherFromCursor(cursor));
            cursor.moveToNext();
        }
        return array_list;
    }


@Nullable
    private SimpleWeather getSimpleWeatherFromCursor (Cursor cursor){

        if (cursor.getCount() == 0) return null;

        SimpleWeather weather = new SimpleWeather();

        long id = cursor.getLong(cursor.getColumnIndex(WEATHER_ID));
        String description = cursor.getString(cursor.getColumnIndex(WEATHER_DESCRIPTION));
        String icon = cursor.getString(cursor.getColumnIndex(WEATHER_ICON));
        double temp = cursor.getDouble(cursor.getColumnIndex(WEATHER_TEMP));
        double tempMin = cursor.getDouble(cursor.getColumnIndex(WEATHER_TEMP_MIN));
        double tempMax = cursor.getDouble(cursor.getColumnIndex(WEATHER_TEMP_MAX));
        int humidity = cursor.getInt(cursor.getColumnIndex(WEATHER_HUMIDITY));
        double pressure = cursor.getDouble(cursor.getColumnIndex(WEATHER_PRESSURE));
        double windSppeed = cursor.getDouble(cursor.getColumnIndex(WEATHER_WIND_SPEED));
        String country = cursor.getString(cursor.getColumnIndex(WEATHER_COUNTRY));
        String city = cursor.getString(cursor.getColumnIndex(WEATHER_CITY));
        long sunrise = cursor.getLong(cursor.getColumnIndex(WEATHER_SUNRISE));
        long sunset = cursor.getLong(cursor.getColumnIndex(WEATHER_SUNSET));

        weather.setId(id);
        weather.setDescription(description);
        weather.setIcon(icon);
        weather.setTemp(temp);
        weather.setTempMax(tempMax);
        weather.setTempMin(tempMin);
        weather.setHumidity(humidity);
        weather.setPressure(pressure);
        weather.setWindSppeed(windSppeed);
        weather.setCountry(country);
        weather.setCity(city);
        weather.setSunrise(sunrise);
        weather.setSunset(sunset);
        return weather;

    }

    private ContentValues convertWeatherIntoContentValues(SimpleWeather weather){

        ContentValues contentValues = new ContentValues();
        contentValues.put(WEATHER_ID, Utils.getTimeStamp());
        contentValues.put(WEATHER_DESCRIPTION, weather.getDescription());
        contentValues.put(WEATHER_ICON, weather.getIcon());
        contentValues.put(WEATHER_TEMP, weather.getTemp());
        contentValues.put(WEATHER_TEMP_MIN, weather.getTempMin());
        contentValues.put(WEATHER_TEMP_MAX, weather.getTempMax());
        contentValues.put(WEATHER_HUMIDITY, weather.getHumidity());
        contentValues.put(WEATHER_PRESSURE, weather.getPressure());
        contentValues.put(WEATHER_WIND_SPEED, weather.getWindSppeed());
        contentValues.put(WEATHER_COUNTRY, weather.getCountry());
        contentValues.put(WEATHER_CITY, weather.getCity());
        contentValues.put(WEATHER_SUNRISE, weather.getSunrise());
        contentValues.put(WEATHER_SUNSET, weather.getSunset());

        return contentValues;
    }




}