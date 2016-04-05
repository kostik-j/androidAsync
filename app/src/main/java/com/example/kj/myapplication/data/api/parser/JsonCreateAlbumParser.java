package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.LogicException;
import com.example.kj.myapplication.entity.SecretToken;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonCreateAlbumParser extends JsonBaseParser<Album> {
    private final static String FIELD_ALBUM = "album";
    private final static String FIELD_ALBUMS = "albums";
    private final static String FIELD_BLOCKS = "blocks";
    private final static String FIELD_ERROR = "error";
    private final static String FIELD_FORM_BUILDER = "formBuilder";

    @Override
    protected Album mapResponseToObject(JSONObject object) throws JSONException, ApiErrorException {
        if (object.has(FIELD_ALBUM)) {
            JsonAlbumParser albumParser = new JsonAlbumParser();
            return albumParser.parse(object.getString(FIELD_ALBUM));
        }
        String message = object.getJSONObject(FIELD_FORM_BUILDER)
                .getJSONArray(FIELD_BLOCKS)
                .getJSONObject(0)
                .getString(FIELD_ERROR);

        throw new ApiErrorException(0, new Throwable(message));
    }
}
