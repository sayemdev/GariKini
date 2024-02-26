package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.garikini.Adapter.CarBrandAdapter;
import com.app.garikini.Adapter.CarModelAdapter;
import com.app.garikini.Model.CarModelList;
import com.app.garikini.databinding.FragmentSelectCarModelBinding;

public class SelectCarModelFragment extends Fragment implements CarBrandAdapter.Listener {

    FragmentSelectCarModelBinding binding;
    FragmentListeners fragmentListeners;
    String brand;

    public SelectCarModelFragment() {
        // Required empty public constructor
    }

    public SelectCarModelFragment(FragmentListeners fragmentListeners, String brand) {
        this.fragmentListeners = fragmentListeners;
        this.brand = brand;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectCarModelBinding.inflate(getLayoutInflater());

        binding.carModelList.setHasFixedSize(true);
        binding.carModelList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.carModelList.setAdapter(new CarModelAdapter(CarModelList.getCarModels().get(brand), binding.getRoot().getContext(), this));

        binding.accordLT.setOnClickListener(v -> {
            fragmentListeners.nextCarModel("Accord");
        });
        binding.airwaveLT.setOnClickListener(v -> {
            fragmentListeners.nextCarModel("Airwave");
        });
        binding.crVLT.setOnClickListener(v -> {
            fragmentListeners.nextCarModel("CR-V");
        });
        binding.cityLt.setOnClickListener(v -> {
            fragmentListeners.nextCarModel("City");
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
            throw new ClassCastException(activity + " must implement EditPhoneNumberValueListener");
        }
    }

    @Override
    public void OnBrandClick(String b) {
        fragmentListeners.nextCarModel(b);
    }

    public interface FragmentListeners {
        void nextCarModel(String carModel);

        void back();

        void close();
    }

}