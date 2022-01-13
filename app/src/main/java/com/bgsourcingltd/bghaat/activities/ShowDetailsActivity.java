package com.bgsourcingltd.bghaat.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.Interface.ImageVariationsInterface;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.ProductVariationAdapter;
import com.bgsourcingltd.bghaat.adapters.WishListAdapter;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.wishlistpreference.WishListPref;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ShowDetailsActivity extends AppCompatActivity implements ImageVariationsInterface {
    private TextView addToCardBtn ;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt,totalPriceTxt;
    private ImageView plusBtn, minusBtn,favIv;
    private int numberOrder = 1;
    private NewArrivalModel object;
    private ManagementCart managementCart;
    private ImageView picFood,backIv;
    private LinearLayout shareLayout,sizeLayout;
    private List<String> stringList;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String checkedRadioButtonText = "";
    private RecyclerView productVariationsRv;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);


        statusTransparent();

        managementCart = new ManagementCart(this);
        initView();
        getBundle();


        favIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishListPref wishListPref = new WishListPref();
                wishListPref.addFavorite(ShowDetailsActivity.this,object);
                Toasty.success(ShowDetailsActivity.this, "Product Add your WishList", Toast.LENGTH_SHORT, true).show();

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
        favIv = findViewById(R.id.iv_fav);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        shareLayout = findViewById(R.id.layout_share);
        sizeLayout = findViewById(R.id.layout_size);
        backIv = findViewById(R.id.iv_back_product_details);
        radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        productVariationsRv = findViewById(R.id.rv_variation_image);
        stringList = new ArrayList<>();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getBundle() {
        object = (NewArrivalModel) getIntent().getSerializableExtra("object");
        Glide.with(this).load(object.getImage()).into(picFood);
        titleTxt.setText(object.getTitle());
        feeTxt.setText("৳ "+ object.getPrice());
        totalPriceTxt.setText("৳ "+ object.getPrice());
        descriptionTxt.setText(removeHtml(object.getDes()));
        numberOrderTxt.setText(String.valueOf(numberOrder));




        ProductVariationAdapter adapter = new ProductVariationAdapter(object.getVariationsImage(),this,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        productVariationsRv.setLayoutManager(manager);
        productVariationsRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        if (!Objects.isNull(object.getProductAttr())) {
            stringList = object.getProductAttr().getSize();
            RadioGroup.LayoutParams layoutParams;


            for (int i = 0; i < stringList.size(); i++) {
                radioButton = new RadioButton(this);
                radioButton.setText(stringList.get(i));
                radioButton.setPadding(10, 0, 0, 0);

                layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, 10, 0, 0);
                radioGroup.addView(radioButton, layoutParams);

            }
            sizeLayout.addView(radioGroup);
        }



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkRadioButton = radioGroup.findViewById(checkedId);
                int checkRadioButtonId = radioGroup.indexOfChild(checkRadioButton);
                checkedRadioButtonText = checkRadioButton.getText().toString();

                Toast.makeText(ShowDetailsActivity.this, ""+checkedRadioButtonText, Toast.LENGTH_SHORT).show();
            }
        });



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
                if (imageUrl != null){
                    object.setImage(imageUrl);
                }
                object.setNumberInCart(numberOrder);
                //object.setImage(imageUrl);
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
        html = html.replaceAll("<(.*?)\\>","");
        html = html.replaceAll("<(.*?)\\\n","");
        html = html.replaceAll("(.*?)\\>","");
        html = html.replaceAll("&nbsp","");
        html = html.replaceAll("&amp","");
        html = html.replaceAll(";","");
        return html;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public void onItemClick(String imageUrl) {
        Glide.with(this).load(imageUrl).into(picFood);
        this.imageUrl = imageUrl;

    }

    private void statusTransparent(){
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}