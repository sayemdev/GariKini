package com.app.garikini.Fragments;

import static com.app.garikini.Activity.LoginActivity.USER_DATA;
import static com.app.garikini.Constants.getUser;
import static com.app.garikini.Constants.isLoggedIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.garikini.Activity.LoginActivity;
import com.app.garikini.R;
import com.app.garikini.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    View view;
    ClickListener clickListener;

    public AccountFragment(ClickListener clickListener) {
        // Required empty public constructor
        this.clickListener=clickListener;
    }

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        binding.continueWithEmail.setOnClickListener(view -> {
            startActivity(new Intent(binding.getRoot().getContext(), LoginActivity.class));
            ((Activity) binding.getRoot().getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.continueWithFacebook.setOnClickListener(view -> {
//            startActivity(new Intent(binding.getRoot().getContext(), MakePostActivity.class));
//            ((Activity)binding.getRoot().getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.continueWithGoogle.setOnClickListener(view -> {
//            startActivity(new Intent(binding.getRoot().getContext(), MakePostActivity.class));
//            ((Activity)binding.getRoot().getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.logout.setOnClickListener(view1 -> {
            binding.getRoot().getContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit().clear().apply();
            clickListener.onClick();
        });

        if (isLoggedIn(binding.getRoot().getContext())) {
            binding.notLoggedInView.setVisibility(View.GONE);
            binding.nameTV.setVisibility(View.VISIBLE);
            binding.nameTV.setText(getUser(binding.getRoot().getContext()).getName()+"");
        } else {
            binding.notLoggedInView.setVisibility(View.VISIBLE);
            binding.nameTV.setVisibility(View.GONE);
        }

        return binding.getRoot();

    }
    public  interface  ClickListener{
        void onClick();
    }
}