package com.app.garikini.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.Model.CarBrand;
import com.app.garikini.R;

import java.util.List;

public class CarBrandAdapter extends RecyclerView.Adapter<CarBrandAdapter.ViewHolder> {

    List<CarBrand>carBrands;
    Context context;
    Listener listener;

    public CarBrandAdapter(List<CarBrand> carBrands, Context context, Listener listener) {
        this.carBrands = carBrands;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.logo.setImageResource(carBrands.get(position).getImageUrl());
        holder.itemView.setOnClickListener(view -> {
            listener.OnBrandClick(carBrands.get(position).getName());
        });
    }

    @Override
    public int getItemCount() {
        return carBrands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logoImageView);
        }
    }
    public interface Listener{
        void OnBrandClick(String brand);
    }
}
