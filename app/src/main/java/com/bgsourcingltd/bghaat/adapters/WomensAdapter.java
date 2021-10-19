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

public class WomensAdapter extends RecyclerView.Adapter<WomensAdapter.WomensViewHoloder> {
    private Context context;
    private List<NewArrivalModel> newArrivalModelList;


    public WomensAdapter(Context context,List<NewArrivalModel> newArrivalModelList) {
        this.context = context;
        this.newArrivalModelList = newArrivalModelList;
    }

    @NonNull
    @Override
    public WomensViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.womens_cat_layout,parent,false);
        return new WomensViewHoloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WomensViewHoloder holder, @SuppressLint("RecyclerView") int position) {

        holder.productTitleTv.setText(newArrivalModelList.get(position).getTitle());
        holder.productPriceTv.setText(newArrivalModelList.get(position).getPrice()+ "৳");
        holder.productStrikeTv.setText(newArrivalModelList.get(position).getRegularPrice());

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
        return (newArrivalModelList.size() > 20 ? 20 : newArrivalModelList.size());
    }

    public class WomensViewHoloder extends RecyclerView.ViewHolder{
        ImageView productIv;
        TextView productTitleTv;
        TextView productPriceTv,productStrikeTv;

        public WomensViewHoloder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_womens_new_arrival);
            productTitleTv = itemView.findViewById(R.id.tv_product_name_womens);
            productPriceTv = itemView.findViewById(R.id.tv_price_womens);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_womens);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }


}
