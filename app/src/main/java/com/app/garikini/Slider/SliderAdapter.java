package com.app.garikini.Slider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.garikini.Activity.ViewZoomImageActivity;
import com.app.garikini.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Rashtro created by Sayem Hossen Saimon on 3/7/2021 at 4:02 PM.
 * Email: saimonchowdhuryi96@gmail.com.
 * Phone: +8801882046404.
 **/
public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private static final String TAG = "SliderAdapter";
    private final Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.packagePriceTV.setText(sliderItem.getPrice());
        viewHolder.companyNameTV.setText(sliderItem.getCompanyName());
        Glide.with(viewHolder.itemView).load(sliderItem.getImageUrl()).fitCenter().into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + sliderItem.getImageUrl());
                context.startActivity(new Intent(context, ViewZoomImageActivity.class).putExtra("link", sliderItem.getImageUrl()));
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }


    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription, companyNameTV, packagePriceTV;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider_norm);
            companyNameTV = itemView.findViewById(R.id.companyName);
            packagePriceTV = itemView.findViewById(R.id.packagePrice);
            this.itemView = itemView;
        }
    }

}