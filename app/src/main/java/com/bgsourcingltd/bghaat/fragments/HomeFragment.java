package com.bgsourcingltd.bghaat.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.AllCategoryActivity;
import com.bgsourcingltd.bghaat.activities.CategoryDetailsActivity;
import com.bgsourcingltd.bghaat.activities.OfferActivty;
import com.bgsourcingltd.bghaat.adapters.BestSellingCatAdapter;
import com.bgsourcingltd.bghaat.adapters.GroceryAdapter;
import com.bgsourcingltd.bghaat.adapters.MainCatAdapter;
import com.bgsourcingltd.bghaat.adapters.NewArrivalCatAdapter;
import com.bgsourcingltd.bghaat.adapters.SliderAdapter;
import com.bgsourcingltd.bghaat.adapters.WomensAdapter;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.models.OfferModel;
import com.bgsourcingltd.bghaat.models.SliderModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    SliderView sliderView;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseReference databaseReference;
    private RecyclerView rvMainCategory,rvNewArrivalCategory,rvBestSelling,rvWomensCat,rvGrocery;
    private Context context;
    private ProgressDialog progressDialog;
    private ApiService apiService;

    private TextView mainCatTv,newArrivalTv,healthBeautyTv,womensViewAllTv,offerDateTv,offerTitle,groceryViewAll;
    private ImageView offerIv;
    private ConstraintLayout offerLayout;


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
        rvWomensCat = view.findViewById(R.id.rv_womens_fashion);
        rvGrocery = view.findViewById(R.id.rv_grocery);
        mainCatTv =  view.findViewById(R.id.tv_main_cat_viewAll);
        newArrivalTv = view.findViewById(R.id.tv_new_arrival_viewall);
        healthBeautyTv = view.findViewById(R.id.tv_best_selling_view_all);
        womensViewAllTv = view.findViewById(R.id.tv_womens_fasion_view_all);
        offerLayout = view.findViewById(R.id.cv_offer);
        progressDialog = new ProgressDialog(context);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        offerDateTv = view.findViewById(R.id.tv_offer_date);
        offerTitle = view.findViewById(R.id.tv_percent_off);
        offerIv = view.findViewById(R.id.iv_flash_sale);
        groceryViewAll = view.findViewById(R.id.tv_grocery_view_all);



        apiService = ApiClient.getRetrofit().create(ApiService.class);
        databaseReference = FirebaseDatabase.getInstance("https://bg-haat-e5629-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("offer");


        setSlider();
        setNewArrivalCategory();
        setMainCategory();
        setBestSelling();
        setWomensFasion();
        setOffer();
        setGrocery();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setNewArrivalCategory();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mainCatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AllCategoryActivity.class));

            }
        });

        newArrivalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                intent.putExtra("catName","Gents");
                startActivity(intent);
            }
        });

        healthBeautyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                intent.putExtra("catName","Health & Beauty");
                startActivity(intent);
            }
        });

        womensViewAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                intent.putExtra("catName","Women");
                startActivity(intent);
            }
        });

        offerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OfferActivty.class);
                startActivity(intent);
            }
        });

        groceryViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                intent.putExtra("catName","Grocery");
                startActivity(intent);
            }
        });

    }

    private void setSlider() {

        Call<List<SliderModel>> listCall = apiService.getSlider();


        listCall.enqueue(new Callback<List<SliderModel>>() {
            @Override
            public void onResponse(Call<List<SliderModel>> call, Response<List<SliderModel>> response) {
                List<SliderModel> sliderModelList = response.body();

                SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList,context);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                sliderView.startAutoCycle();

            }

            @Override
            public void onFailure(Call<List<SliderModel>> call, Throwable t) {

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
        mainCategoryModelList.add(new MainCategoryModel("Health & Beauty",R.drawable.women));

        MainCatAdapter adapter = new MainCatAdapter(context,mainCategoryModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMainCategory.setLayoutManager(linearLayoutManager);
        rvMainCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setNewArrivalCategory() {

        Call<List<NewArrivalModel>> listCall = apiService.getGentsProduct();

        swipeRefreshLayout.setRefreshing(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {

            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){

                    List<NewArrivalModel> newArrivalModelList = response.body();
                    Collections.reverse(newArrivalModelList);
                    NewArrivalCatAdapter adapter = new NewArrivalCatAdapter(context,newArrivalModelList);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvNewArrivalCategory.setLayoutManager(manager);
                    rvNewArrivalCategory.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });


    }

    private void setBestSelling() {

        Call<List<NewArrivalModel>> listCall = apiService.getHeathBeauty();

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){
                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    BestSellingCatAdapter adapter = new BestSellingCatAdapter(list,context);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvBestSelling.setLayoutManager(manager);
                    rvBestSelling.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });

    }

    private void setWomensFasion() {
        Call<List<NewArrivalModel>> listCall = apiService.getWomenProduct();

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                if (response.isSuccessful()){

                    List<NewArrivalModel> list = response.body();
                    Collections.reverse(list);
                    WomensAdapter adapter = new WomensAdapter(context,list);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvWomensCat.setLayoutManager(manager);
                    rvWomensCat.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });

    }

    private void setOffer(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OfferModel offerModel = dataSnapshot.getValue(OfferModel.class);

                    offerDateTv.setText(offerModel.getDate());
                    offerTitle.setText(offerModel.getTitle());

                    //Glide.with(context).load(offerModel.getImageUrl()).into(offerIv);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setGrocery(){

        Call<List<NewArrivalModel>> listCall = apiService.getGroceryProduct();

        listCall.enqueue(new Callback<List<NewArrivalModel>>() {
            @Override
            public void onResponse(Call<List<NewArrivalModel>> call, Response<List<NewArrivalModel>> response) {
                List<NewArrivalModel> list = response.body();
                Collections.reverse(list);

                GroceryAdapter adapter = new GroceryAdapter(list,context);
                GridLayoutManager manager = new GridLayoutManager(context,2);
                rvGrocery.setLayoutManager(manager);
                rvGrocery.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<NewArrivalModel>> call, Throwable t) {

            }
        });

    }


}