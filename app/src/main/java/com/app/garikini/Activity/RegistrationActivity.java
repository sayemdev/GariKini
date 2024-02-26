package com.app.garikini.Activity;

import static com.app.garikini.Activity.LoginActivity.USER_DATA;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.Http.RetrofitClient;
import com.app.garikini.Model.LoginResponse;
import com.app.garikini.databinding.ActivityRegistrationBinding;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    String name, phone, email, password, rePassword, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.registerButton.setOnClickListener(view -> {
            name = binding.nameET.getText().toString();
            phone = binding.phoneET.getText().toString().trim();
            email = binding.emailET.getText().toString().trim();
            password = binding.passwordET.getText().toString().trim();
            rePassword = binding.rePasswordET.getText().toString().trim();
            location = binding.locationEt.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            } else if (phone.isEmpty()) {
                Toast.makeText(this, "Please enter your phone", Toast.LENGTH_SHORT).show();
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(this, "Password must contains 6 character", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(rePassword)) {
                Toast.makeText(this, "Password doesn't matched", Toast.LENGTH_SHORT).show();
            } else {


                RequestBody names = RequestBody.create(MediaType.parse("text/plain"), name);
                RequestBody phones = RequestBody.create(MediaType.parse("text/plain"), phone);
                RequestBody emails = RequestBody.create(MediaType.parse("text/plain"), email);
                RequestBody passwords = RequestBody.create(MediaType.parse("text/plain"), password);
                RequestBody locations = RequestBody.create(MediaType.parse("text/plain"), location);

                Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().Registration(names, phones, emails, passwords, locations);
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle("Please wait...");
                dialog.setMessage("Wait while registration successful");
                dialog.show();
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus()) {
                                Toast.makeText(RegistrationActivity.this, "Welcome, " + response.body().getResult().getName(), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = getSharedPreferences(USER_DATA, MODE_PRIVATE).edit();
                                editor.putString("data", new Gson().toJson(response.body().getResult()));
                                editor.putBoolean("log", true);
                                editor.apply();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegistrationActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Something went wrong. Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.loginButton.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

    private static final String TAG = "RegistrationActivity";

}