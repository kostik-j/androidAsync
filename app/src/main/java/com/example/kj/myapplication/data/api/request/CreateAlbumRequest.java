package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.parser.JsonCreateAlbumParser;
import com.example.kj.myapplication.entity.Album;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAlbumRequest extends Request<Album> {
    private final static String FIELD_ALBUM = "album";
    private final static String FIELD_NAME = "name";
    private final static String FIELD_COMMENT_STATE = "commentState";
    private final static String FIELD_VISIBLE_STATE = "visibleState";
    private final static String FIELD_VISIBLE_STATE_ALL = "all";

    private String mName;

    public CreateAlbumRequest(String name) {
        mName = name;
        setParser(new JsonCreateAlbumParser());
    }

    @Override
    public String getEventName() {
        return ApiEvents.REQUEST_CREATE_ALBUM;
    }

    @Override
    protected String getData() {
        return getNetworkRequest().makePostRequest(
            getUrlBuilder().getCreateAlbumUrl(),
            getPostJsonData(mName)
        );
    }

    private JSONObject getPostJsonData(String name) {
        JSONObject data = new JSONObject();
        JSONObject albumObject = new JSONObject();
        try {
            albumObject.put(FIELD_NAME, name);
            albumObject.put(FIELD_COMMENT_STATE, true);
            albumObject.put(FIELD_VISIBLE_STATE, FIELD_VISIBLE_STATE_ALL);
            data.put(FIELD_ALBUM, albumObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
