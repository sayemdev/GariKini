package com.app.garikini.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.garikini.Adapter.ImagesAdapter;
import com.app.garikini.databinding.FragmentUploadPicBinding;
import com.creativechintak.multiimagepicker.builder.MultiImagePicker;

import java.util.ArrayList;
import java.util.List;

public class UploadPicFragment extends Fragment {

    private static final int REQUEST_CODE_SELECT_IMAGES = 111;
    private static final int REQUEST_CODE_PERMISSION = 121;
    private static final String TAG = "UploadPicFragment";
    ClickListener clickListener;

    FragmentUploadPicBinding binding;
    List<Uri> imageUris;

    public UploadPicFragment() {
    }

    public UploadPicFragment(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUploadPicBinding.inflate(inflater, container, false);

        binding.imageList.setHasFixedSize(true);
        binding.imageList.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));

        binding.placeHolder.setOnClickListener(view -> {
            checkAndRequestPermissions();
        });
        binding.selectButton.setOnClickListener(view -> {
            checkAndRequestPermissions();
        });

        binding.postButton.setOnClickListener(view -> {
            clickListener.post(binding.titleTV.getText().toString(), binding.descriptionTV.getText().toString(), binding.priceTV.getText().toString());
        });

        binding.backButton.setOnClickListener(view -> {
            clickListener.back();
        });

        binding.closeButton.setOnClickListener(view -> {
            clickListener.close();
        });

        return binding.getRoot();
    }

    public void setImages(List<Uri> imageUri) {
        this.imageUris = imageUri;
        binding.imageList.setVisibility(View.VISIBLE);
        binding.imageList.setAdapter(new ImagesAdapter(binding.getRoot().getContext(), imageUris, new ImagesAdapter.Listener() {
            @Override
            public void addPhotoListener(int position) {
                openGalleryForImageSelection();
            }
        }));
        binding.postButton.setVisibility(View.VISIBLE);
        binding.placeHolder.setVisibility(View.GONE);
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 33) {
            String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission.
                    ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE_PERMISSION);
                    return;
                }
            }
            openGalleryForImageSelection();

        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission.
                    ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE_PERMISSION);
                    return;
                }
            }
            openGalleryForImageSelection();
        }
    }

    private void openGalleryForImageSelection() {
        clickListener.upload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    // Handle the case where the user denied a permission.
                    // You may want to inform the user or disable the functionality.
                    return;
                }
            }
            openGalleryForImageSelection();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiImagePicker.REQUEST_PICK_MULTI_IMAGES && resultCode == RESULT_OK) {
            MultiImagePicker.Result result = new MultiImagePicker.Result(data);
            if (result.isSuccess()) {
                ArrayList<Uri> imageListInUri = result.getImageList(); // List os selected images as content Uri format
                //You can also request list as absolute filepath instead of Uri as below
                ArrayList<String> imageListInAbsFilePath = result.getImageListAsAbsolutePath(binding.getRoot().getContext());
                Log.d(TAG, "onActivityResult: " + imageListInAbsFilePath);
            }
        }
        Log.d(TAG, "onActivityResult: called");
    }

    public interface ClickListener {
        void upload();

        void back();

        void close();

        void post(String title, String description, String price);
    }
}