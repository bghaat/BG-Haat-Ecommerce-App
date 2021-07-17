package com.bgsourcingltd.bghaat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.activities.CartListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView menuIv,cartIv;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIv = findViewById(R.id.iv_menu);
        cartIv = findViewById(R.id.iv_cart);

        setBottomNavigation();
        setNavigationDrawer();

        cartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
    }

    private void setBottomNavigation() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    private void setNavigationDrawer() {
        menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    case R.id.wish_list:
                        Toast.makeText(MainActivity.this, "wishList", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}