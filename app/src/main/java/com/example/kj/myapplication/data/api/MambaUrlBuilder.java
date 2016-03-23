package com.example.kj.myapplication.data.api;

import com.example.kj.myapplication.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class MambaUrlBuilder {
    private String mBaseUrl;

    public MambaUrlBuilder() {
        mBaseUrl = BuildConfig.BASE_URL;
    }

    public URL getSecretLoginUrl() {
        try {
            return new URL(mBaseUrl + "login/secret/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getLoginUrl() {
        try {
            return new URL(mBaseUrl + "login/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getProfile() {
        try {
            return new URL(mBaseUrl + "profile/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
