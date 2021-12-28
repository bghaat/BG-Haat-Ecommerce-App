package com.bgsourcingltd.bghaat.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.Interface.DeleteWishListListener;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.WishListAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.wishlistpreference.WishListPref;

import java.util.ArrayList;
import java.util.List;


public class WishListFragment extends Fragment {


    private Context context;
    private RecyclerView wishListRv;
    WishListAdapter adapter;
    WishListPref wishListPref;
    private TextView emptyWishListTv,clearAllTv;
    private ImageView emptyIv;
    List<NewArrivalModel> wishList;
    private ConstraintLayout containerLayout;



    public WishListFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_list, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        wishListRv = view.findViewById(R.id.rv_wish_list);
        emptyWishListTv = view.findViewById(R.id.tv_empty_wishlist);
        emptyIv = view.findViewById(R.id.iv_empty_wish_list);
        clearAllTv = view.findViewById(R.id.tv_clear_all_wishlist);
        containerLayout = view.findViewById(R.id.cl_container);

        wishList = new ArrayList<>();
        wishListPref = new WishListPref();
        wishList = wishListPref.getFavorites(context);


        clearAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wishListPref.removeFavorite(context);

            }
        });

        //list = wishListPref.getFavorites(context);
        if (wishList == null){
            emptyWishListTv.setVisibility(View.VISIBLE);
            emptyIv.setVisibility(View.VISIBLE);
        }
        else {
            emptyIv.setVisibility(View.GONE);
            emptyWishListTv.setVisibility(View.GONE);
            adapter = new WishListAdapter(wishList, context);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            wishListRv.setLayoutManager(manager);
            wishListRv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}