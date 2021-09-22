package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.ShowDetailsActivity;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.AllProductViewHolder> {

    private List<NewArrivalModel> list;
    private Context context;


    public AllProductAdapter(List<NewArrivalModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public AllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_product_layout,parent,false);
        return new AllProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.productTitle.setText(list.get(position).getTitle());
        holder.productPrice.setText(list.get(position).getPrice()+" ৳");

        Glide.with(context).load(list.get(position).
                getImage()).
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

    /*public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString().toLowerCase();
                if (key.isEmpty()){
                    filterDataList = list;
                }
                else {
                    List<NewArrivalModel> lstFiltered = new ArrayList<>();
                    for (NewArrivalModel row : list){
                        if (row.getTitle().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    filterDataList = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filterDataList = (List<NewArrivalModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }*/

    public class AllProductViewHolder extends RecyclerView.ViewHolder{
        ImageView productIv;
        TextView productTitle,productPrice,productStrikeTv;

        public AllProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_search_details);
            productTitle = itemView.findViewById(R.id.tv_product_name_search_details);
            productPrice = itemView.findViewById(R.id.tv_price_search_details);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_search_details);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }


}
