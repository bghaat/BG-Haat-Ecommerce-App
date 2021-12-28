package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bgsourcingltd.bghaat.Interface.ChangeNumberItemsListener;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.CartListAdapter;
import com.bgsourcingltd.bghaat.adapters.CouponAdapter;
import com.bgsourcingltd.bghaat.helper.Constraint;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.CouponCodeModel;
import com.bgsourcingltd.bghaat.userauth.CouponAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt,checkOut;
    private EditText couponEt;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private double tax;
    private ScrollView scrollView;
    private Gson gson;
    private double total;
    private Button applyBtn;
    private boolean appliedAlready = true;
    private CouponAuth couponAuth;
    //private List<NewArrivalModel> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);
        initView();
        initList();
        calculateCard();



        couponAuth = new CouponAuth(CartListActivity.this);
        //orders = managementCart.getListCard();
        gson = new GsonBuilder().setPrettyPrinting().create();

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutButtonClicked();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orginCode = couponEt.getText().toString();
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String getCurrentDateTime = sdf.format(calendar.getTime());
                String getMyTime = Constraint.couponDate;



                if (getCurrentDateTime.compareTo(getMyTime) < 0) {
                    if (couponAuth.getCouponValue()) {
                        total = total - Double.parseDouble(Constraint.couponAmount);
                        totalTxt.setText("৳" + total);
                        Toasty.info(CartListActivity.this, "You Have Got Discount", Toast.LENGTH_SHORT, true).show();

                    }
                    else {
                        Toasty.info(CartListActivity.this, "You Already Used Coupon Code", Toast.LENGTH_SHORT, true).show();
                    }

                }
                else {
                    Toast.makeText(CartListActivity.this, "Date Is not valid", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCard(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCard() {
        double percentTax = 0.02;
        double delivery = 10;

        //tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
        total = Math.round((managementCart.getTotalFee()) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;

        totalFeeTxt.setText("৳" + itemTotal);
        /*taxTxt.setText("৳" + tax);
        deliveryTxt.setText("৳" + delivery);*/
        totalTxt.setText("৳" + total);
    }


    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerview);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        /*taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);*/
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView4);
        checkOut = findViewById(R.id.btn_checkOut);
        couponEt = findViewById(R.id.et_coupon);
        applyBtn = findViewById(R.id.btn_apply);
    }

    private void checkOutButtonClicked(){
        Intent intent = new Intent(CartListActivity.this,CustomerDetailsActivity.class);
        //intent.putExtra("order", (Serializable) orders);
        String json = gson.toJson(managementCart.getListCard());
        intent.putExtra("cart",json);
        intent.putExtra("totalAmount",total);
        Log.d("json", "json "+json);
        couponAuth.setCouponValue(false);
        startActivity(intent);

    }
}