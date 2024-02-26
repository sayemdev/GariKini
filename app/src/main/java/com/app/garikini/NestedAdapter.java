package com.app.garikini;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    String type;
    ClickListener clickListener;
    private final List<String> mList;

    public NestedAdapter(List<String> mList, ClickListener clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
    }

    public NestedAdapter(List<String> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent, false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position));
        holder.itemView.setOnClickListener(view -> {
            clickListener.OnClick(mList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListener {
        void OnClick(String s);
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTv;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.nestedItemTv);
        }
    }

}
