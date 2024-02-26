package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentPowerOfEngineBinding;

public class PowerOfEngineFragment extends Fragment {

    FragmentPowerOfEngineBinding binding;
    FragmentListeners fragmentListeners;

    public PowerOfEngineFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public PowerOfEngineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPowerOfEngineBinding.inflate(getLayoutInflater());

        binding.nextButton.setOnClickListener(v -> {
            fragmentListeners.nextPowerOfEngineFragment(binding.powerOfEngineET.getText().toString().trim());
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
        void nextPowerOfEngineFragment(String powerOfEngine);

        void back();

        void close();
    }


}