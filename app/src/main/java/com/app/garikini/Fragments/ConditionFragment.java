package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentConditionBinding;

public class ConditionFragment extends Fragment {

    FragmentConditionBinding binding;
    FragmentListeners fragmentListeners;

    public ConditionFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public ConditionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConditionBinding.inflate(getLayoutInflater(), container, false);

        binding.newMCV.setOnClickListener(v -> {
            fragmentListeners.nextCondition("New");
        });

        binding.usedOrReconditionMCV.setOnClickListener(v -> {
            fragmentListeners.nextCondition("UsedOrRecondition");
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
        void nextCondition(String condition);

        void back();

        void close();
    }

}