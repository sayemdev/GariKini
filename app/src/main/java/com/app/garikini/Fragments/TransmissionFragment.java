package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentTransmisionBinding;

public class TransmissionFragment extends Fragment {

    FragmentListeners fragmentListeners;
    FragmentTransmisionBinding binding;

    public TransmissionFragment() {
        // Required empty public constructor
    }

    public TransmissionFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransmisionBinding.inflate(getLayoutInflater());

        binding.automaticMCV.setOnClickListener(v -> {
            fragmentListeners.nextTransmission("Automatic");
        });
        binding.manualMCV.setOnClickListener(v -> {
            fragmentListeners.nextTransmission("Manual");
        });
        binding.otherTransmissionMCV.setOnClickListener(v -> {
            fragmentListeners.nextTransmission("Other");
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
        void nextTransmission(String transmission);

        void back();

        void close();
    }


}