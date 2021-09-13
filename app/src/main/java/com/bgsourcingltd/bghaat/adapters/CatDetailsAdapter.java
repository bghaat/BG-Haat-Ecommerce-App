package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class CatDetailsAdapter extends RecyclerView.Adapter<CatDetailsAdapter.CatDetailsViewHolder> {
    private Context context;
    private List<NewArrivalModel> catList;

    public CatDetailsAdapter(Context context, List<NewArrivalModel> catList) {
        this.context = context;
        this.catList = catList;
    }

    @NonNull
    @Override
    public CatDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_details_layout,parent,false);
        return new CatDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatDetailsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productTitle.setText(catList.get(position).getTitle());
        holder.productPrice.setText(catList.get(position).getPrice()+" ৳");

        Glide.with(context).load(catList.get(position).
                getImage()).
                into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArrivalModel object = catList.get(position);
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("object",object);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class CatDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView productIv;
        TextView productTitle,productPrice;

        public CatDetailsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_cat_details);
            productTitle = itemView.findViewById(R.id.tv_product_name_cat_details);
            productPrice = itemView.findViewById(R.id.tv_price_cat_details);
        }
    }
}
