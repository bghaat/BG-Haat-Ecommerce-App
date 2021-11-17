package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.WishListAdapter;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.wishlistpreference.WishListPref;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ShowDetailsActivity extends AppCompatActivity {
    private TextView addToCardBtn ;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt,totalPriceTxt;
    private ImageView plusBtn, minusBtn,favIv,backIv;
    private int numberOrder = 1;
    private NewArrivalModel object;
    private ManagementCart managementCart;
    private PhotoView picFood;
    private LinearLayout shareLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);


        managementCart = new ManagementCart(this);
        initView();
        getBundle();

        favIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishListPref wishListPref = new WishListPref();
                wishListPref.addFavorite(ShowDetailsActivity.this,object);
                Toasty.success(ShowDetailsActivity.this, "Product Add your WishList", Toast.LENGTH_LONG, true).show();

            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject test");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "https://bghaat.com/");
                startActivity(Intent.createChooser(i,"Share via"));
            }
        });



        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowDetailsActivity.this, "Click Back Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*private void setFav(NewArrivalModel object) {

        List<NewArrivalModel> favList = new ArrayList<>();
            favList.add(object);
            WishListPref.setSharedPreferenceStringList(this,"wish_list",favList);

    }*/

    private void initView() {
        addToCardBtn = findViewById(R.id.addToCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.foodPic);
        favIv = findViewById(R.id.iv_fav);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        backIv = findViewById(R.id.iv_back);
        shareLayout = findViewById(R.id.layout_share);


    }

    private void getBundle() {
        object = (NewArrivalModel) getIntent().getSerializableExtra("object");
        Glide.with(this).load(object.getImage()).into(picFood);
        titleTxt.setText(object.getTitle());
        feeTxt.setText("৳ "+ object.getPrice());
        totalPriceTxt.setText("৳ "+ object.getPrice());
        descriptionTxt.setText(removeHtml(object.getDes()));
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText("৳ "+Math.round(numberOrder * Integer.parseInt(object.getPrice())));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText("৳ "+Math.round(numberOrder * Integer.parseInt(object.getPrice())));
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

    private String removeHtml(String html){
        html = html.replaceAll("<(.*?)\\>"," ");
        html = html.replaceAll("<(.*?)\\\n"," ");
        html = html.replaceAll("(.*?)\\>"," ");
        html = html.replaceAll("&nbsp"," ");
        html = html.replaceAll("&amp"," ");
        return html;

    }

    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }*/
}