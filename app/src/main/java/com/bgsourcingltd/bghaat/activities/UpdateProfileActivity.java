package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.OrderResponse;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText updateNameEt,updatePhoneEt,updateEmailEt,updateAddressEt;
    private Button updateProfileBtn;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        updateNameEt = findViewById(R.id.et_update_name);
        updatePhoneEt = findViewById(R.id.et_update_phone);
        updateEmailEt = findViewById(R.id.et_update_email);
        updateAddressEt = findViewById(R.id.et_update_address);

        updateProfileBtn = findViewById(R.id.btn_update_profile);

        apiService = ApiClient.getRetrofit().create(ApiService.class);



        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = updateNameEt.getText().toString();
                String phone = updatePhoneEt.getText().toString();
                String email = updateEmailEt.getText().toString();
                String address = updateAddressEt.getText().toString();

                if (userName.isEmpty()){
                    Toasty.warning(UpdateProfileActivity.this, "Name Required", Toast.LENGTH_LONG, true).show();
                }
                else if (phone.isEmpty()){
                    Toasty.warning(UpdateProfileActivity.this, "Phone Required", Toast.LENGTH_LONG, true).show();
                }

                else if (email.isEmpty()){
                    Toasty.warning(UpdateProfileActivity.this, "Email Required", Toast.LENGTH_LONG, true).show();
                }

                else if (address.isEmpty()){
                    Toasty.warning(UpdateProfileActivity.this, "Address Required", Toast.LENGTH_LONG, true).show();
                }

                else {

                    Call<OrderResponse> listCall = apiService.postProfile(userName, phone, email, address);

                    listCall.enqueue(new Callback<OrderResponse>() {
                        @Override
                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                            OrderResponse orderResponse = response.body();
                            Toasty.success(UpdateProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_LONG, true).show();
                            UpdateProfileActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Call<OrderResponse> call, Throwable t) {

                        }
                    });

                }


            }
        });
    }
}