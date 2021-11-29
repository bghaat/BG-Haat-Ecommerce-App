package com.bgsourcingltd.bghaat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.CouponAdapter;
import com.bgsourcingltd.bghaat.models.CouponCodeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CouponActivity extends AppCompatActivity {
    private Toolbar couponTb;
    private RecyclerView couponRv;
    public static List<CouponCodeModel> list;
    CouponAdapter adapter;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        progressDialog = new ProgressDialog(this);
        couponTb = findViewById(R.id.toolbar_coupon);
        couponRv = findViewById(R.id.rv_coupon);
        couponRv.setLayoutManager(new LinearLayoutManager(this));

        couponTb.setTitle("Coupon Code");
        setSupportActionBar(couponTb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance("https://bg-haat-e5629-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("user");

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    CouponCodeModel model = ds.getValue(CouponCodeModel.class);
                    list.add(model);

                }

                adapter = new CouponAdapter(CouponActivity.this,list);
                couponRv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}