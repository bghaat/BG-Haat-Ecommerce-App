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

public class MainCatAdapter extends RecyclerView.Adapter<MainCatAdapter.MainCatViewHolder> {

    private Context context;
    private List<MainCategoryModel> mainCategoryModelList;

    public MainCatAdapter(Context context, List<MainCategoryModel> mainCategoryModelList) {
        this.context = context;
        this.mainCategoryModelList = mainCategoryModelList;
    }

    @NonNull
    @NotNull
    @Override
    public MainCatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_category_layout,parent,false);
        return new MainCatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainCatAdapter.MainCatViewHolder holder, int position) {
        holder.mainCatTv.setText(mainCategoryModelList.get(position).getCatName());
        Glide.with(context).load(mainCategoryModelList.get(position).getCatImage()).into(holder.mainCatIv);
    }

    @Override
    public int getItemCount() {
        return mainCategoryModelList.size();
    }

    public class MainCatViewHolder extends RecyclerView.ViewHolder{
        TextView mainCatTv;
        ImageView mainCatIv;


        public MainCatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mainCatTv = itemView.findViewById(R.id.tv_main_cat_raw);
            mainCatIv = itemView.findViewById(R.id.iv_main_cat_raw);

        }
    }
}
