package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.ShowDetailsActivity;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CatDetailsAdapter extends RecyclerView.Adapter<CatDetailsAdapter.CatDetailsViewHolder> implements Filterable {
    private Context context;
    private List<NewArrivalModel> catList;
    private List<NewArrivalModel> listFull;

    public CatDetailsAdapter(Context context, List<NewArrivalModel> catList) {
        this.context = context;
        this.catList = catList;
        this.listFull = new ArrayList<>(catList);
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
        holder.productStrikeTv.setText(catList.get(position).getRegularPrice());

        Glide.with(context).load(catList.get(position).getImage()).
                placeholder(R.drawable.progress_dialog).
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

    @Override
    public Filter getFilter() {
        return filterUser;
    }

    private Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            List<NewArrivalModel> tempList = new ArrayList<>();
            if (searchText.length()==0 || searchText.isEmpty()){
                tempList.addAll(listFull);
            }
            else {
                for (NewArrivalModel items : listFull){
                    //if you want to query with multiple condition put logic here
                    if (items.getTitle().toLowerCase().contains(searchText)){
                        tempList.add(items);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            catList.clear();
            catList.addAll((Collection<? extends NewArrivalModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public class CatDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView productIv;
        TextView productTitle,productPrice,productStrikeTv;

        public CatDetailsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.iv_product_cat_details);
            productTitle = itemView.findViewById(R.id.tv_product_name_cat_details);
            productPrice = itemView.findViewById(R.id.tv_price_cat_details);
            productStrikeTv = itemView.findViewById(R.id.tv_strike_cat_details);

            productStrikeTv.setPaintFlags(productStrikeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
