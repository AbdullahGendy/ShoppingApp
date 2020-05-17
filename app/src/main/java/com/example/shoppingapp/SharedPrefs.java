package com.example.shoppingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {

    private Context context;

    public SharedPrefs(Context context) {
        this.context = context;
    }

    synchronized public void setUserData(String userID,
                                         String userName,
                                         String userEmail,
                                         String userPhone,
                                         String userType,
                                         String userAddress) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_ID_KEY), userID).apply();
        sharedPreferences.edit().putString(context.getString(R.string.USER_FULL_NAME_KEY), userName).apply();
        sharedPreferences.edit().putString(context.getString(R.string.USER_EMAIL_KEY), userEmail).apply();
        sharedPreferences.edit().putString(context.getString(R.string.USER_PHONE_KEY), userPhone).apply();
        sharedPreferences.edit().putString(context.getString(R.string.USER_TYPE), userType).apply();
        sharedPreferences.edit().putString(context.getString(R.string.USER_ADDRESS_KEY), userAddress).apply();


    }


    synchronized public void setUserId(String userId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_ID_KEY), userId).apply();
    }

    public String getUserId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_ID_KEY), "");
    }


    synchronized public void setUserName(String userName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_FULL_NAME_KEY), userName).apply();
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_FULL_NAME_KEY), "en");
    }

    synchronized public void setUserPhone(String userMobile) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_PHONE_KEY), userMobile).apply();
    }

    public String getUserPhone() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_PHONE_KEY), "");
    }


    synchronized public void setUserEmail(String userEmail) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_EMAIL_KEY), userEmail).apply();
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_EMAIL_KEY), "");
    }


    synchronized public void setUserAddress(String userAddress) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_ADDRESS_KEY), userAddress).apply();
    }


    public String getUserAddress() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_ADDRESS_KEY), "");
    }

    synchronized public void setUserType(String userPin) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_TYPE), userPin).apply();
    }


    public String getUserType() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_TYPE), "");
    }

    synchronized public void setUserPhotoUrl(String userPhotoUrl) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(context.getString(R.string.USER_PHOTO_KEY), userPhotoUrl).apply();
    }


    public String getUserPhotoUrl() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.USER_PHOTO_KEY), "");
    }

    public void clearCache() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().clear().apply();
    }


}
