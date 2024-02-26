package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentProductionYearBinding;

import java.util.Calendar;

public class ProductionYearFragment extends Fragment {

    FragmentProductionYearBinding binding;
    FragmentListeners fragmentListeners;
    int value;

    public ProductionYearFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public ProductionYearFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProductionYearBinding.inflate(getLayoutInflater());

        binding.numberPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));
        binding.numberPicker.setMinValue(Calendar.getInstance().get(Calendar.YEAR) - 100);
        binding.numberPicker.setValue(Calendar.getInstance().get(Calendar.YEAR));
        binding.numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            this.value = newVal;
        });

        binding.nextButton.setOnClickListener(v -> {
            fragmentListeners.nextProductionYear(value+"");
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
        void nextProductionYear(String edition);

        void back();

        void close();
    }


}