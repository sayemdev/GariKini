package com.app.garikini;

import static com.app.garikini.Activity.LoginActivity.USER_DATA;

import android.content.Context;

import com.app.garikini.Model.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Constants {
    public static final String BASE_URL = "https://garikini.ghoroya.com/";


    public static boolean isLoggedIn(Context context) {
        return context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).getBoolean("log", false);
    }

    public static Result getUser(Context context) {
        String data = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).getString("data", "");
        Gson gson = new Gson();

        return gson.fromJson((data), new TypeToken<Result>() {
        }.getType());

    }


}
