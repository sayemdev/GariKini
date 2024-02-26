package com.app.garikini.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.Fragments.LoginFragment;
import com.app.garikini.R;
import com.app.garikini.databinding.ActivityLoginOrRegistrationBinding;

public class LoginOrRegistrationActivity extends AppCompatActivity {

    ActivityLoginOrRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_or_registration);
        binding = ActivityLoginOrRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentContainer, new LoginFragment()).commit();

    }
}