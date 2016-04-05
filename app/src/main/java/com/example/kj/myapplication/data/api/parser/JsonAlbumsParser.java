package com.example.kj.myapplication.data.api.parser;

import android.net.Uri;

import com.example.kj.myapplication.data.api.ApiErrorException;
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

    @Override
    protected ArrayList<Album> mapResponseToObject(JSONObject object) throws JSONException, ApiErrorException {
        JsonAlbumParser albumParser = new JsonAlbumParser();
        ArrayList<Album> albums = new ArrayList<>();
        JSONArray albumsArray = object.getJSONArray(FIELD_ALBUMS);
        for (int i = 0; i < albumsArray.length(); i++) {
            Album album = albumParser.parse(albumsArray.getJSONObject(i).toString());
            if (album != null) {
                albums.add(album);
            }
        }
        return albums;
    }
}
