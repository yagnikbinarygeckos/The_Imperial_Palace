package com.app.theimperialpalace.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtils {
    public static final String USER_ID = "user_id";
    private final static String The_Imperial_Palace = "the_imperial_palace";
    private final static String LOGINTOKEN = "login_token";
    private final static String token_new = "token_new";



    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(The_Imperial_Palace, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getSharedPreferenceString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(The_Imperial_Palace, 0);
        return settings.getString(key, "");
    }

}
