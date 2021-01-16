package com.example.yihan.myweather.db;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class WeatherSave  extends LitePalSupport {
    String weather_Id;
    String weather_InfoSave;

    public String getWeather_Id() {
        return weather_Id;
    }

    public void setWeather_Id(String weather_Id) {
        this.weather_Id = weather_Id;
    }

    public String getWeather_InfoSave() {
        return weather_InfoSave;
    }

    public void setWeather_InfoSave(String weather_InfoSave) {
        this.weather_InfoSave = weather_InfoSave;
    }
}
