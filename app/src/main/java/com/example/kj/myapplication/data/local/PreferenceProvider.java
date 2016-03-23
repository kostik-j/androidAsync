package com.example.kj.myapplication.data.local;

import android.content.SharedPreferences;

import com.example.kj.myapplication.entity.AuthData;

public class PreferenceProvider implements IPreferenceProvider {
    SharedPreferences mSharedPreferences;

    public PreferenceProvider(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public AuthData getAuthData() {
        return null;
    }

    public void setAuthData(AuthData authData) {

    }
}
