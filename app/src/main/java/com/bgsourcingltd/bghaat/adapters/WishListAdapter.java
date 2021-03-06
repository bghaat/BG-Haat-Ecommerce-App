package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.ShowDetailsActivity;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.wishlistpreference.WishListPref;
import com.bumptech.glide.Glide;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private List<NewArrivalModel> modelList;
    private Context context;
    private WishListPref wishListPref;



    public WishListAdapter(List<NewArrivalModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        wishListPref = new WishListPref();


    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_layout,parent,false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productTitle.setText(modelList.get(position).getTitle());
        holder.productPrice.setText(modelList.get(position).getPrice()+ "TK");
        //holder.productStrikeTv.setText(modelList.get(position).getStrikePrice());

        Glide.with(context).
                load(modelList.get(position).getImage()).
                into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArrivalModel object = modelList.get(position);
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("object",object);
                context.startActivity(intent);

            }
        });

        /*holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                NewArrivalModel object = modelList.get(position);
                *//*wishListPref.removeFavorite(context,object);
                notifyDataSetChanged();*//*

                modelList.remove(object);
                notifyDataSetChanged();
                wishListPref.saveFavorites(context,modelList);


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle,productPrice;
        ImageView productIv,deleteIv;



        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.picCard);
            deleteIv = itemView.findViewById(R.id.iv_delete_wish_list);
            productTitle = itemView.findViewById(R.id.tv_wishList_product_title);
            productPrice = itemView.findViewById(R.id.tv_price_wish_list);

        }
    }
}
