package com.bgsourcingltd.bghaat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.MainCategoryModel;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {
    private Context context;
    private List<MainCategoryModel> list;

    public AllCategoryAdapter(Context context, List<MainCategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_all_layout,parent,false);
        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllCategoryAdapter.AllCategoryViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getCatImage()).into(holder.allCatIv);
        holder.allCatTv.setText(list.get(position).getCatName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllCategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView allCatIv;
        TextView allCatTv;

        public AllCategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            allCatIv = itemView.findViewById(R.id.all_cat_iv);
            allCatTv = itemView.findViewById(R.id.all_cat_tv);
        }
    }
}
