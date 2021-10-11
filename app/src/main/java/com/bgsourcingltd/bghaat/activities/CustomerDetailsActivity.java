package com.bgsourcingltd.bghaat.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.models.OrderResponse;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {
    private EditText nameEt,phoneEt,locationEt;
    private MaterialButton submitBtn;
    private List<NewArrivalModel> orderList;
    private ApiService apiService;
    private String json;
    private ProgressDialog progressDialog;
    RadioButton cashRb,onlineRb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);


        nameEt = findViewById(R.id.et_name);
        phoneEt = findViewById(R.id.et_phone);
        locationEt = findViewById(R.id.et_phone);
        submitBtn = findViewById(R.id.btn_submit);
        progressDialog = new ProgressDialog(this);
        cashRb = findViewById(R.id.rg_cash_on);
        onlineRb = findViewById(R.id.rg_online_payment);

        //json = new Gson().toJson(managementCart.getListCard());

        /*orderList = (List<NewArrivalModel>) getIntent().getSerializableExtra("order");

        if (orderList != null){


            Toast.makeText(CustomerDetailsActivity.this, ""+json, Toast.LENGTH_LONG).show();
        }*/


        json = getIntent().getStringExtra("cart");
        Log.d("catchjson", " "+json);
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        RadioGroup radioGroup = findViewById(R.id.rg);



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cashRb.isChecked()){

                String name = nameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String location = locationEt.getText().toString();

                Log.d("list", "onCreate: " + json);


                Call<OrderResponse> orderResponseCall = apiService.postOrder(name, phone, location, json);

                progressDialog.show();
                progressDialog.setContentView(R.layout.show_dialog_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                orderResponseCall.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        OrderResponse orderResponse = response.body();
                        Toast.makeText(CustomerDetailsActivity.this, "" + orderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Log.e("order", "onFailure " + t.getLocalizedMessage());

                    }
                });

            }
                else if (onlineRb.isChecked()){
                    Toast.makeText(CustomerDetailsActivity.this, "Go to payment activity", Toast.LENGTH_SHORT).show();
                }
                else if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(CustomerDetailsActivity.this, "Select Payment Method", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomerDetailsActivity.this.finish();
    }
}