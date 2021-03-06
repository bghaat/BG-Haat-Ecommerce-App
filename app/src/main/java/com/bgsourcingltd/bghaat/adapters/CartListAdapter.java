package com.bgsourcingltd.bghaat.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.Interface.ChangeNumberItemsListener;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<NewArrivalModel> newArrivalModelArrayList;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<NewArrivalModel> newArrivalModelArrayList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.newArrivalModelArrayList = newArrivalModelArrayList;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(newArrivalModelArrayList.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(newArrivalModelArrayList.get(position).getPrice()));
        holder.totalEachItem.setText(String.valueOf(Math.round((newArrivalModelArrayList.get(position).getNumberInCart() * Double.parseDouble(newArrivalModelArrayList.get(position).getPrice())) * 100.0) / 100.0));
        holder.num.setText(String.valueOf(newArrivalModelArrayList.get(position).getNumberInCart()));

        Glide.with(holder.itemView.getContext())
                .load(newArrivalModelArrayList
                        .get(position).getImage())
                .into(holder.pic);


        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberFood(newArrivalModelArrayList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.MinusNumberFood(newArrivalModelArrayList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(R.drawable.ic_baseline_delete_forever_24)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                managementCart.DeleteItem(newArrivalModelArrayList, position, new ChangeNumberItemsListener() {
                                    @Override
                                    public void changed() {
                                        notifyDataSetChanged();
                                        changeNumberItemsListener.changed();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return newArrivalModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem,deleteIv;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title2Txt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);
            deleteIv = itemView.findViewById(R.id.iv_delete);
        }
    }
}
