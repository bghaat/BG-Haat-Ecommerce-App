package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.Interface.ImageVariationsInterface;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductVariationAdapter extends RecyclerView.Adapter<ProductVariationAdapter.VariationsViewHolder> {

    private List<String> variationsImageList;
    private Context context;
    private ImageVariationsInterface imageVariationsInterface;

    private static int lastClickedPosition = 0;
    private int selectedItem;


    public ProductVariationAdapter(List<String> variationsImageList, Context context,ImageVariationsInterface imageVariationsInterface) {
        this.variationsImageList = variationsImageList;
        this.context = context;
        this.imageVariationsInterface = imageVariationsInterface;
        selectedItem = -1;
    }

    @NonNull
    @Override
    public VariationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_variations_layout,parent,false);
        return new VariationsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VariationsViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(context).load(variationsImageList.get(position)).into(holder.productVariationImage);


        holder.productVariationsCv.setCardBackgroundColor(context.getResources().getColor(R.color.white, context.getTheme()));

        if (selectedItem == position){
            holder.productVariationsCv.setCardBackgroundColor(context.getResources().getColor(R.color.colorDivider, context.getTheme()));
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int previousItem = selectedItem;
                    selectedItem = position;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(position);


                    String imageUrl = variationsImageList.get(position);
                    imageVariationsInterface.onItemClick(imageUrl);

                }
            });


    }

    @Override
    public int getItemCount() {
        return variationsImageList.size();
    }

    public static class VariationsViewHolder extends RecyclerView.ViewHolder{
        ImageView productVariationImage;
        CardView productVariationsCv;

        public VariationsViewHolder(@NonNull View itemView) {
            super(itemView);

            productVariationImage = itemView.findViewById(R.id.iv_product_variation);
            productVariationsCv = itemView.findViewById(R.id.card_view_product_variation);

        }
    }
}
