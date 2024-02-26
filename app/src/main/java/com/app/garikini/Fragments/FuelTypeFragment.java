package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.garikini.databinding.FragmentFuelTypeBinding;

public class FuelTypeFragment extends Fragment {

    FragmentFuelTypeBinding binding;
    FragmentListeners fragmentListeners;


    public FuelTypeFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public FuelTypeFragment() {
    }

    StringBuilder checked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFuelTypeBinding.inflate(getLayoutInflater());




        binding.nextButton.setOnClickListener(v -> {
            checked=new StringBuilder();
            if(binding.diesel.isChecked()){
                checked.append(binding.diesel.getText()).append(",");
            }
            if(binding.petrol.isChecked()){
                checked.append(binding.diesel.getText()).append(",");
            }
            if(binding.cng.isChecked()){
                checked.append(binding.cng.getText()).append(",");
            }
            if(binding.hybrid.isChecked()){
                checked.append(binding.hybrid.getText()).append(",");
            }
            if(binding.electric.isChecked()){
                checked.append(binding.electric.getText()).append(",");
            }
            if(binding.octane.isChecked()){
                checked.append(binding.octane.getText());
            }
            Log.d(TAG, "onCreateView: "+checked);
            fragmentListeners.nextFuelType(checked.toString());
        });

        binding.backButton.setOnClickListener(view -> {
            fragmentListeners.back();
        });

        binding.closeButton.setOnClickListener(view -> {
            fragmentListeners.close();
        });

        return binding.getRoot();
    }

    private static final String TAG = "FuelTypeFragment";

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
        void nextFuelType(String powerOfEngine);

        void back();

        void close();
    }


}