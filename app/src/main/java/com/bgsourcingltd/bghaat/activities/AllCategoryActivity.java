package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.AllCategoryAdapter;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllCategoryActivity extends AppCompatActivity {
    private RecyclerView catAllRv;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        catAllRv = findViewById(R.id.rv_cat_all);
        toolbar = findViewById(R.id.all_cat_toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setCategoryRv();
    }



    private void setCategoryRv() {
        List<MainCategoryModel> list = new ArrayList<>();

        list.add(new MainCategoryModel("Gents",R.drawable.cotton_polo_shirt));
        list.add(new MainCategoryModel("Women",R.drawable.women));
        list.add(new MainCategoryModel("Grocery",R.drawable.grocery));
        list.add(new MainCategoryModel("Kids",R.drawable.kids));
        list.add(new MainCategoryModel("Electronics",R.drawable.device));
        list.add(new MainCategoryModel("Health & Beauty",R.drawable.women));

        AllCategoryAdapter adapter = new AllCategoryAdapter(this,list);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        catAllRv.setLayoutManager(layoutManager);
        catAllRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}