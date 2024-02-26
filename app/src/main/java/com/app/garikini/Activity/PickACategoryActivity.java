package com.app.garikini.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.garikini.NestedAdapter;
import com.app.garikini.R;
import com.app.garikini.databinding.ActivityPickAcategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class PickACategoryActivity extends AppCompatActivity implements NestedAdapter.ClickListener {

    public static final int REQUEST_CODE = 123;
    ActivityPickAcategoryBinding binding;
    List<String> typesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPickAcategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        typesList = new ArrayList<>();

        typesList.add("All Ads");
        typesList.add("Top Urgent");
        typesList.add("Light Vehicle");
        typesList.add("Heavy Vehicle");
        typesList.add("Heavy & Equipment Vehicle");
        typesList.add("Motorbike");
        typesList.add("Biycle");
        typesList.add("Auto CNG");
        typesList.add("Three Wheeler");
        typesList.add("Auto Service Center");
        typesList.add("Auto Spare Shop");
        typesList.add("Auto Spare Parts");
        typesList.add("Garage");
        typesList.add("Parking");
        typesList.add("Rental Vehicle");
        typesList.add("Water Transport");
        typesList.add("Agro Vehicle");
        typesList.add("Want to Buy");

        binding.categoryList.setHasFixedSize(true);
        binding.categoryList.setLayoutManager(new LinearLayoutManager(this));
        binding.categoryList.setAdapter(new NestedAdapter(typesList, this));

    }


    @Override
    public void OnClick(String s) {
        Intent intent = getIntent();
        intent.putExtra("category", s);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}