package com.example.yihan.myweather;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yihan.myweather.adapter.ViewPagerAdapter;
import com.example.yihan.myweather.db.WeatherSave;
import com.example.yihan.myweather.fragment.WeatherInfoFragment;
import com.example.yihan.myweather.gson.Weather;
import com.example.yihan.myweather.util.HttpUtil;
import com.example.yihan.myweather.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TextActivity extends AppCompatActivity {
    private ViewPager pagerViewpager;
    private Button addButton;
    private Button moreButton;
    List<Fragment> fragmentList = new ArrayList<>();
    ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        pagerViewpager = (ViewPager) findViewById(R.id.pager_Viewpager);
        addButton = (Button) findViewById(R.id.add_button);
        moreButton = (Button) findViewById(R.id.more_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        initPager();
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
    }

    private void initPager() {
        Toast.makeText(this, "正在获取天气，请稍后..", Toast.LENGTH_SHORT).show();
            List<WeatherSave> weatherList = LitePal.findAll(WeatherSave.class);
            WeatherInfoFragment fragment;
            if (weatherList.size() == 0) {
                requestWeather("CN101230701",false);
            }
            for (int i = 0; i < weatherList.size(); i++) {
                requestWeather(weatherList.get(i).getWeather_Id(),true);
            }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==10101){
                fragmentList.clear();
                    List<WeatherSave> weatherList = LitePal.findAll(WeatherSave.class);
                    for(int i=0;i<weatherList.size();i++){
                        Weather weather=Utility.handleWeatherResponse(weatherList.get(i).getWeather_InfoSave());
                        Fragment    fragment = new WeatherInfoFragment(weather);
                        fragmentList.add(fragment);
                    }

                pagerViewpager.setAdapter(adapter);
                pagerViewpager.setCurrentItem(0);
            }
        }
    };
    @Override
    protected void onRestart() {
        super.onRestart();
        initPager();
    }
    private void requestWeather(final String weatherId, final boolean flag) {
        String weatherUrl="http://guolin.tech/api/weather?cityid="+weatherId+"&key=0c5a961831b24c61969b59158a752b3d";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(TextActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final  String responseText=response.body().string();
                final  Weather weather=Utility.handleWeatherResponse(responseText);
                if(weather!=null&&"ok".equals(weather.status)){
                    if(flag){
                        WeatherSave save=new WeatherSave();
                        save.setWeather_InfoSave(responseText);
                        save.updateAll("weather_Id = ?",weatherId);
                    }else {
                        WeatherSave save=new WeatherSave();
                        save.setWeather_Id(weatherId);
                        save.setWeather_InfoSave(responseText);
                        save.save();
                    }
                    handler.sendEmptyMessage(10101);
                }else {
                    Toast.makeText(TextActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
