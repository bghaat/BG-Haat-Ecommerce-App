package com.bgsourcingltd.bghaat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.models.SliderModel;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    private List<SliderModel> sliderModelList;
    private Context context;

    public SliderAdapter(List<SliderModel> sliderModelList,Context context) {
        this.sliderModelList = sliderModelList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        Glide.with(context).
                load(sliderModelList.get(position).getImage()).
                into(viewHolder.imageView);

    }

    @Override
    public int getCount() {
        return sliderModelList.size();
    }

    public class Holder extends  SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }

}