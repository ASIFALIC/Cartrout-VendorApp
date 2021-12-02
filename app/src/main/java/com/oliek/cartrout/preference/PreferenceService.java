package com.oliek.cartrout.preference;

import android.content.Context;

import com.google.gson.Gson;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.model.UserModel;


public class PreferenceService {


    private static volatile PreferenceService sInstance;

    private SharedPreferenceImplementation mSharedPrefService;

    public static PreferenceService getInstance(Context context){
        if(null == sInstance){
            sInstance = new PreferenceService(context);
        }

        return sInstance;
    }

    public PreferenceService(Context context){
        mSharedPrefService = new SharedPreferenceImplementation(context);
    }

    public void saveString(String key, String value) {
        mSharedPrefService.saveString(key, value);
    }

    public String getString(String key) {
       return mSharedPrefService.getString(key);
    }

    public void clearAll() {
        mSharedPrefService.clearAll();
    }
    public void clearUser() {
        mSharedPrefService.saveString(GlobalConstants.PREF_KEY_USER,null);
    }

    public void saveUser(String key, UserModel user){
        String jsonString = new Gson().toJson(user);
        mSharedPrefService.saveString(key, jsonString);
    }

    public UserModel getUser() {
        UserModel user = null;
        String jsonString = mSharedPrefService.getString(GlobalConstants.PREF_KEY_USER);
        if (jsonString != null) {
            user = new Gson().fromJson(jsonString, UserModel.class);
        }
        return user;
    }



}
