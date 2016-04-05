package com.example.kj.myapplication.data.api.parser;

import android.util.Log;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.entity.Album;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class JsonAlbumParser implements Parser<Album>{
    final String LOG_TAG = JsonAlbumParser.class.getSimpleName();
    final String FIELD_ID = "id";
    final String FIELD_NAME = "name";
    final String FIELD_PHOTO_COUNT = "photoCounts";
    final String FIELD_PHOTO = "coverUrl";

    @Override
    public Album parse(String str) throws ApiErrorException {
        JSONObject object;
        try {
            object = new JSONObject(str);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Null album from: " + str);
        return null;
    }

}
