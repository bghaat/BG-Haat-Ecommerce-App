package com.bgsourcingltd.bghaat.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.helper.Constraint;
import com.bgsourcingltd.bghaat.models.CouponCodeModel;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponCodeViewHolder> {

    private Context context;
    private List<CouponCodeModel> codeModelList;


    public CouponAdapter(Context context, List<CouponCodeModel> codeModelList) {
        this.context = context;
        this.codeModelList = codeModelList;
    }

    @NonNull
    @Override
    public CouponCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.coupon_item_layout,parent,false);
        return new CouponCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponCodeViewHolder holder, int position) {
        holder.couponTitle.setText(codeModelList.get(position).getPromoTitle());
        holder.couponCode.setText(codeModelList.get(position).getCouponCode());
        holder.couponAmount.setText(codeModelList.get(position).getAmount());
        holder.expiredDate.setText(codeModelList.get(position).getExpiredDate());

        Constraint.couponCode = codeModelList.get(position).getCouponCode();
        Constraint.couponDate = codeModelList.get(position).getExpiredDate();
        Constraint.couponAmount = codeModelList.get(position).getAmount();

    }

    @Override
    public int getItemCount() {
        return codeModelList.size();
    }


    public class CouponCodeViewHolder extends RecyclerView.ViewHolder{

        TextView couponTitle,couponCode,couponAmount,expiredDate;

        public CouponCodeViewHolder(@NonNull View itemView) {
            super(itemView);

            couponTitle = itemView.findViewById(R.id.tv_coupon_title);
            couponCode = itemView.findViewById(R.id.tv_coupon_code_value);
            couponAmount = itemView.findViewById(R.id.tv_amount_value);
            expiredDate = itemView.findViewById(R.id.tv_date_value);

        }
    }

}
