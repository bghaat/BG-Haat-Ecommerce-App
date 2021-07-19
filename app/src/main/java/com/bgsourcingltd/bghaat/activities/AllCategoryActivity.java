package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.AllCategoryAdapter;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryActivity extends AppCompatActivity {
    private RecyclerView catAllRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        catAllRv = findViewById(R.id.rv_cat_all);

        setCategoryRv();
    }

    private void setCategoryRv() {
        List<MainCategoryModel> list = new ArrayList<>();

        list.add(new MainCategoryModel("Gents",R.drawable.cotton_polo_shirt));
        list.add(new MainCategoryModel("Women",R.drawable.women));
        list.add(new MainCategoryModel("Grocery",R.drawable.grocery));
        list.add(new MainCategoryModel("Kids",R.drawable.kids));
        list.add(new MainCategoryModel("Electronics Device",R.drawable.device));

        AllCategoryAdapter adapter = new AllCategoryAdapter(this,list);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        catAllRv.setLayoutManager(layoutManager);
        catAllRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}