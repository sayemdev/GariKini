package com.app.garikini.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.app.garikini.Adapter.ImagesAdapter;
import com.app.garikini.Http.RetrofitClient;
import com.app.garikini.R;
import com.app.garikini.databinding.ActivityPostOthersAdsBinding;
import com.blankj.utilcode.util.UriUtils;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.creativechintak.multiimagepicker.builder.MultiImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostOthersAdsActivity extends AppCompatActivity implements ImagesAdapter.Listener {
    private static final int REQUEST_CODE_SELECT_IMAGES = 111;
    private static final int REQUEST_CODE_PERMISSION = 121;
    private static final String TAG = "PostOthersAdsActivity";
    private static final int REQUEST_CODE = 111;
    ActivityPostOthersAdsBinding binding;
    List<Uri> imageUris;
    int type;
    String title;
    File cFile;
    ProgressDialog dialog;
    List<String> imagePaths;
    ImagesAdapter adapter;
    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            Log.d(TAG, "Image selection calling: ");
            Uri img = result.getUriContent();
            if (!com.app.garikini.UriUtils.doesUriExistInList(img, imageUris)) {
                imageUris.add(img);
                adapter.addImg(img);
                setImages();
            }
        }
    });

    public static String getPathFromUri(Context context, Uri uri) {
        if (uri == null) {
            Log.d(TAG, "getPathFromUri: URI NULL");
            return null;
        }

        // If the URI is a content URI, we can use the MediaStore to get the file path.
        if ("content".equals(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } finally {
                if (cursor != null) {
                    cursor.close();
                } else {
                    try {
                        throw new Exception(uri.getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Sentry.captureException(e);
                    }
                }
            }
        } else if ("file".equals(uri.getScheme())) {
            return uri.getPath();
        }
        try {
            throw new Exception(uri.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostOthersAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageUris = new ArrayList<>();
        imagePaths = new ArrayList<>();
        dialog = new ProgressDialog(this);

        type = getIntent().getIntExtra("type", -1);
        title = getIntent().getStringExtra("title");

        binding.titleTV.setText("Post your " + title);

        if (title.equals("Garage") || title.equals("Parking") || title.equals("Auto Service Center") || title.equals("Rental Vehicles") || title.equals("Top Urgent Post")) {
            binding.conditionLT.setVisibility(View.GONE);
            binding.carInfoLT.setVisibility(View.GONE);
        }


        binding.selectButton.setOnClickListener(view -> {
            checkAndRequestPermissions();
        });

        binding.postAdButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MembershipActivity.class);
            if (title.equals("Top Urgent")) {
                intent.putExtra("type", "top");
            }
            startActivityForResult(intent, REQUEST_CODE);
        });

        adapter = new ImagesAdapter(binding.getRoot().getContext(), new ArrayList<>(), this);
        binding.imageList.setAdapter(adapter);


    }

    public void boostPost() {
        List<String> imagePaths = this.imagePaths;
        Log.d(TAG, "post: " + imagePaths.size());
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        String description, titleS, condition, price, brand, model, mileage, cc, phone;
        boolean negotiable;
        description = binding.descriptionET.getText().toString().trim();
        titleS = binding.titleET.getText().toString().trim();
        price = binding.priceET.getText().toString().trim();
        brand = binding.brandET.getText().toString().trim();
        model = binding.modelET.getText().toString().trim();
        mileage = binding.mileageEt.getText().toString().trim();
        cc = binding.ccET.getText().toString().trim();
        phone = binding.phoneET.getText().toString().trim();

        negotiable = binding.isNegotiable.isChecked();
        int selectedId = binding.conditionTypeRG.getCheckedRadioButtonId();

        try {
            RadioButton radioButton = findViewById(selectedId);

            condition = radioButton.getText().toString();
        } catch (Exception e) {
            condition = "NA";
        }
        // find the radiobutton by returned id


        for (Uri uri : imageUris) {
            File file = UriUtils.uri2File(uri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images[]", file.getName(), requestFile);
            imageParts.add(imagePart);
        }

        RequestBody titleRequest = RequestBody.create(MediaType.parse("text/plain"), titleS);
        RequestBody descriptionRequest = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody priceRequest = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody postedByRequest = RequestBody.create(MediaType.parse("text/plain"), "postedBy");
        RequestBody categoryRequestBody = RequestBody.create(MediaType.parse("text/plain"), title + "");

        RequestBody conditionRequest = RequestBody.create(MediaType.parse("text/plain"), condition);
        RequestBody brandRequest = RequestBody.create(MediaType.parse("text/plain"), brand);
        RequestBody modelRequest = RequestBody.create(MediaType.parse("text/plain"), model);
        RequestBody mileageRequest = RequestBody.create(MediaType.parse("text/plain"), mileage);
        RequestBody ccRequest = RequestBody.create(MediaType.parse("text/plain"), cc);
        RequestBody phoneRequest = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody classTypeRequest = RequestBody.create(MediaType.parse("text/plain"), negotiable + "");
        Call<String> call = RetrofitClient.getInstance().getMyApi().insertOtherAds(imageParts, titleRequest, descriptionRequest, priceRequest, postedByRequest, conditionRequest, classTypeRequest, categoryRequestBody, brandRequest, modelRequest, mileageRequest, ccRequest, phoneRequest);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading your ad");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                Log.d(TAG, "onResponse: " + response.body());
                String concatenatedString = "Title: " + title + "Description: " + description + "Price: " + price + "Posted By: " + "postedBy" + "Negotiable: " + negotiable + "Category: " + title;
                try {
                    throw new Exception(concatenatedString);
                } catch (Exception e) {
                    Sentry.captureException(e);
                }
                Toast.makeText(PostOthersAdsActivity.this, concatenatedString, Toast.LENGTH_SHORT).show();


                if (response.body().equals("Data inserted successfully")) {
                    Toast.makeText(PostOthersAdsActivity.this, "Post Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostOthersAdsActivity.this, "Adding Post failed" + response.body(), Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(PostOthersAdsActivity.this, MainActivity.class));
                finish();
                try {
                    throw new Exception(response.body());
                } catch (Exception e) {
                    Sentry.captureException(e);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(PostOthersAdsActivity.this, "Post adding failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Sentry.captureException(t);
            }
        });
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 33) {
            String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission.
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
                    return;
                }
            }
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission.
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
                    return;
                }
            }
        }
        openGalleryForImageSelection();
    }

    private void openGalleryForImageSelection() {
        Log.d(TAG, "openGalleryForImageSelection: " + Build.VERSION.SDK_INT);
//        if (Build.VERSION.SDK_INT >= 33) {
//            final Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
//            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 5);
//            try {
//                startForMultipleModeResult.launch(intent);
//            } catch (ActivityNotFoundException ex) {
//                ex.printStackTrace();
//            }
//        } else {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MultiImagePicker.REQUEST_PICK_MULTI_IMAGES);


//        }
    }

    private void launchImageCropper(Uri uri) {
        CropImageOptions cropImageOptions = new CropImageOptions();
        cropImageOptions.imageSourceIncludeGallery = false;
        cropImageOptions.imageSourceIncludeCamera = true;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(uri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " jbjh jhb " + resultCode);
        if (requestCode == MultiImagePicker.REQUEST_PICK_MULTI_IMAGES && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                if (count > 0 && count < 6) {
//                    imageUris.clear();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        imageUris.add(imageUri);
                        launchImageCropper(imageUri);
                    }
                } else if (count > 5) {
//                    imageUris.clear();
                    for (int i = 0; i < 5; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        imageUris.add(imageUri);
                        launchImageCropper(imageUri);

                    }
                    Toast.makeText(this, "First 5 items selected", Toast.LENGTH_SHORT).show();
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                launchImageCropper(imageUri);
            }

          /*  if (imageUris != null) {
                setImages(imageUris);
            }*/


            Log.d(TAG, "onActivityResult: unseccess" + imageUris.size());
        }


        Log.d(TAG, "onActivityResult: " + requestCode + "==" + MultiImagePicker.REQUEST_PICK_MULTI_IMAGES + "&&" + resultCode + "==" + RESULT_OK);

        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {

                    boostPost();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private List<String> getImagePathsFromUris(List<Uri> imageUris) {
        ArrayList<String> imagePaths = new ArrayList<>();
        for (Uri uri : imageUris) {
            if (uri != null) {
                grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String imagePath = getPathFromUri(this, uri);
                if (imagePath != null) {
                    imagePaths.add(imagePath);
                }
            }
        }
        return imagePaths;
    }

    public void setImages() {
        binding.imageList.setVisibility(View.VISIBLE);
//        binding.imageList.setAdapter(new ImagesAdapter(binding.getRoot().getContext(), imageUris, this));
        binding.placeHolder.setVisibility(View.GONE);


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void addPhotoListener(int position) {
        openGalleryForImageSelection();
    }
}