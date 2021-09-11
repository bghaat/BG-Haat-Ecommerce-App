package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;

import java.util.Objects;

public class CategoryDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        toolbar = findViewById(R.id.toolbar_details_cat);

        String catTitle = getIntent().getStringExtra("catName");
        Toast.makeText(this, ""+catTitle, Toast.LENGTH_SHORT).show();

        toolbar.setTitle(catTitle);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}