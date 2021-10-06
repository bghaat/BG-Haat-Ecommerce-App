package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.CatDetailsAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ApiService apiService;
    private RecyclerView catDetailsRv;
    private ProgressDialog progressDialog;
    private String catTitle;
    private CatDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        toolbar = findViewById(R.id.toolbar_details_cat);
        catDetailsRv = findViewById(R.id.cat_details_rv);
        progressDialog = new ProgressDialog(this);

        apiService = ApiClient.getRetrofit().create(ApiService.class);

        catTitle = getIntent().getStringExtra("catName");

        toolbar.setTitle(catTitle);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if (catTitle.equals("Women")){
            callWomensAPI();
        }
        else if (catTitle.equals("Gents")){
            callGentsAPI();
        }
        else if (catTitle.equals("Grocery")){
            callGroceryAPI();
        }
        else if (catTitle.equals("Electronics")){
            callElectronicsAPI();
        }
        else if (catTitle.equals("Health & Beauty")){
            callHealthBeautyAPI();

        }
        else if (catTitle.equals("Kids")){
            callKidsAPI();

        }
        else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void callHealthBeautyAPI() {

        Call<List<NewArrivalModel>> healthBeautyList = apiService.getHeathBeauty();

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        healthBeautyList.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    adapter = new CatDetailsAdapter(CategoryDetailsActivity.this, list);
                    GridLayoutManager manager = new GridLayoutManager(CategoryDetailsActivity.this,2);
                    catDetailsRv.setLayoutManager(manager);
                    catDetailsRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });
    }

    private void callElectronicsAPI() {

        Call<List<NewArrivalModel>> electronicsList = apiService.getElectronicsProduct();

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        electronicsList.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    adapter = new CatDetailsAdapter(CategoryDetailsActivity.this,list);
                    GridLayoutManager manager = new GridLayoutManager(CategoryDetailsActivity.this,2);
                    catDetailsRv.setLayoutManager(manager);
                    catDetailsRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });
    }

    private void callGroceryAPI() {

        Call<List<NewArrivalModel>> groceryList = apiService.getGroceryProduct();

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        groceryList.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    adapter = new CatDetailsAdapter(CategoryDetailsActivity.this,list);
                    GridLayoutManager manager = new GridLayoutManager(CategoryDetailsActivity.this,2);
                    catDetailsRv.setLayoutManager(manager);
                    catDetailsRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });
    }

    private void callGentsAPI() {

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        Call<List<NewArrivalModel>> gentsList = apiService.getGentsProduct();
        gentsList.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    adapter = new CatDetailsAdapter(CategoryDetailsActivity.this,list);
                    GridLayoutManager manager = new GridLayoutManager(CategoryDetailsActivity.this,2);
                    catDetailsRv.setLayoutManager(manager);
                    catDetailsRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });
    }

    private void callWomensAPI() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Call<List<NewArrivalModel>> listCall = apiService.getWomenProduct();
        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){

                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    adapter = new CatDetailsAdapter(CategoryDetailsActivity.this,list);
                    GridLayoutManager manager = new GridLayoutManager(CategoryDetailsActivity.this,2);
                    catDetailsRv.setLayoutManager(manager);
                    catDetailsRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });

    }
    private void callKidsAPI(){
        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Call<List<NewArrivalModel>> listCall = apiService.getKidsProduct();
        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){

                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    adapter = new CatDetailsAdapter(CategoryDetailsActivity.this,list);
                    GridLayoutManager manager = new GridLayoutManager(CategoryDetailsActivity.this,2);
                    catDetailsRv.setLayoutManager(manager);
                    catDetailsRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });
    }

    @Override
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}