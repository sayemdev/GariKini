package com.app.garikini.Activity;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class ViewZoomImageActivity extends AppCompatActivity {

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_zoom_document);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
        });

        PhotoView photoView = findViewById(R.id.photo_view);
        Glide.with(this).load(getIntent().getStringExtra("link")).placeholder(R.drawable.round_hide_image_24).error(R.drawable.round_hide_image_24).into(photoView);

    }


}