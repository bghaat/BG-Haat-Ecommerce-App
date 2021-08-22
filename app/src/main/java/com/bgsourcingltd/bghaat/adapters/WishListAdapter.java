package com.bgsourcingltd.bghaat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private List<NewArrivalModel> modelList;
    private Context context;

    public WishListAdapter(List<NewArrivalModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_layout,parent,false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        holder.productTitle.setText(modelList.get(position).getFoodTitle());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle;

        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.productTtle);
        }
    }
}
