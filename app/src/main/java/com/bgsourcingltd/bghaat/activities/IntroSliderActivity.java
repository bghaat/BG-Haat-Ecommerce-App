package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.MyAdapter;

public class IntroSliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] dotsTv;
    private int[] layouts;
    private Button nextBtn,skipBtn;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isFirstTimeAppStart()){
            setAppStartStatus(false);
            startActivity(new Intent(IntroSliderActivity.this, SendOTPActivity.class));
            finish();
        }
        setContentView(R.layout.activity_intro_slider);
        viewPager = findViewById(R.id.intro_viewpager);
        linearLayout = findViewById(R.id.dots_layout);
        nextBtn = findViewById(R.id.btn_next);
        skipBtn = findViewById(R.id.btn_skip);

        statusTransparent();

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppStartStatus(false);
                startActivity(new Intent(IntroSliderActivity.this, SendOTPActivity.class));
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem()+1;
                if (currentPage < layouts.length){
                    viewPager.setCurrentItem(currentPage);
                }
                else {
                    setAppStartStatus(false);
                    startActivity(new Intent(IntroSliderActivity.this, SendOTPActivity.class));
                    finish();
                }
            }
        });

        layouts = new int[]{R.layout.slide_one,R.layout.slide_two,R.layout.slide_three};
        myAdapter = new MyAdapter(layouts,this);
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == layouts.length-1){
                    nextBtn.setText("Start");
                    skipBtn.setVisibility(View.GONE);
                }
                else {
                    nextBtn.setText("Next");
                    skipBtn.setVisibility(View.VISIBLE);
                }
                setDots(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDots(0);


    }

    private void setDots(int page){
        linearLayout.removeAllViews();
        dotsTv = new TextView[layouts.length];
        for (int i = 0; i< dotsTv.length; i++){
            dotsTv[i] = new TextView(this);
            dotsTv[i].setText(Html.fromHtml("&#8226"));
            dotsTv[i].setTextSize(30);
            dotsTv[i].setTextColor(Color.parseColor("#a9b4bb"));
            linearLayout.addView(dotsTv[i]);
        }
        if (dotsTv.length > 0){
            dotsTv[page].setTextColor(Color.parseColor("#FF03DAC5"));
        }
    }

    private boolean isFirstTimeAppStart(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SLIDER", Context.MODE_PRIVATE);
        return pref.getBoolean("APP_START",true);

    }
    private void setAppStartStatus(boolean status){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SLIDER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("APP_START",status);
        editor.apply();

    }

    private void statusTransparent(){
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


}