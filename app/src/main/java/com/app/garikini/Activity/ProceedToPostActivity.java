package com.app.garikini.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.garikini.Adapter.ImagesAdapter;
import com.app.garikini.Fragments.BodyTypeFragment;
import com.app.garikini.Fragments.BrandFragment;
import com.app.garikini.Fragments.CarEditionFragment;
import com.app.garikini.Fragments.ConditionFragment;
import com.app.garikini.Fragments.FuelTypeFragment;
import com.app.garikini.Fragments.KMRunFragment;
import com.app.garikini.Fragments.PowerOfEngineFragment;
import com.app.garikini.Fragments.ProductionYearFragment;
import com.app.garikini.Fragments.SelectCarModelFragment;
import com.app.garikini.Fragments.SelectLocationFragment;
import com.app.garikini.Fragments.TransmissionFragment;
import com.app.garikini.Fragments.UploadPicFragment;
import com.app.garikini.Http.RetrofitClient;
import com.app.garikini.R;
import com.app.garikini.databinding.ActivityProceedToPostBinding;
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

public class ProceedToPostActivity extends AppCompatActivity implements SelectLocationFragment.FragmentListeners, ConditionFragment.FragmentListeners, BrandFragment.FragmentListeners, SelectCarModelFragment.FragmentListeners, CarEditionFragment.FragmentListeners, ProductionYearFragment.FragmentListeners, KMRunFragment.FragmentListeners, PowerOfEngineFragment.FragmentListeners, FuelTypeFragment.FragmentListeners, TransmissionFragment.FragmentListeners, BodyTypeFragment.FragmentListeners, UploadPicFragment.ClickListener {
    private static final String TAG = "ProceedToPostActivity";
    private static final int REQUEST_CODE = 1;
    ActivityProceedToPostBinding binding;
    UploadPicFragment uploadPicFragment;
    List<Uri> imageUris;
      String title, description, price, postedBy, brand, model, edition, productionYear, registrationYear = "2011", condition, transmission, bodyType, fuelType, enginePower, kmRun, classType;
    String location;
    int type;

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

    public String getFileName(Uri uri) {
        String fileName = getFileNameFromCursor(uri);
        if (fileName == null) {
            String fileExtension = getFileExtension(uri);
            fileName = "temp_file" + (fileExtension != null ? "." + fileExtension : "");
        } else if (!fileName.contains(".")) {
            String fileExtension = getFileExtension(uri);
            fileName = fileName + "." + fileExtension;
        }
        return fileName;
    }

