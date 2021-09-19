package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {
    private RecyclerView searchRv;
    private EditText searchEt;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEt = findViewById(R.id.et_search_all_product);
        searchRv = findViewById(R.id.rv_all_product);

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<List<NewArrivalModel>> listCall = apiService.getAllProduct();

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Toast.makeText(SearchActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });


    }
}