package com.bgsourcingltd.bghaat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;





public class SplashActivity extends AppCompatActivity {
    private ImageView logoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        logoImg = findViewById(R.id.iv_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (getIntent() != null && getIntent().hasExtra("key1")){
                            for (String key: getIntent().getExtras().keySet()){
                                String value = getIntent().getExtras().getString(key);
                                Log.d("notificationValue", "onComplete"+value+" Data "+getIntent().getExtras().getString(key));
                                Toast.makeText(SplashActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                            }
                        }

                        /*String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }

                        Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                    }
                });


        goToIntroActivity();


    }


    private void goToIntroActivity(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, IntroSliderActivity.class));
                finish();
            }
        }, 2000);
    }


}