package com.app.garikini.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.garikini.Adapter.CarListAdapter;
import com.app.garikini.Http.RetrofitClient;
import com.app.garikini.Model.Ad;
import com.app.garikini.R;
import com.app.garikini.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    RecyclerView carList;
    List<Ad> ads;
    HomeFragmentFragment.ClickListener listener;
    FragmentSearchBinding binding;
    View view;
    CarListAdapter carListAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        ads = new ArrayList<>();

        carList = view.findViewById(R.id.carListRCV);
        carList.setHasFixedSize(true);
        carList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        carListAdapter = new CarListAdapter(ads, view.getContext());

        binding.searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterD(editable.toString());
            }
        });

        getData();

        return view;
    }

    private void filterD(String s) {

        List<Ad> filtered = new ArrayList<>();
        for (Ad ad : ads) {
            Log.d(TAG, "filterD: "+ad+" ==== "+s);
            if (ad.getTitle().toLowerCase().contains(s.toLowerCase()) || ad.getDescription().toLowerCase().contains(s.toLowerCase())) {
                filtered.add(ad);
            }
        }
        Log.d(TAG, "filterD: "+filtered.size());
        carListAdapter.filteredList(filtered);
    }

    private static final String TAG = "SearchFragment";
    void getData() {
        ProgressDialog dialog=new ProgressDialog(view.getContext());
        dialog.setTitle("Getting posts");
        dialog.setMessage("Please wait....");
        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
        Call<List<Ad>> adCall = RetrofitClient.getInstance().getMyApi().GetAds();
        adCall.enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
//                dialog.dismiss();
                if (response.isSuccessful()) {
                    ads.clear();
                    ads=response.body();
                    carListAdapter = new CarListAdapter(ads, view.getContext());
                    carList.setAdapter(carListAdapter);
                    carListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                t.printStackTrace();
//                dialog.dismiss();
            }
        });
    }


}