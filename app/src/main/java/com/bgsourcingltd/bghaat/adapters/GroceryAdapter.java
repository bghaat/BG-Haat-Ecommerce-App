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

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.TopBrandsViewHolder> {

    private List<NewArrivalModel> groceryList;
    private Context context;

    public GroceryAdapter(List<NewArrivalModel> groceryList, Context context) {
        this.groceryList = groceryList;
        this.context = context;
    }

    @NonNull
    @Override
    public TopBrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_brands_layout,parent,false);
        return new TopBrandsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopBrandsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.productTitleTv.setText(groceryList.get(position).getTitle());
        holder.productPriceTv.setText(groceryList.get(position).getPrice()+ "৳");
        holder.productStrikeTv.setText(groceryList.get(position).getRegularPrice());

        Glide.with(context).
                load(groceryList.get(position).getImage()).
                placeholder(R.drawable.progress_dialog).
                into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArrivalModel object = groceryList.get(position);
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("object",object);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (groceryList.size() > 20 ? 20 : groceryList.size());
    }

    public class TopBrandsViewHolder extends RecyclerView.ViewHolder{
        ImageView productIv;
        TextView productTitleTv;
        TextView productPriceTv,productStrikeTv;

        public TopBrandsViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_grocery);
            productTitleTv = itemView.findViewById(R.id.tv_product_name_grocery);
            productPriceTv = itemView.findViewById(R.id.tv_price_grocery);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_grocery);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}
