package com.bgsourcingltd.bghaat.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.MainCatAdapter;
import com.bgsourcingltd.bghaat.adapters.SliderAdapter;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    SliderView sliderView;

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

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();



        setMainCategory();


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

    private void setMainCategory() {

        List<MainCategoryModel> mainCategoryModelList = new ArrayList<>();
        mainCategoryModelList.add(new MainCategoryModel("Gents",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Women",R.drawable.logo));
        /*mainCategoryModelList.add(new MainCategoryModel("Kids",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Grocery",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Cocking",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Helath",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Gents",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Gents",R.drawable.shirt));
        mainCategoryModelList.add(new MainCategoryModel("Gents",R.drawable.shirt));*/

        MainCatAdapter adapter = new MainCatAdapter(context,mainCategoryModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMainCategory.setLayoutManager(linearLayoutManager);
        rvMainCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}