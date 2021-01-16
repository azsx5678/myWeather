package com.example.yihan.myweather.db;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport {
    private  int id;
    private String countName;
    private  String weatherId;
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouuntName() {
        return countName;
    }

    public void setCountName(String couuntName) {
        this.countName = couuntName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
