package com.app.garikini.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    Context context;
    List<Uri>uris;
    Listener listener;
    private static final String TAG = "ImagesAdapter";
    public ImagesAdapter(Context context, List<Uri> uris,Listener listener) {
        this.context = context;
        this.uris = uris;
        this.listener=listener;
    }

    public void addImg(Uri uri){
        Log.d(TAG, "addImg: Calling "+uri);
        uris.add(uri);
        notifyItemInserted(uris.size()-1);
    }

    public ImagesAdapter(Context context, List<Uri> uris) {
        this.context = context;
        this.uris = uris;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.images_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position<(uris.size())){
            holder.addPhoto.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageURI(uris.get(position));
        }else{
            holder.imageView.setVisibility(View.GONE);
            holder.addPhoto.setVisibility(View.VISIBLE);
            holder.addPhoto.setOnClickListener(view -> {
                listener.addPhotoListener(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return uris.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        LinearLayout addPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            addPhoto=itemView.findViewById(R.id.addPhoto);
        }
    }

    public interface Listener{
        void addPhotoListener(int position);
    }

}
