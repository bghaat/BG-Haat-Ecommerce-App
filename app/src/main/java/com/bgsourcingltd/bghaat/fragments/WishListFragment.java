package com.bgsourcingltd.bghaat.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.Interface.DeleteWishListListener;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.adapters.WishListAdapter;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.wishlistpreference.WishListPref;

import java.util.ArrayList;
import java.util.List;


public class WishListFragment extends Fragment implements DeleteWishListListener {


    private Context context;
    private RecyclerView wishListRv;
    WishListAdapter adapter;
    WishListPref wishListPref;
    List<NewArrivalModel> list;


    public WishListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        wishListRv = view.findViewById(R.id.rv_wish_list);
        wishListPref = new WishListPref();

        list = wishListPref.getFavorites(context);
        if (list.isEmpty()){
            Toast.makeText(context, "empty wish list", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter = new WishListAdapter(list, context,this);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            wishListRv.setLayoutManager(manager);
            wishListRv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void deleteItem(List<NewArrivalModel> list, int position,NewArrivalModel model) {
        WishListPref wishListPref = new WishListPref();
        wishListPref.removeFavorite(context,model);
    }
}