package com.app.garikini.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.garikini.Adapter.HeavyBodyTypeAdapter;
import com.app.garikini.Model.ChooseSellOptionModel;
import com.app.garikini.R;
import com.app.garikini.databinding.FragmentBodyTypeBinding;

import java.util.ArrayList;
import java.util.List;

public class BodyTypeFragment extends Fragment implements HeavyBodyTypeAdapter.Listener {

    private static final String TAG = "BodyTypeFragment";
    FragmentBodyTypeBinding binding;
    FragmentListeners fragmentListeners;
    int type;
    List<ChooseSellOptionModel> bodyTypeList;

    public BodyTypeFragment(FragmentListeners fragmentListeners, int type) {
        this.fragmentListeners = fragmentListeners;
        this.type = type;
    }

    public BodyTypeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBodyTypeBinding.inflate(getLayoutInflater());

        if (type == 0) {
            binding.heavyBodyTypeList.setVisibility(View.GONE);
            binding.lightBodyType.setVisibility(View.VISIBLE);
            binding.lightHeavyImg.setImageResource(R.drawable.ic_car);
        } else if(type==1){
            binding.heavyBodyTypeList.setVisibility(View.VISIBLE);
            binding.lightBodyType.setVisibility(View.GONE);
            binding.lightHeavyImg.setImageResource(R.drawable.heavyeq);
            bodyTypeList = new ArrayList<>();

            bodyTypeList.add(new ChooseSellOptionModel("Truck", R.drawable.truck));
            bodyTypeList.add(new ChooseSellOptionModel("Cover Van", R.drawable.cover_van));
            bodyTypeList.add(new ChooseSellOptionModel("Oil Tanker", R.drawable.oil_tanker));
            bodyTypeList.add(new ChooseSellOptionModel("Lory", R.drawable.lory));
            bodyTypeList.add(new ChooseSellOptionModel("Sand Truck", R.drawable.sand_truck));
            bodyTypeList.add(new ChooseSellOptionModel("City Transit Bus", R.drawable.city_bus));
            bodyTypeList.add(new ChooseSellOptionModel("Double Decker Bus", R.drawable.double_decker));
            bodyTypeList.add(new ChooseSellOptionModel("School Bus", R.drawable.school_bus));
            bodyTypeList.add(new ChooseSellOptionModel("Dump Truck", R.drawable.dump));
            bodyTypeList.add(new ChooseSellOptionModel("Fire Truck", R.drawable.firetruck));
            bodyTypeList.add(new ChooseSellOptionModel("Refrigerate Van", R.drawable.refrigarate));

            binding.heavyBodyTypeList.setHasFixedSize(true);
            binding.heavyBodyTypeList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.heavyBodyTypeList.setAdapter(new HeavyBodyTypeAdapter(bodyTypeList, binding.getRoot().getContext(), BodyTypeFragment.this));


        }else if (type==2){
            binding.heavyBodyTypeList.setVisibility(View.VISIBLE);
            binding.lightBodyType.setVisibility(View.GONE);
            binding.lightHeavyImg.setImageResource(R.drawable.heavy_vehicle);
            bodyTypeList = new ArrayList<>();

            bodyTypeList.add(new ChooseSellOptionModel("Tractor", R.drawable.tractor));
            bodyTypeList.add(new ChooseSellOptionModel("Bulldozer", R.drawable.bulldozer));
            bodyTypeList.add(new ChooseSellOptionModel("Tower Crane", R.drawable.tower_crane));
            bodyTypeList.add(new ChooseSellOptionModel("Wrecking Ball Crane", R.drawable.ball_crane));
            bodyTypeList.add(new ChooseSellOptionModel("Excavator", R.drawable.excavator));
            bodyTypeList.add(new ChooseSellOptionModel("Road Roller", R.drawable.road_roller));
            bodyTypeList.add(new ChooseSellOptionModel("Tow", R.drawable.tow));
            bodyTypeList.add(new ChooseSellOptionModel("Concrete Mixture", R.drawable.concrete));

            binding.heavyBodyTypeList.setHasFixedSize(true);
            binding.heavyBodyTypeList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.heavyBodyTypeList.setAdapter(new HeavyBodyTypeAdapter(bodyTypeList, binding.getRoot().getContext(), BodyTypeFragment.this));
        }

        binding.backButton.setOnClickListener(view -> {
            fragmentListeners.back();
        });

        binding.closeButton.setOnClickListener(view -> {
            fragmentListeners.close();
        });

        binding.sedanMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Sedan");
        });

        binding.coupleMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Couple");

        });
        binding.salonMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Salon");

        });
        binding.hatchbackMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Hatchback");

        });
        binding.esterMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Ester");

        });
        binding.convertibleMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Convertible");

        });
        binding.sportsMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Sports");

        });
        binding.suvMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Suv/4X");

        });
        binding.miniVan.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("Mini Van");

        });
        binding.mvpMCV.setOnClickListener(view -> {
            fragmentListeners.nextBodyType("MVP");

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
    public void onClick(int position) {
        Log.d(TAG, "onClick: " + position);
        fragmentListeners.nextBodyType(bodyTypeList.get(position).getName());
    }

    public interface FragmentListeners {
        void nextBodyType(String bodyType);

        void close();

        void back();
    }


}