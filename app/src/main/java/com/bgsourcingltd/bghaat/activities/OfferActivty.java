package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.Bundle;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.FlashSaleAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferActivty extends AppCompatActivity {
    private Toolbar flashSaleTb;
    private RecyclerView flashSaleRv;

    private ApiService apiService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_activty);

        flashSaleTb = findViewById(R.id.toolbar_flash_sale);
        flashSaleRv = findViewById(R.id.rv_flash_sale);
        progressDialog = new ProgressDialog(this);

        setSupportActionBar(flashSaleTb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        apiService = ApiClient.getRetrofit().create(ApiService.class);

        Call<List<NewArrivalModel>> listCall = apiService.getFlashSale();

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                List<NewArrivalModel> list = response.body();
                Collections.reverse(list);
                FlashSaleAdapter adapter = new FlashSaleAdapter(list,OfferActivty.this);
                GridLayoutManager manager = new GridLayoutManager(OfferActivty.this,2);
                flashSaleRv.setLayoutManager(manager);
                flashSaleRv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}