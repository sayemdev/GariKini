package com.app.garikini.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.R;

public class CarModelAdapter extends RecyclerView.Adapter<CarModelAdapter.ViewHolder> {

    String[] models;
    Context context;
    CarBrandAdapter.Listener listener;

    public CarModelAdapter(String[] models, Context context, CarBrandAdapter.Listener listener) {
        this.models = models;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.carModel.setText(models[position]);
        holder.itemView.setOnClickListener(view -> {
            listener.OnBrandClick(models[position]);
        });
    }

    @Override
    public int getItemCount() {
        return models.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView carModel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carModel = itemView.findViewById(R.id.model);
        }
    }
}