    public String getFileExtension(Uri uri) {
        String fileType = this.getContentResolver().getType(uri);
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType);
    }

    public String getFileNameFromCursor(Uri uri) {
        Cursor fileCursor = ProceedToPostActivity.this.getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
        String fileName = null;
        if (fileCursor != null && fileCursor.moveToFirst()) {
            int cIndex = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cIndex != -1) {
                fileName = fileCursor.getString(cIndex);
            }
        }
        return fileName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProceedToPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getIntExtra("type", -1);
        Log.d(TAG, "onCreate: " + getIntent().getStringExtra("title"));

        uploadPicFragment = new UploadPicFragment(this);


        imageUris = new ArrayList<>();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SelectLocationFragment(this)).addToBackStack(null).commit();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void nextLocation(String location) {
        this.location = location;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ConditionFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void back() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Log.i("MainActivity", "popping backstack" + getSupportFragmentManager().getBackStackEntryCount());
            getSupportFragmentManager().popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            finish();
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void nextCondition(String condition) {
        this.condition = condition;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new BrandFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextBrand(String brand) {
        this.brand = brand;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SelectCarModelFragment(this, brand)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextCarModel(String model) {
        this.model = model;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CarEditionFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextCarEdition(String edition) {
        this.edition = edition;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProductionYearFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextProductionYear(String productionYear) {
        this.productionYear = productionYear;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new KMRunFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextKMRun(String mileage) {
        this.kmRun = mileage;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PowerOfEngineFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextPowerOfEngineFragment(String powerOfEngine) {
        this.enginePower = powerOfEngine;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FuelTypeFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextFuelType(String fuelType) {
        this.fuelType = fuelType;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TransmissionFragment(this)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextTransmission(String transmission) {
        this.transmission = transmission;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new BodyTypeFragment(this, type)).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    @Override
    public void nextBodyType(String bodyType) {
        this.bodyType = bodyType;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, uploadPicFragment).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).addToBackStack(null).commit();
    }

    ImagesAdapter adapter;
    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            Log.d(TAG, "Image selection calling: ");
            Uri img = result.getUriContent();
            if (!com.app.garikini.UriUtils.doesUriExistInList(img, imageUris)) {
                imageUris.add(img);
                uploadPicFragment.setImages(imageUris);
            }
        }
    });
    private void launchImageCropper(Uri uri) {
        CropImageOptions cropImageOptions = new CropImageOptions();
        cropImageOptions.imageSourceIncludeGallery = false;
        cropImageOptions.imageSourceIncludeCamera = true;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(uri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " jbjh jhb " + resultCode);
        if (requestCode == MultiImagePicker.REQUEST_PICK_MULTI_IMAGES && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                if (count > 0) {
//                    imageUris.clear();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        imageUris.add(imageUri);
                        launchImageCropper(imageUri);
                    }
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    /*imageUris.clear();
                    imageUris.add(imageUri);*/
                    launchImageCropper(imageUri);
                }
            }

           /* if (imageUris.size() > 0) {
                uploadPicFragment.setImages(imageUris);
            }*/

            Log.d(TAG, "onActivityResult: unseccess" + imageUris.size());
        }
        Log.d(TAG, "onActivityResult: " + requestCode + "==" + REQUEST_CODE + "&&" + resultCode + "==" + RESULT_OK);
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+data);
        try {

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    String title = data.getStringExtra("title");
                    String description = data.getStringExtra("description");
                    String price = data.getStringExtra("price");
                    boostPost(title, description, price);
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "ERROR 1 "+ex.toString(),
                    Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }


    }


    private final ActivityResultLauncher<Intent> startActivityForResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                String title = data.getStringExtra("title");
                                String description = data.getStringExtra("description");
                                String price = data.getStringExtra("price");
                                boostPost(title, description, price);
                            }
                        }
                    });



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




    @Override
    public void upload() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MultiImagePicker.REQUEST_PICK_MULTI_IMAGES);
    }


    @Override
    public void post(String title, String description, String price) {

        Intent intent = new Intent(this, MembershipActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("price", price);
//        startActivityForResult(intent, REQUEST_CODE);
        startActivityForResultLauncher.launch(intent);
    }


    public void boostPost(String title, String description, String price) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Boosting your post");
        dialog.setMessage("Please wait until it's completed");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (Uri uri : imageUris) {
            File file = UriUtils.uri2File(uri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images[]", file.getName(), requestFile);
            imageParts.add(imagePart);
        }

        RequestBody titleRequest = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody categoryRequestBody = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("title") + "");
        RequestBody descriptionRequest = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody priceRequest = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody postedByRequest = RequestBody.create(MediaType.parse("text/plain"), "postedBy");
        RequestBody brandRequest = RequestBody.create(MediaType.parse("text/plain"), brand);
        RequestBody modelRequest = RequestBody.create(MediaType.parse("text/plain"), model);
        RequestBody editionRequest = RequestBody.create(MediaType.parse("text/plain"), edition);
        RequestBody productionYearRequest = RequestBody.create(MediaType.parse("text/plain"), productionYear);
        RequestBody registrationYearRequest = RequestBody.create(MediaType.parse("text/plain"), registrationYear);
        RequestBody conditionRequest = RequestBody.create(MediaType.parse("text/plain"), condition);
        RequestBody transmissionRequest = RequestBody.create(MediaType.parse("text/plain"), transmission);
        RequestBody bodyTypeRequest = RequestBody.create(MediaType.parse("text/plain"), bodyType);
        RequestBody fuelTypeRequest = RequestBody.create(MediaType.parse("text/plain"), fuelType);
        RequestBody enginePowerRequest = RequestBody.create(MediaType.parse("text/plain"), enginePower);
        RequestBody kmRunRequest = RequestBody.create(MediaType.parse("text/plain"), kmRun);
        RequestBody classTypeRequest = RequestBody.create(MediaType.parse("text/plain"), "High");
        Call<String> call = RetrofitClient.getInstance().getMyApi().uploadImages(imageParts, titleRequest, descriptionRequest, priceRequest, postedByRequest, brandRequest, modelRequest, editionRequest, productionYearRequest, registrationYearRequest, conditionRequest, transmissionRequest, bodyTypeRequest, fuelTypeRequest, enginePowerRequest, kmRunRequest, classTypeRequest, categoryRequestBody);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                Log.d(TAG, "onResponse: " + response.body());
                String concatenatedString = "Title: " + title + "\nDescription: " + description + "\nPrice: " + price + "\nPosted By: " + "postedBy" + "\nBrand: " + brand + "\nModel: " + model + "\nEdition: " + edition + "\nProduction Year: " + productionYear + "\nRegistration Year: " + registrationYear + "\nCondition: " + condition + "\nTransmission: " + transmission + "\nBody Type: " + bodyType + "\nFuel Type: " + fuelType + "\nEngine Power: " + enginePower + "\nKilometers Run: " + kmRun;
                try {
                    throw new Exception(concatenatedString);
                } catch (Exception e) {
                    Sentry.captureException(e);
                }
                Toast.makeText(ProceedToPostActivity.this, concatenatedString, Toast.LENGTH_SHORT).show();


                if (response.body().equals("Data inserted successfully")) {
                    Toast.makeText(ProceedToPostActivity.this, "Post Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProceedToPostActivity.this, "Post Added Successfully" + response.body(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ProceedToPostActivity.this, "Post Added Successfully" + response.body(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ProceedToPostActivity.this, MainActivity.class));
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
                Toast.makeText(ProceedToPostActivity.this, "Post Added Successfully" + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Sentry.captureException(t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Log.i("MainActivity", "popping backstack" + getSupportFragmentManager().getBackStackEntryCount());
            getSupportFragmentManager().popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            finish();
        }
    }

}