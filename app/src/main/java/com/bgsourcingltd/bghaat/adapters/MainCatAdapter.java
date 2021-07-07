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
        holder.mainCatIv.setImageResource(mainCategoryModelList.get(position).getCatImage());

    }

    @Override
    public int getItemCount() {
        return mainCategoryModelList.size();
    }

    public class MainCatViewHolder extends RecyclerView.ViewHolder{
        ImageView mainCatIv;
        TextView mainCatTv;

        public MainCatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mainCatIv = itemView.findViewById(R.id.iv_main_cat_raw);
            mainCatTv = itemView.findViewById(R.id.tv_main_cat_raw);
        }
    }
}
