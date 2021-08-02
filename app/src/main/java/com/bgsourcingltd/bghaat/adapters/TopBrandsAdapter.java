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
import com.bgsourcingltd.bghaat.models.TopBrandsModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class TopBrandsAdapter extends RecyclerView.Adapter<TopBrandsAdapter.TopBrandsViewHolder> {

    private List<TopBrandsModel> topBrandsModelList;
    private Context context;

    public TopBrandsAdapter(List<TopBrandsModel> topBrandsModelList, Context context) {
        this.topBrandsModelList = topBrandsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public TopBrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_brands_layout,parent,false);
        return new TopBrandsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopBrandsViewHolder holder, int position) {
        Glide.with(context).load(topBrandsModelList.get(position).getImgUrl()).into(holder.topBrandsIv);
        holder.topBrandsTitle.setText(topBrandsModelList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return topBrandsModelList.size();
    }

    public class TopBrandsViewHolder extends RecyclerView.ViewHolder{
        ImageView topBrandsIv;
        TextView topBrandsTitle;

        public TopBrandsViewHolder(@NonNull View itemView) {
            super(itemView);

            topBrandsIv = itemView.findViewById(R.id.iv_top_brands);
            topBrandsTitle = itemView.findViewById(R.id.tv_top_brands);

        }
    }
}
