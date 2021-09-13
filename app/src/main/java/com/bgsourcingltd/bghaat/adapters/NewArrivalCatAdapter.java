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

public class NewArrivalCatAdapter extends RecyclerView.Adapter<NewArrivalCatAdapter.NewArrivalViewHolder> {
    private Context context;
    private List<NewArrivalModel> newArrivalModelList;

    public NewArrivalCatAdapter(Context context, List<NewArrivalModel> newArrivalModelList) {
        this.context = context;
        this.newArrivalModelList = newArrivalModelList;
    }

    @NonNull
    @NotNull
    @Override
    public NewArrivalViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_arrival_layout,parent,false);
        return new NewArrivalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewArrivalCatAdapter.NewArrivalViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.productTitleTv.setText(newArrivalModelList.get(position).getTitle());
        holder.productPriceTv.setText(newArrivalModelList.get(position).getPrice()+" ৳");

        //holder.productStrikeTv.setText(newArrivalModelList.get(position).getStrikePrice());

        Glide.with(context).
                load(newArrivalModelList.get(position).getImage()).
                into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArrivalModel object = newArrivalModelList.get(position);
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("object",object);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return newArrivalModelList.size();
    }

    public class NewArrivalViewHolder extends RecyclerView.ViewHolder{
        ImageView productIv;
        TextView productTitleTv;
        TextView productPriceTv,productStrikeTv;

        public NewArrivalViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_new_arrival);
            productTitleTv = itemView.findViewById(R.id.tv_product_name_new_arrival);
            productPriceTv = itemView.findViewById(R.id.tv_price_new_arrival);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_new_arrival);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }
    }
}
