package com.app.garikini.Activity;

import static com.app.garikini.Activity.LoginActivity.USER_DATA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.Fragments.AccountFragment;
import com.app.garikini.Fragments.HomeFragmentFragment;
import com.app.garikini.Fragments.SearchFragment;
import com.app.garikini.R;
import com.app.garikini.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, HomeFragmentFragment.ClickListener, AccountFragment.ClickListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1234;
    BottomNavigationView bottomNavigationView;
    MenuItem menuItem;

    ActivityMainBinding binding;
    HomeFragmentFragment homeFragmentFragment;
    AccountFragment acntFr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // waiting for view to draw to better represent a captured error with a screenshot
        findViewById(android.R.id.content).getViewTreeObserver().addOnGlobalLayoutListener(() -> {

        });
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragmentFragment = new HomeFragmentFragment(this);
        acntFr = new AccountFragment(this);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homeMenuId);
        binding.postButton.setOnClickListener(view -> {
            bottomNavigationView.setSelectedItemId(R.id.postMenuId);
        });
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "onBackPressed: " + count);
        if (bottomNavigationView.getSelectedItemId() == R.id.homeMenuId) {
            super.onBackPressed();
            //additional code
        } else {
            bottomNavigationView.setSelectedItemId(R.id.homeMenuId);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        menuItem = item;
        if (item.getItemId() == R.id.homeMenuId) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragmentContainer, homeFragmentFragment).commit();
        }
        if (item.getItemId() == R.id.searchMenuId) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragmentContainer, new SearchFragment()).commit();
        }
        if (item.getItemId() == R.id.chatsMenuId) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragmentContainer, new HomeFragmentFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.homeMenuId);
        }
        if (item.getItemId() == R.id.profileMenuId) {
            if (!getSharedPreferences(USER_DATA, MODE_PRIVATE).getBoolean("log", false)) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFragmentContainer, acntFr).commit();
            }
        }
        if (item.getItemId() == R.id.postMenuId) {
            if (!getSharedPreferences(USER_DATA, MODE_PRIVATE).getBoolean("log", false)) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, MakePostActivity.class));
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    String category = data.getStringExtra("category");
                    filterByCategory(category);
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void filterByCategory(String category) {
        Log.d(TAG, "filterByCategory: " + category);

        homeFragmentFragment.searchByCat(category);
    }

    @Override
    public void CategoryClick() {
        Intent intent = new Intent(this, PickACategoryActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClick() {
        recreate();
    }
}