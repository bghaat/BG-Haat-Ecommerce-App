package com.bgsourcingltd.bghaat.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.AllCategoryActivity;
import com.bgsourcingltd.bghaat.activities.CartListActivity;
import com.bgsourcingltd.bghaat.adapters.BestSellingCatAdapter;
import com.bgsourcingltd.bghaat.adapters.MainCatAdapter;
import com.bgsourcingltd.bghaat.adapters.NewArrivalCatAdapter;
import com.bgsourcingltd.bghaat.adapters.SliderAdapter;
import com.bgsourcingltd.bghaat.adapters.TopBrandsAdapter;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.models.TopBrandsModel;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    SliderView sliderView;

    int[] images = {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.app};
    private RecyclerView rvMainCategory,rvNewArrivalCategory,rvBestSelling,rvTopBrands;
    private Context context;
    private ProgressDialog progressDialog;

    private TextView mainCatTv;


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
        rvNewArrivalCategory = view.findViewById(R.id.rv_new_arrival);
        rvBestSelling = view.findViewById(R.id.rv_best_selling);
        rvTopBrands = view.findViewById(R.id.rv_top_brands);
        mainCatTv =  view.findViewById(R.id.tv_main_cat_viewAll);
        progressDialog = new ProgressDialog(context);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


        setMainCategory();
        setNewArrivalCategory();
        setBestSelling();
        setTopBrands();

        //click view all category

        mainCatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AllCategoryActivity.class));

            }
        });

    }

    private void setMainCategory() {
        List<MainCategoryModel> mainCategoryModelList = new ArrayList<>();

        mainCategoryModelList.add(new MainCategoryModel("Gents",R.drawable.cotton_polo_shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.women));
        mainCategoryModelList.add(new MainCategoryModel("Grocery",R.drawable.grocery));
        mainCategoryModelList.add(new MainCategoryModel("Kids",R.drawable.kids));
        mainCategoryModelList.add(new MainCategoryModel("Electronics",R.drawable.device));

        MainCatAdapter adapter = new MainCatAdapter(context,mainCategoryModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMainCategory.setLayoutManager(linearLayoutManager);
        rvMainCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setNewArrivalCategory() {
        List<NewArrivalModel> newArrivalModelList = new ArrayList<>();

        newArrivalModelList.add(new NewArrivalModel(R.drawable.deshi_chesse,"Deshi Chesee Regular",150.00,"100"));
        newArrivalModelList.add(new NewArrivalModel(R.drawable.deshi_chesse,"Mozarella cheese",250.0,"100"));
        newArrivalModelList.add(new NewArrivalModel(R.drawable.deshi_chesse,"Deshi Oil Regular",250.0,"100"));
        newArrivalModelList.add(new NewArrivalModel(R.drawable.deshi_chesse,"Garlic Muri",250.0,"100"));
        newArrivalModelList.add(new NewArrivalModel(R.drawable.deshi_chesse,"Deshi Chesee Regular",250.0,"100"));


        NewArrivalCatAdapter adapter = new NewArrivalCatAdapter(context,newArrivalModelList);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvNewArrivalCategory.setLayoutManager(manager);
        rvNewArrivalCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void setBestSelling() {

        List<NewArrivalModel> list = new ArrayList<>();

        list.add(new NewArrivalModel(R.drawable.muri,"Demo Product Name",150.00,"100"));
        list.add(new NewArrivalModel(R.drawable.deshi_chesse,"Demo Product Name",250.0,"100"));
        list.add(new NewArrivalModel(R.drawable.muri,"Demo Product Name",250.0,"100"));
        list.add(new NewArrivalModel(R.drawable.deshi_chesse,"Demo Product Name",250.0,"100"));
        list.add(new NewArrivalModel(R.drawable.deshi_chesse,"Demo Product Name",250.0,"100"));


        BestSellingCatAdapter adapter = new BestSellingCatAdapter(list,context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvBestSelling.setLayoutManager(manager);
        rvBestSelling.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void setTopBrands() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        List<TopBrandsModel> topBrandsModelList = new ArrayList<>();
        topBrandsModelList.add(new TopBrandsModel("ACI",R.drawable.logo));
        topBrandsModelList.add(new TopBrandsModel("ACI",R.drawable.logo));
        topBrandsModelList.add(new TopBrandsModel("ACI",R.drawable.logo));
        topBrandsModelList.add(new TopBrandsModel("ACI",R.drawable.logo));
        topBrandsModelList.add(new TopBrandsModel("ACI",R.drawable.logo));

        TopBrandsAdapter adapter = new TopBrandsAdapter(topBrandsModelList,context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTopBrands.setLayoutManager(manager);
        rvTopBrands.setAdapter(adapter);
        progressDialog.dismiss();

    }
}