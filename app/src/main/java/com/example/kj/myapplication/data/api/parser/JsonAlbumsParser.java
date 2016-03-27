package com.example.kj.myapplication.data.api.parser;

import android.net.Uri;

import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonAlbumsParser extends JsonBaseParser<ArrayList<Album>> {
    final String FIELD_ALBUMS = "albums";
    final String FIELD_ID = "id";
    final String FIELD_NAME = "name";
    final String FIELD_PHOTO_COUNT = "photoCounts";
    final String FIELD_PHOTO = "coverUrl";

    @Override
    protected ArrayList<Album> mapResponseToObject(JSONObject object) throws JSONException {
        ArrayList<Album> albums = new ArrayList<>();
        JSONArray albumsArray = object.getJSONArray(FIELD_ALBUMS);
        for (int i = 0; i < albumsArray.length(); i++) {
            try {
                albums.add(getAlbum(albumsArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return albums;
    }

    private Album getAlbum(JSONObject object) throws JSONException {
        String photo = object.getString(FIELD_PHOTO);
        try {
            new URL(photo);
        } catch (MalformedURLException malformedURLException) {
            photo = "";
        }

        return new Album(
                object.getLong(FIELD_ID),
                object.getString(FIELD_NAME),
                photo,
                object.getInt(FIELD_PHOTO_COUNT)
        );
    }
}
