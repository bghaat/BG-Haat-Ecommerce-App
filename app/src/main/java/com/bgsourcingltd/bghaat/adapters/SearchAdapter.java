package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<NewArrivalModel> list;
    private Context context;

    public SearchAdapter(List<NewArrivalModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_item_layout,parent,false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.productTitleTv.setText(list.get(position).getTitle());
        holder.productPriceTv.setText(list.get(position).getPrice()+ "৳");
        holder.productStrikeTv.setText(list.get(position).getRegularPrice());

        Glide.with(context).
                load(list.get(position).getImage()).
                into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArrivalModel object = list.get(position);
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("object",object);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        ImageView productIv;
        TextView productTitleTv;
        TextView productPriceTv,productStrikeTv;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_search);
            productTitleTv = itemView.findViewById(R.id.tv_product_name_search);
            productPriceTv = itemView.findViewById(R.id.tv_price_search);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_search);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
