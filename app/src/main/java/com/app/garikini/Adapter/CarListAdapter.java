package com.app.garikini.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.Activity.AdsDetailsActivity;
import com.app.garikini.Activity.OtherAdsDetailsActivity;
import com.app.garikini.Model.Ad;
import com.app.garikini.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

/**
 * Gari Kini created by Sayem Hossen Saimon on 10/21/2021 at 1:14 PM.
 * Email: saimonchowdhuryi96@gmail.com.
 * Phone: +8801882046404.
 **/
public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {
    List<Ad>ads;
    Context context;


    public CarListAdapter(List<Ad> ads, Context context) {
        this.ads = ads;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carlist_item,parent,false));
    }

    public void filteredList(List<Ad> userArrayListList) {
        ads = userArrayListList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CarListAdapter.ViewHolder holder, int position) {
        Ad ad = ads.get(position);
        holder.priceTV.setText("৳ "+ad.getPrice());
        Glide.with(context).load(ad.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.carImageView);
        holder.titleTV.setText(ad.getTitle());
        if(ad.getCategory().equals("Top Urgent")) {
            holder.topUrgent.setVisibility(View.VISIBLE);
        }else{
            holder.topUrgent.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(view -> {
            if(ad.getCategory().equals("car")) {
                Log.d(TAG, "onBindViewHolder: " + new Gson().toJson(ad));
                String adS = new Gson().toJson(ad);
                Intent intent = new Intent(context, AdsDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", adS);
                context.startActivity(intent);
            }else{
                Log.d(TAG, "onBindViewHolder: " + new Gson().toJson(ad));
                String adS = new Gson().toJson(ad);
                Intent intent = new Intent(context, OtherAdsDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", adS);
                context.startActivity(intent);
            }
        });
    }

    private static final String TAG = "CarListAdapter";
    @Override
    public int getItemCount() {
        return ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView carImageView;
        TextView titleTV,priceTV;
        LinearLayout topUrgent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            carImageView = itemView.findViewById(R.id.carImageView);
            titleTV = itemView.findViewById(R.id.titleTV);
            topUrgent=itemView.findViewById(R.id.topUrgent);
            priceTV = itemView.findViewById(R.id.priceTV);

        }
    }
}
