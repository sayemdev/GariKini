package com.app.garikini.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.Http.RetrofitClient;
import com.app.garikini.Model.LoginResponse;
import com.app.garikini.databinding.FragmentLoginBinding;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_DATA = "USER";
    FragmentLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(view -> {

            if (!binding.phoneET.getText().toString().trim().isEmpty() && !binding.passwordET.getText().toString().trim().isEmpty()) {
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), binding.phoneET.getText().toString().trim());
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), binding.passwordET.getText().toString().trim());

                Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().Login(phone, password);
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle("Please wait...");
                dialog.setMessage("Wait while login successful");
                dialog.show();
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus()) {
                                Toast.makeText(LoginActivity.this, "Welcome back, " + response.body().getResult().getName(), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = getSharedPreferences(USER_DATA, MODE_PRIVATE).edit();
                                editor.putString("data", new Gson().toJson(response.body().getResult()));
                                editor.putBoolean("log",true);
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Something went wrong. Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                Toast.makeText(this, "Enter phone and password", Toast.LENGTH_SHORT).show();
            }

        });
        binding.createNewButton.setOnClickListener(view -> {
            startActivity(new Intent(this, RegistrationActivity.class));
            finish();
        });

    }
}