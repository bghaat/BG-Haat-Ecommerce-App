package com.bgsourcingltd.bghaat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bgsourcingltd.bghaat.activities.AllCategoryActivity;
import com.bgsourcingltd.bghaat.activities.AllProductActivity;
import com.bgsourcingltd.bghaat.activities.CartListActivity;
import com.bgsourcingltd.bghaat.activities.ContactUsActivity;

import com.bgsourcingltd.bghaat.activities.CouponActivity;
import com.bgsourcingltd.bghaat.activities.OfferActivty;
import com.bgsourcingltd.bghaat.activities.SearchActivity;
import com.bgsourcingltd.bghaat.cartcounter.CartCounter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.userauth.UserAuthPreference;
import com.bgsourcingltd.bghaat.userauth.UserPhoneAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView cartIv,searchIv;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private TextView cartCounter;
    private UserAuthPreference preference;
    private UserPhoneAuth phoneAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        cartIv = findViewById(R.id.iv_cart);
        toolbar = findViewById(R.id.toolbar);
        searchIv = findViewById(R.id.iv_search);
        cartCounter = findViewById(R.id.cart_counter);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        preference = new UserAuthPreference(this);
        phoneAuth = new UserPhoneAuth(this);



        noInternetCheck();
        setNavigationDrawer();
        setBottomNavigation();



        cartIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));

            }
        });

        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });


    }


    private void setBottomNavigation() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    private void setNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()){

                    case R.id.category:
                        startActivity(new Intent(MainActivity.this, AllCategoryActivity.class));
                        break;
                    case R.id.cart:
                        startActivity(new Intent(MainActivity.this,CartListActivity.class));
                        break;

                    case R.id.contact:
                        startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                        break;

                    case R.id.rate:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                        break;

                    case R.id.allProduct:
                        startActivity(new Intent(MainActivity.this,AllProductActivity.class));
                        break;

                    case R.id.coupon:
                        startActivity(new Intent(MainActivity.this, CouponActivity.class));
                        break;


                }
                return true;
            }
        });

        //Show shared preference data from login info
        View view = navigationView.getHeaderView(0);
        TextView phoneNumber = (TextView) view.findViewById(R.id.tv_user_header_number);
        phoneNumber.setText(phoneAuth.getPhoneNumber());

    }

    /*@Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
        //getActivity().finish();
    }*/

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }
    }

    private void noInternetCheck() {
        // No Internet Dialog: Pendulum
        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                this,
                getLifecycle()
        );
        DialogPropertiesPendulum properties = builder.getDialogProperties();

        properties.setCancelable(false); // Optional
        properties.setNoInternetConnectionTitle("No Internet"); // Optional
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText("Please turn on"); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle("No Internet"); // Optional
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        properties.setPleaseTurnOffText("Please turn off"); // Optional
        properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();
    }


    @Override
    protected void onResume() {
        CartCounter cartCounterObj = new CartCounter(this);
        cartCounter.setText(String.valueOf(cartCounterObj.getCartValue()));
        super.onResume();
    }
}