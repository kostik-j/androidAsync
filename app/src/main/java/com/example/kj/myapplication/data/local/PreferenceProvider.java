package com.example.kj.myapplication.data.local;

import android.content.SharedPreferences;

import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.SecretToken;

public class PreferenceProvider implements IPreferenceProvider {
    final String SETTING_ANKETA_ID = "anketa_id";
    final String SETTING_SECRET = "secret";

    SharedPreferences mSharedPreferences;

    public PreferenceProvider(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
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
            mSharedPreferences.edit().remove(SETTING_SECRET).apply();
        } else {
            mSharedPreferences.edit().putString(SETTING_SECRET, secretToken.toString()).apply();
        }
    }
    public long getAnketaId() {
        return mSharedPreferences.getLong(SETTING_ANKETA_ID, 0);
    }

    public void setAnketaId(long anketaId) {
        if (anketaId == 0) {
            mSharedPreferences.edit().remove(SETTING_ANKETA_ID).apply();
        } else {
            mSharedPreferences.edit().putLong(SETTING_ANKETA_ID, anketaId).apply();
        }
    }
}
