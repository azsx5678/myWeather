package com.example.yihan.myweather.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yihan.myweather.R;
import com.example.yihan.myweather.gson.Forecast;
import com.example.yihan.myweather.gson.Weather;

public class WeatherInfoFragment extends Fragment {
    public static final String TAG="遗憾";
    private ScrollView weatherLayout;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashTest;
    private TextView sportText;
    private TextView localeText;
    private Weather weatherItem;

    public Weather getWeatherItem() {
        return weatherItem;
    }

    public WeatherInfoFragment() {
    }

    @SuppressLint("ValidFragment")
    public WeatherInfoFragment(Weather weatherItem) {
        this.weatherItem=weatherItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_weather,container,false);
        weatherLayout = (ScrollView)view. findViewById(R.id.weather_layout);
        degreeText = (TextView) view.findViewById(R.id.degree_text);
        localeText = (TextView)view. findViewById(R.id.locale_text);
        weatherInfoText = (TextView) view.findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout)view. findViewById(R.id.forecast_layout);
        aqiText = (TextView)view. findViewById(R.id.aqi_text);
        pm25Text = (TextView)view. findViewById(R.id.pm25_text);
        comfortText = (TextView)view. findViewById(R.id.comfort_text);
        carWashTest = (TextView)view. findViewById(R.id.car_wash_test);
        sportText = (TextView)view. findViewById(R.id.sport_text);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showWeatherInfo();
            }
        });
        return view;
    }
    

    private void showWeatherInfo() {
        if (weatherItem!=null){
           String cityName=weatherItem.basic.cityName;
            String degree=weatherItem.now.temperature+"°C";
            String weatherIofo=weatherItem.now.more.info;
            localeText.setText(cityName);
            degreeText.setText(degree);
            weatherInfoText.setText(weatherIofo);
            forecastLayout.removeAllViews();
            for(int i=0;i<2;i++){
                Forecast forecast =weatherItem.forecastList.get(i);
                String day="";
                switch (i){
                    case 0:day="明天";break;
                    case 1:day="后天";break;
                    default:break;
                }
                View view=LayoutInflater.from(getContext()).inflate(R.layout.forecast_item,forecastLayout,false);
                TextView dateText = (TextView)view. findViewById(R.id.date_text);
                TextView  maxText = (TextView) view.findViewById(R.id.max_text);
                dateText.setText(day+"·"+forecast.more.info);
                String strInfo=forecast.temperature.max+"/"+forecast.temperature.min+"°C";
                maxText.setText(strInfo);
                forecastLayout.addView(view);
            }
            if(weatherItem.aqi!=null){
                aqiText.setText(weatherItem.aqi.city.aqi);
                pm25Text.setText(weatherItem.aqi.city.pm25);
            }
            String comfort="舒适度:"+weatherItem.suggestion.comfort.info;
            String carWash="洗衣指数:"+weatherItem.suggestion.carWash.info;
            String sport="运动建议:"+weatherItem.suggestion.sport.info;
            comfortText.setText(comfort);
            carWashTest.setText(carWash);
            sportText.setText(sport);
            weatherLayout.setVisibility(View.VISIBLE);
        }

    }

}
