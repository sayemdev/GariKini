package com.app.garikini.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.Model.ChooseSellOptionModel;
import com.app.garikini.R;

import java.util.List;

public class SellOptionAdapter extends RecyclerView.Adapter<SellOptionAdapter.ViewHolder> {

    List<ChooseSellOptionModel>sellOptionModels;
    Context context;
    Listener listener;

    public SellOptionAdapter(List<ChooseSellOptionModel> sellOptionModels, Context context, Listener listener) {
        this.sellOptionModels = sellOptionModels;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_option_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(sellOptionModels.get(position).getResourceId());
        holder.titleTV.setText(sellOptionModels.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return sellOptionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
    public interface Listener{
        void onClick(int position);
    }
}
