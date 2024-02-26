package com.app.garikini.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.garikini.Adapter.SellOptionAdapter;
import com.app.garikini.Model.ChooseSellOptionModel;
import com.app.garikini.R;
import com.app.garikini.databinding.ActivityMakePostBinding;

import java.util.ArrayList;
import java.util.List;

public class MakePostActivity extends AppCompatActivity implements SellOptionAdapter.Listener {
    ActivityMakePostBinding binding;

    List<ChooseSellOptionModel> sellOptionModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sellOptionModelList = new ArrayList<>();
        sellOptionModelList.add(new ChooseSellOptionModel("Light Vehicles", R.drawable.ic_car));
        sellOptionModelList.add(new ChooseSellOptionModel("Heavy Vehicles", R.drawable.heavy_vehicle));
        sellOptionModelList.add(new ChooseSellOptionModel("Heavy & Equipment Vehicles", R.drawable.heavyeq));
        sellOptionModelList.add(new ChooseSellOptionModel("Motorbike", R.drawable.ic_motorbike));
        sellOptionModelList.add(new ChooseSellOptionModel("Bicycle", R.drawable.bicycle));
        sellOptionModelList.add(new ChooseSellOptionModel("Auto CNG", R.drawable.auto_cng));
        sellOptionModelList.add(new ChooseSellOptionModel("Three Wheeler", R.drawable.three_wheeler));
        sellOptionModelList.add(new ChooseSellOptionModel("Auto Service Center", R.drawable.auto_service));
        sellOptionModelList.add(new ChooseSellOptionModel("Auto Spare Shop", R.drawable.auto_spare_shop));
        sellOptionModelList.add(new ChooseSellOptionModel("Auto Spare Parts", R.drawable.auto_spare_parts));
        sellOptionModelList.add(new ChooseSellOptionModel("Garage", R.drawable.garage));
        sellOptionModelList.add(new ChooseSellOptionModel("Parking", R.drawable.parking));
        sellOptionModelList.add(new ChooseSellOptionModel("Rental Vehicle", R.drawable.ic_rental_car));
        sellOptionModelList.add(new ChooseSellOptionModel("Water Transport", R.drawable.water_vehicle));
        sellOptionModelList.add(new ChooseSellOptionModel("Agro Vehicle", R.drawable.agro));
        sellOptionModelList.add(new ChooseSellOptionModel("Want to Buy", R.drawable.buy));

        binding.sellOptions.setHasFixedSize(true);
        binding.sellOptions.setLayoutManager(new GridLayoutManager(this, 2));
        binding.sellOptions.setAdapter(new SellOptionAdapter(sellOptionModelList, this, this));
        binding.topUrgentPost.setOnClickListener(view -> {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", -1).putExtra("title", "Top Urgent"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            startActivity(new Intent(this, ProceedToPostActivity.class).putExtra("type", position).putExtra("title",sellOptionModelList.get(position).getName()));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 1) {
            startActivity(new Intent(this, ProceedToPostActivity.class).putExtra("type", position).putExtra("title",sellOptionModelList.get(position).getName()));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else if (position == 2) {
            startActivity(new Intent(this, ProceedToPostActivity.class).putExtra("type", position).putExtra("title",sellOptionModelList.get(position).getName()));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 3) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Motorbike"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 4) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Bicycle"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 5) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Auto CNG"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 6) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Three Wheeler"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 7) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Auto Service Center"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 8) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Auto Spare Shop"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 9) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Auto Spare Parts"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 10) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Garage"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 11) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Parking"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 12) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Rental Vehicles"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 13) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", sellOptionModelList.get(position).getName()));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 14) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", sellOptionModelList.get(position).getName()));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (position == 15) {
            startActivity(new Intent(this, PostOthersAdsActivity.class).putExtra("type", position).putExtra("title", "Want to Buy"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}