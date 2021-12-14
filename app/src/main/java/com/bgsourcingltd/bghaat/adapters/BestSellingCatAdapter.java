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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BestSellingCatAdapter extends RecyclerView.Adapter<BestSellingCatAdapter.BestSellingViewHolder> {

    private List<NewArrivalModel> modelList;
    private Context context;

    public BestSellingCatAdapter(List<NewArrivalModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public BestSellingViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.best_selling_layout,parent,false);
        return new BestSellingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BestSellingCatAdapter.BestSellingViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.productTitleTv.setText(modelList.get(position).getTitle());
        holder.productPriceTv.setText(modelList.get(position).getPrice()+ "৳");
        holder.productStrikeTv.setText(modelList.get(position).getRegularPrice());

        Glide.with(context).
                load(modelList.get(position).getImage()).
                placeholder(R.drawable.progress_dialog).
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
    }

    @Override
    public int getItemCount() {
        return (modelList.size() > 20 ? 20 : modelList.size());
    }

    public class BestSellingViewHolder extends RecyclerView.ViewHolder {
        ImageView productIv;
        TextView productTitleTv;
        TextView productPriceTv,productStrikeTv;

        public BestSellingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_best_selling);
            productTitleTv = itemView.findViewById(R.id.tv_product_name_best_selling);
            productPriceTv = itemView.findViewById(R.id.tv_price_best_selling);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_best_selling);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
