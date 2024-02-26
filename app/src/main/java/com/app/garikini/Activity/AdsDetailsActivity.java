package com.app.garikini.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.garikini.Adapter.BDescAdapter;
import com.app.garikini.Model.Ad;
import com.app.garikini.Model.BDesc;
import com.app.garikini.R;
import com.app.garikini.Slider.SliderAdapter;
import com.app.garikini.Slider.SliderItem;
import com.app.garikini.databinding.ActivityAdsDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class AdsDetailsActivity extends AppCompatActivity {

    ActivityAdsDetailsBinding binding;
    private static final String TAG = "AdsDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Gson gson = new Gson();

        Ad ad = gson.fromJson((getIntent().getStringExtra("data")), new TypeToken<Ad>() {
        }.getType());

        binding.descriptionTV.setText(ad.getDescription());
        binding.priceTV.setText("Price: ৳" + ad.getPrice());
        binding.titleTV.setText(ad.getTitle());
        binding.timeTV.setText("Posted At: " + ad.getPostedAt());
        binding.seller.setText("Seller: " + ad.getPostedBy());
        Glide.with(this).load(ad.getImages()).into(binding.productImageView);

        List<BDesc> descList = new ArrayList<>();
        descList.add(new BDesc("Brand", ad.getBrand()));
        descList.add(new BDesc("Model", ad.getModel()));
        descList.add(new BDesc("Edition", ad.getEdition()));
        descList.add(new BDesc("Production Year", ad.getProductionYear()));
        descList.add(new BDesc("Registration Year", ad.getRegistrationYear()));
        descList.add(new BDesc("Condition", ad.getCondition()));
        descList.add(new BDesc("Transmission", ad.getTransmission()));
        descList.add(new BDesc("Body Type", ad.getBodyType()));
        descList.add(new BDesc("Fuel Type", ad.getFuelType()));
        descList.add(new BDesc("Engine Power", ad.getEnginePower()));
        descList.add(new BDesc("Kilometer Run", ad.getKilometreRun()));
        descList.add(new BDesc("Class", ad.getClass_()));

        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        String[] dataArray = ad.getImages().split(",");

        // Output the individual elements
        for (String element : dataArray) {
            sliderAdapter.addItem(new SliderItem(element));
        }

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setSliderAdapter(sliderAdapter);


        binding.bDescRCV.setHasFixedSize(true);
        binding.bDescRCV.setLayoutManager(new LinearLayoutManager(this));
        binding.bDescRCV.setAdapter(new BDescAdapter(descList, this));


        binding.callOwner.setOnClickListener(view -> {
            String phone = ad.getPhone();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        });

    }
}