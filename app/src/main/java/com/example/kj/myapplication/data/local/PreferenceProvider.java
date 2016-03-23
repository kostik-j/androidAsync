package com.example.kj.myapplication.data.local;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.entity.SidToken;

public class PreferenceProvider implements IPreferenceProvider {
    final String SETTING_SID = "sid_token";
    final String SETTING_ANKETA_ID = "anketa_id";
    final String SETTING_SECRET = "sid_secret";

    SharedPreferences mSharedPreferences;

    public PreferenceProvider(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public AuthData getAuthData() {
        return null;
    }

    public void setAuthData(AuthData authData) {}

    public SidToken getSidToken() {
        try {
            String sid = mSharedPreferences.getString(SETTING_SID, "");
            return sid.isEmpty() ? null : new SidToken(sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSidToken(SidToken sidToken) {
        if (sidToken == null) {
            mSharedPreferences.edit().remove(SETTING_SID).commit();
        } else {
            mSharedPreferences.edit().putString(SETTING_SID, sidToken.toString()).commit();
        }
    }

    public SecretToken getSecretToken() {
        try {
            String sid = mSharedPreferences.getString(SETTING_SECRET, "");
            return sid.isEmpty() ? null : new SecretToken(sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSecretToken(SecretToken secretToken) {
        if (secretToken == null) {
            mSharedPreferences.edit().remove(SETTING_SECRET).commit();
        } else {
            mSharedPreferences.edit().putString(SETTING_SECRET, secretToken.toString()).commit();
        }
    }
}
