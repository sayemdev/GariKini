package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.garikini.Adapter.CarBrandAdapter;
import com.app.garikini.Model.CarBrand;
import com.app.garikini.R;
import com.app.garikini.databinding.FragmentBrandBinding;

import java.util.ArrayList;

public class BrandFragment extends Fragment {

    FragmentBrandBinding binding;
    FragmentListeners fragmentListeners;
    ArrayList<CarBrand> carBrands;

    public BrandFragment(FragmentListeners fragmentListeners) {
        this.fragmentListeners = fragmentListeners;
    }

    public BrandFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBrandBinding.inflate(getLayoutInflater(), container, false);
        carBrands = new ArrayList<>();

        carBrands.add(new CarBrand("Toyota", R.drawable.toyota));
        carBrands.add(new CarBrand("Honda", R.drawable.honda_logo));
        carBrands.add(new CarBrand("Suzuki", R.drawable.suzuki));
        carBrands.add(new CarBrand("Nissan", R.drawable.nissan_logo));
        carBrands.add(new CarBrand("Mitsubishi", R.drawable.mitsubishi));
        carBrands.add(new CarBrand("Ford", R.drawable.ford));
        carBrands.add(new CarBrand("Hyundai", R.drawable.hyundai_logo));
        carBrands.add(new CarBrand("BMW", R.drawable.bmw));
        carBrands.add(new CarBrand("Mercedes-Benz", R.drawable.mercedes));
        carBrands.add(new CarBrand("Chevrolet", R.drawable.chevrolet));
        carBrands.add(new CarBrand("Volkswagen", R.drawable.volkswagen));
        carBrands.add(new CarBrand("Kia", R.drawable.kia));
        carBrands.add(new CarBrand("Audi", R.drawable.audi));
        carBrands.add(new CarBrand("Volvo", R.drawable.volvo));
        carBrands.add(new CarBrand("Lexus", R.drawable.lexus));
        carBrands.add(new CarBrand("Subaru", R.drawable.subaru));
        carBrands.add(new CarBrand("Peugeot", R.drawable.peugeot));
        carBrands.add(new CarBrand("Jaguar", R.drawable.jaguar));
        carBrands.add(new CarBrand("Land Rover", R.drawable.landrover));
        carBrands.add(new CarBrand("Fiat", R.drawable.fiat));
        carBrands.add(new CarBrand("Porsche", R.drawable.porsche));
        carBrands.add(new CarBrand("Ferrari", R.drawable.ferrari));
        carBrands.add(new CarBrand("Lamborghini", R.drawable.lamborghini));
        carBrands.add(new CarBrand("TaTa", R.drawable.tata));
        carBrands.add(new CarBrand("Mahindra", R.drawable.mahindra));
        carBrands.add(new CarBrand("JAC", R.drawable.jac));
        carBrands.add(new CarBrand("Proton", R.drawable.proton));
        carBrands.add(new CarBrand("ISUZU", R.drawable.isuzu));


        binding.brandList.setHasFixedSize(true);
        binding.brandList.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),3));
        binding.brandList.setAdapter(new CarBrandAdapter(carBrands, binding.getRoot().getContext(), new CarBrandAdapter.Listener() {
            @Override
            public void OnBrandClick(String brand) {
                fragmentListeners.nextBrand(brand);
            }
        }));
        binding.hondaMCV.setOnClickListener(v -> {
            fragmentListeners.nextBrand("Honda");
        });

        binding.hyundaiMCV.setOnClickListener(v -> {
            fragmentListeners.nextBrand("Hyundai");
        });

        binding.mitshubishiMCV.setOnClickListener(v -> {
            fragmentListeners.nextBrand("Mitshubishi");
        });

        binding.nissanMCV.setOnClickListener(v -> {
            fragmentListeners.nextBrand("Nissan");
        });

        binding.suzukiMCV.setOnClickListener(v -> {
            fragmentListeners.nextBrand("Suzuki");
        });

        binding.toyotaMCV.setOnClickListener(v -> {
            fragmentListeners.nextBrand("Toyota");
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

    public interface FragmentListeners {
        void nextBrand(String brand);

        void back();

        void close();
    }

}