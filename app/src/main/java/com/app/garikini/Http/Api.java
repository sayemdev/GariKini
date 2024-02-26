package com.app.garikini.Http;

import com.app.garikini.Constants;
import com.app.garikini.Model.Ad;
import com.app.garikini.Model.LoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Belal on 10/2/2017.
 */

public interface Api {

    String BASE_URL = Constants.BASE_URL;

    @GET("get_ads.php")
    Call<List<Ad>> GetAds();
    @Multipart
    @POST("get_ads_cat.php")
    Call<List<Ad>> GetFilteredAds(@Part("category") RequestBody title);

    @Multipart
    @POST("insert_ads.php")
    Call<String> uploadImages(@Part List<MultipartBody.Part> images, @Part("title") RequestBody title, @Part("description") RequestBody description, @Part("price") RequestBody price, @Part("posted_by") RequestBody postedBy, @Part("brand") RequestBody brand, @Part("model") RequestBody model, @Part("edition") RequestBody edition, @Part("production_year") RequestBody productionYear, @Part("registration_year") RequestBody registrationYear, @Part("condition") RequestBody condition, @Part("transmission") RequestBody transmission, @Part("body_type") RequestBody bodyType, @Part("fuel_type") RequestBody fuelType, @Part("engine_power") RequestBody enginePower, @Part("kilometre_run") RequestBody kilometreRun, @Part("class") RequestBody adClass, @Part("category") RequestBody category);

    @Multipart
    @POST("insert_other_ads.php")
    Call<String> insertOtherAds(@Part List<MultipartBody.Part> images, @Part("title") RequestBody title, @Part("description") RequestBody description, @Part("price") RequestBody price, @Part("posted_by") RequestBody postedBy, @Part("condition") RequestBody condition, @Part("negotiable") RequestBody negotiable, @Part("category") RequestBody category, @Part("brand") RequestBody brandRequest, @Part("model") RequestBody modelRequest, @Part("kilometre_run") RequestBody mileageRequest, @Part("engine_power") RequestBody ccRequest, @Part("phone") RequestBody phoneRequest);

    @Multipart
    @POST("register.php")
    Call<LoginResponse> Registration(@Part("name") RequestBody name, @Part("phone") RequestBody phone, @Part("email") RequestBody email, @Part("password") RequestBody password, @Part("location") RequestBody location);

    @Multipart
    @POST("login.php")
    Call<LoginResponse> Login(@Part("phone") RequestBody phone, @Part("password") RequestBody password);

}
