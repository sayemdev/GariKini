package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentSelectLocationBinding;

public class SelectLocationFragment extends Fragment {

    FragmentSelectLocationBinding binding;
    FragmentListeners fragmentListeners;

    public SelectLocationFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public SelectLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectLocationBinding.inflate(getLayoutInflater(), container, false);

        binding.nextButton.setOnClickListener(v -> {
            fragmentListeners.nextLocation("Chittagong");
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
        void nextLocation(String location);
        void back();
        void close();
    }


}