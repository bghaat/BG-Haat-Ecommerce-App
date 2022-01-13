package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.SearchAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {
    private EditText searchRt;
    private List<NewArrivalModel> list;
    private ApiService apiService;
    private RecyclerView searchRv;
    private ProgressDialog progressDialog;
    private ImageView searchBackIv;
    private TextView noFoundTv;
    private ChipGroup searchChipGroup;
    private Chip tShirt,hoodies,watch,ladies,kids;
    private String searchInput = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchRt = findViewById(R.id.et_search);
        searchRv = findViewById(R.id.rv_search);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        progressDialog = new ProgressDialog(this);
        searchBackIv = findViewById(R.id.iv_back);
        noFoundTv = findViewById(R.id.tv_no_product_found);
        searchChipGroup = findViewById(R.id.search_chip_group);
        tShirt = findViewById(R.id.chip_tshirt);
        hoodies = findViewById(R.id.chip_hoodies);
        watch = findViewById(R.id.chip_watch);
        ladies = findViewById(R.id.chip_ladies);
        kids = findViewById(R.id.chip_kids);

        tShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = tShirt.getText().toString();
                callSearchApi(searchInput);

            }
        });

        hoodies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = hoodies.getText().toString();
                callSearchApi(searchInput);
            }
        });

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = watch.getText().toString();
                callSearchApi(searchInput);
            }
        });

        ladies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = ladies.getText().toString().trim();
                callSearchApi(searchInput);
            }
        });

        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = kids.getText().toString();
                callSearchApi(searchInput);
            }
        });


        searchRt.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchInput = searchRt.getText().toString();

                callSearchApi(searchInput);


                return true;
            }

            return false;

        });


        searchBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callSearchApi(String searchString){

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Call<List<NewArrivalModel>> listCall = apiService.getSearch(searchString);
        listCall.enqueue(new Callback<List<NewArrivalModel>>() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                list = response.body();
                if (list.isEmpty()){
                    noFoundTv.setVisibility(View.VISIBLE);
                    searchRv.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    hideKeyBoared();

                }
                else {

                    noFoundTv.setVisibility(View.GONE);
                    searchRv.setVisibility(View.VISIBLE);
                    Collections.reverse(list);
                    SearchAdapter adapter = new SearchAdapter(list, SearchActivity.this);
                    GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 2);
                    searchRv.setLayoutManager(manager);
                    searchRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                    hideKeyBoared();

                }

            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });
    }

    private void hideKeyBoared(){
        InputMethodManager in = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchRt.getWindowToken(), 0);
    }

}