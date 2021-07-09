package com.bgsourcingltd.bghaat.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.MainCatAdapter;
import com.bgsourcingltd.bghaat.adapters.SliderAdapter;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private DrawerLayout drawerLayout;
    private ImageView menuIv;
    SliderView sliderView;
    private NavigationView navigationView;

    int[] images = {R.drawable.one,R.drawable.two,R.drawable.three};
    private RecyclerView rvMainCategory;
    private Context context;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sliderView = view.findViewById(R.id.image_slider);
        rvMainCategory = view.findViewById(R.id.rv_main_category);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);
        menuIv = view.findViewById(R.id.iv_menu);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();




        setMainCategory();
        setNavigationDrawer();


        //Fragment OnBack Pressed
        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                //getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), backPressedCallback);
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
                        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
                    case R.id.wish_list:
                        Toast.makeText(context, "wishList", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void setMainCategory() {
        List<MainCategoryModel> mainCategoryModelList = new ArrayList<>();

        mainCategoryModelList.add(new MainCategoryModel("Gents",R.drawable.polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.polo_shirt));

        MainCatAdapter adapter = new MainCatAdapter(context,mainCategoryModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMainCategory.setLayoutManager(linearLayoutManager);
        rvMainCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}