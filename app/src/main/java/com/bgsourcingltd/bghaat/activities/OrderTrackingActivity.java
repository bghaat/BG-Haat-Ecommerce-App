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
import android.widget.Toast;

import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.OrderNumberModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTrackingActivity extends AppCompatActivity {
    private TextView estimatedDate,orderId;
    private Button continueShopingBtn;
    private View orderConfirm,orderProcess,orderCompleted;
    private ApiService apiService;
    private String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        estimatedDate = findViewById(R.id.tv_dates);
        continueShopingBtn = findViewById(R.id.btn_contunue_shoping);
        orderConfirm = findViewById(R.id.viewOrderConfirm);
        orderProcess = findViewById(R.id.viewOrderProcessed);
        orderId = findViewById(R.id.tv_order_id);
        orderCompleted = findViewById(R.id.viewOrderReady);

        apiService = ApiClient.getRetrofit().create(ApiService.class);



        orderStatus = getIntent().getStringExtra("flag");
        if (orderStatus.equals("Customer")){
            getOrderId();
        }

        else {
            trackOrder();
        }


        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE dd.MM.yyyy");
        Calendar currentCal = Calendar.getInstance();
        String currentdate=dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.DATE, 5);
        String toDate = dateFormat.format(currentCal.getTime());

        estimatedDate.setText(toDate);




        continueShopingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderTrackingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void trackOrder() {

        if(orderStatus.equals("wc-processing")){
            orderProcess.setBackground(ContextCompat.getDrawable(this,R.drawable.shape_status_completed));
        }

        /*else if (orderStatus.equals("wc-completed")){
            orderCompleted.setBackground(ContextCompat.getDrawable(this,R.drawable.shape_status_completed));
        }*/
    }

    private void getOrderId() {

        Call<List<OrderNumberModel>> listCall = apiService.getOrderNumber();

        listCall.enqueue(new Callback<List<OrderNumberModel>>() {
            @Override
            public void onResponse(Call<List<OrderNumberModel>> call, Response<List<OrderNumberModel>> response) {
                List<OrderNumberModel> orderNumberModelList = response.body();
                orderId.setText(orderNumberModelList.get(0).getOrder_Id());
            }

            @Override
            public void onFailure(Call<List<OrderNumberModel>> call, Throwable t) {

            }
        });
    }
}