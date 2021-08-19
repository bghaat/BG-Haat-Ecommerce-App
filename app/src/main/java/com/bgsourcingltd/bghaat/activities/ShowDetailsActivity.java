package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class ShowDetailsActivity extends AppCompatActivity {
    private TextView addToCardBtn ;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private int numberOrder = 1;
    private NewArrivalModel object;
    private ManagementCart managementCart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);


        managementCart = new ManagementCart(this);
        initView();
        getBundle();

    }


    private void initView() {
        addToCardBtn = findViewById(R.id.addToCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.foodPic);


    }

    private void getBundle() {
        object = (NewArrivalModel) getIntent().getSerializableExtra("object");
        Glide.with(this).load(object.getImageUrl()).into(picFood);
        titleTxt.setText(object.getFoodTitle());
        feeTxt.setText("$" + object.getPrice());
        //descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);

                Snackbar snackbar = Snackbar.make(v,"You have added new Product in Cart",Snackbar.LENGTH_LONG);
                snackbar.setDuration(4000);
                snackbar.setAnchorView(addToCardBtn);
                snackbar.setAction("View", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ShowDetailsActivity.this,CartListActivity.class));
                    }
                });
                snackbar.show();

            }
        });

    }
}