package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.bgsourcingltd.bghaat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderTrackingActivity extends AppCompatActivity {
    private TextView estimatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        estimatedDate = findViewById(R.id.tv_dates);


        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE dd.MM.yyyy");
        Calendar currentCal = Calendar.getInstance();
        String currentdate=dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 3);
        String toDate=dateFormat.format(currentCal.getTime());

        estimatedDate.setText(toDate);
    }
}