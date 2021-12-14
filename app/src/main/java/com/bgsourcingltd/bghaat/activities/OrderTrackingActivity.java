package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderTrackingActivity extends AppCompatActivity {
    private TextView estimatedDate;
    private Button continueShopingBtn;
    private View orderConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        estimatedDate = findViewById(R.id.tv_dates);
        continueShopingBtn = findViewById(R.id.btn_contunue_shoping);
        orderConfirm = findViewById(R.id.viewOrderConfirm);


        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE dd.MM.yyyy");
        Calendar currentCal = Calendar.getInstance();
        String currentdate=dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 5);
        String toDate = dateFormat.format(currentCal.getTime());

        estimatedDate.setText(toDate);

        orderConfirm.setBackground(ContextCompat.getDrawable(this,R.drawable.shape_status_completed));


        continueShopingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderTrackingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}