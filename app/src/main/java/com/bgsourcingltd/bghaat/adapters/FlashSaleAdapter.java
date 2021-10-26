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

public class FlashSaleAdapter extends RecyclerView.Adapter<FlashSaleAdapter.FlashSaleViewHolder> {

    private List<NewArrivalModel> flashList;
    private Context context;

    public FlashSaleAdapter(List<NewArrivalModel> flashList, Context context) {
        this.flashList = flashList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlashSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flash_sale_item,parent,false);
        return new FlashSaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashSaleViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.productTitleTv.setText(flashList.get(position).getTitle());
        holder.productPriceTv.setText(flashList.get(position).getPrice()+ "৳");
        holder.productStrikeTv.setText(flashList.get(position).getRegularPrice());

        Glide.with(context).
                load(flashList.get(position).getImage()).
                into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArrivalModel object = flashList.get(position);
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("object",object);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return flashList.size();
    }

    public class FlashSaleViewHolder extends RecyclerView.ViewHolder{

        ImageView productIv;
        TextView productTitleTv;
        TextView productPriceTv,productStrikeTv;

        public FlashSaleViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_flash_sale);
            productTitleTv = itemView.findViewById(R.id.tv_product_name_flash_sale);
            productPriceTv = itemView.findViewById(R.id.tv_price_flash_sale);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_flash_sale);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
