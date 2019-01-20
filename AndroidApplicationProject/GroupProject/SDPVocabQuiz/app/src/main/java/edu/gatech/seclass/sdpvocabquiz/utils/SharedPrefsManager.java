package edu.gatech.seclass.sdpvocabquiz.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

    public static final String MY_PREFERNCES = "sdpvocabquizprefs";
    public static final String PREF_USERNAME = "username";
    public static final String PREF_REMEMBER_ME = "rememberMeTick";

    public static SharedPreferences sharedPreferences;

    public static SharedPreferences loadPreferences(Activity activity){
        if(sharedPreferences == null){
            sharedPreferences = activity.getSharedPreferences(MY_PREFERNCES, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void setPreferenceString(Activity activity, String key, String value){
        loadPreferences(activity);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setPreferenceInt(Activity activity, String key, int value){
        loadPreferences(activity);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setPreferenceFloat(Activity activity, String key, float value){
        loadPreferences(activity);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    // To get values use methods
    // getString("KEY", null);
}
