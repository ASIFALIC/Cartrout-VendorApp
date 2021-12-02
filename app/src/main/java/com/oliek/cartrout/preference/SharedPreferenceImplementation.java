package com.oliek.cartrout.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.oliek.cartrout.GlobalConstants;


public class SharedPreferenceImplementation {

    private SharedPreferences mSharedPrefs;

    public SharedPreferenceImplementation(Context context){
        mSharedPrefs = context.getSharedPreferences(GlobalConstants.USER_PREFS, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return mSharedPrefs.getString(key, null);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.clear();
        editor.commit();
    }


}
