package com.bgsourcingltd.bghaat.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.AllProductAdapter;
import com.bgsourcingltd.bghaat.adapters.CatDetailsAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {
    private RecyclerView searchRv;
    private EditText searchEt;
    private List<NewArrivalModel> list = new ArrayList<>();
    private ApiService apiService;
    private AllProductAdapter adapter;
    List<NewArrivalModel> filterList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEt = findViewById(R.id.et_search_all_products);
        searchRv = findViewById(R.id.rv_all_product);
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        callAllProductAPI();

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList.clear();

                if (s.toString().isEmpty()){
                    searchRv.setAdapter(new AllProductAdapter(list,SearchActivity.this));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Filter(s.toString());
                }

            }
        });


    }

    private void callAllProductAPI() {
        Call<List<NewArrivalModel>> listCall = apiService.getAllProduct();

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    list = response.body();

                    Toast.makeText(SearchActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                    adapter = new AllProductAdapter(list,SearchActivity.this);
                    GridLayoutManager manager = new GridLayoutManager(SearchActivity.this,2);
                    searchRv.setLayoutManager(manager);
                    searchRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {
                Log.e("search", "onFailure: "+t.getLocalizedMessage());

            }
        });

    }

    private void Filter(String text){
        for (NewArrivalModel model : list){
            if (model.getTitle().equals(text)){
                filterList.add(model);
            }
        }

        searchRv.setAdapter(new AllProductAdapter(filterList,SearchActivity.this));



    }


}