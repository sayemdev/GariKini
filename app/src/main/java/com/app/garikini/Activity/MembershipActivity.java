package com.app.garikini.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.R;
import com.app.garikini.databinding.ActivityMembershipBinding;

public class MembershipActivity extends AppCompatActivity {
    ActivityMembershipBinding binding;
    private static final String TAG = "MembershipActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMembershipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Select a membership");


        if(getIntent().getStringExtra("type")!=null){
            binding.allLt.setVisibility(View.GONE);
            binding.topUrgentOnly.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void TryFreePost(View view) {
        Intent intent = getIntent();
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("description", getIntent().getStringExtra("description"));
        intent.putExtra("price", getIntent().getStringExtra("price"));
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}