package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.AllProductAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllProductActivity extends AppCompatActivity {
    private RecyclerView allProductRv;
    private Toolbar toolbar;
    private ApiService apiService;
    private AllProductAdapter adapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        toolbar = findViewById(R.id.toolbar_all_product);
        allProductRv = findViewById(R.id.rv_all_product);
        progressDialog = new ProgressDialog(this);

        toolbar.setTitle("All Product");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        apiService = ApiClient.getRetrofit().create(ApiService.class);

        Call<List<NewArrivalModel>> listCall = apiService.getAllProduct();

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    callAllProductAPI(list);

                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {
                Log.e("search", "onFailure: "+t.getLocalizedMessage());

            }
        });

    }

    private void callAllProductAPI(List<NewArrivalModel> list) {

        adapter = new AllProductAdapter(list,AllProductActivity.this);
        GridLayoutManager manager = new GridLayoutManager(AllProductActivity.this,2);
        allProductRv.setLayoutManager(manager);
        allProductRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_product);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}