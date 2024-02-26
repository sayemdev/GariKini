package com.app.garikini.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.app.garikini.Slider.SliderAdapter;
import com.app.garikini.Slider.SliderItem;
import com.app.garikini.databinding.FragmentHomeFragmentBinding;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragmentFragment extends Fragment {

    RecyclerView carList;
    List<Ad> ads;
    ClickListener listener;
    View view;
    FragmentHomeFragmentBinding binding;

    public HomeFragmentFragment() {
        // Required empty public constructor
    }

    public HomeFragmentFragment(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeFragmentBinding binding = FragmentHomeFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        ads = new ArrayList<>();

        carList = view.findViewById(R.id.carListRCV);
        carList.setHasFixedSize(true);
        carList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        SliderView sliderView = view.findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(view.getContext());
        sliderAdapter.addItem(new SliderItem("Toyota Alion Package: G 2016", "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8Y2Fyc3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80", "Toyota Automobiles", "৳31,20,000"));
        sliderAdapter.addItem(new SliderItem("Garikini Alion Package: G 2018", "https://cdn.luxe.digital/media/2020/12/16175812/most-expensive-cars-2021-luxe-digital%402x-1536x768.jpg", "Toyota Automobiles", "৳31,20,000"));
        sliderAdapter.addItem(new SliderItem("Bugatti Alion Package: G 2020", "https://cdn.luxe.digital/media/2020/12/16175731/most-expensive-cars-2021-Bentley-Flying-Spur-Speed-luxe-digital%402x.jpg", "Toyota Automobiles", "৳31,20,000"));
        sliderAdapter.addItem(new SliderItem(" ", "https://cdn.luxe.digital/media/2020/12/16175731/most-expensive-cars-2021-Bentley-Flying-Spur-Speed-luxe-digital%402x.jpg"));
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        binding.categoryFilter.setOnClickListener(view1 -> {
            listener.CategoryClick();
        });


        getData();

        return view;
    }

    void getData() {
            ProgressDialog dialog=new ProgressDialog(view.getContext());
            dialog.setTitle("Getting posts");
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
        Call<List<Ad>> adCall = RetrofitClient.getInstance().getMyApi().GetAds();
        adCall.enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                if (response.isSuccessful()) {
                    carList.setAdapter(new CarListAdapter(response.body(), view.getContext()));
//                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                t.printStackTrace();
//                dialog.dismiss();

            }
        });
    }

    void getFilteredData(String s) {

        RequestBody sBody = RequestBody.create(MediaType.parse("text/plain"), s);

        Call<List<Ad>> adCall = RetrofitClient.getInstance().getMyApi().GetFilteredAds(sBody);
        adCall.enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                if (response.isSuccessful()) {
                    carList.setAdapter(new CarListAdapter(response.body(), view.getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void searchByCat(String s) {
        if (s.equals("All Ads")) {
            getData();
        }else {
            getFilteredData(s);
        }
    }

    public interface ClickListener {
        void CategoryClick();
    }
}