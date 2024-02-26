package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentKMRunBinding;

public class KMRunFragment extends Fragment {

    FragmentKMRunBinding binding;
    FragmentListeners fragmentListeners;

    public KMRunFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public KMRunFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKMRunBinding.inflate(getLayoutInflater());
        binding.nextButton.setOnClickListener(v -> {
            fragmentListeners.nextKMRun(binding.distanceEt.getText().toString().trim());
        });

        binding.backButton.setOnClickListener(view -> {
            fragmentListeners.back();
        });

        binding.closeButton.setOnClickListener(view -> {
            fragmentListeners.close();
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            fragmentListeners = (FragmentListeners) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement EditPhoneNumberValueListener");
        }
    }

    public interface FragmentListeners {
        void nextKMRun(String mileage);

        void back();

        void close();
    }


}