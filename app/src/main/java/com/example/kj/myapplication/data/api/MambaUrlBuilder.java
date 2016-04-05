package com.example.kj.myapplication.data.api;

import com.example.kj.myapplication.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class MambaUrlBuilder {
    private String mBaseUrl;

    public MambaUrlBuilder() {
        mBaseUrl = BuildConfig.BASE_URL;
    }

    private URL getUrl(String path) {
        try {
            return new URL(mBaseUrl + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getSecretLoginUrl() {
        return getUrl("login/secret/");
    }

    public URL getProfileUrl() {
        return getUrl("profile/");
    }

    public URL getLoginUrl() {
        return getUrl("login/");
    }

    public URL getAllContactsUrl() {
        return getUrl("contacts/all/");
    }

    public URL getAnketaUrl(long anketaId) {
        return getUrl("users/" + String.valueOf(anketaId) + "/");
    }

    public URL getAlbumsUrl(long anketaId) {
        return getUrl("users/" + String.valueOf(anketaId) + "/albums/");
    }

    public URL getCreateAlbumUrl() {
        return getUrl("albums/");
    }
}
