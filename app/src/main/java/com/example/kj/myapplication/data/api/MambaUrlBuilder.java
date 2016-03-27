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

    public URL getAllContacts() {
        try {
            return new URL(mBaseUrl + "contacts/all/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getAnketa(long anketaId) {
        try {
            return new URL(mBaseUrl + "users/" + String.valueOf(anketaId) + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getAbums(long anketaId) {
        try {
            return new URL(mBaseUrl + "users/" + String.valueOf(anketaId) + "/albums/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
