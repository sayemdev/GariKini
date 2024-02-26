package com.app.garikini.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.Model.BDesc;
import com.app.garikini.R;

import java.util.List;

public class BDescAdapter extends RecyclerView.Adapter<BDescAdapter.ViewHolder> {

    List<BDesc>bDescs;
    Context context;

    public BDescAdapter(List<BDesc> bDescs, Context context) {
        this.bDescs = bDescs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bdesc_lt,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dataTV.setText(bDescs.get(position).getData()+":");
        holder.valueTV.setText(bDescs.get(position).getValue());

    }

    @Override
    public int getItemCount() {
        return bDescs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dataTV,valueTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataTV = itemView.findViewById(R.id.dataTV);
            valueTV=itemView.findViewById(R.id.valueTV);

        }
    }
}
