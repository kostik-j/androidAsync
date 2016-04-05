package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.parser.JsonAlbumsParser;
import com.example.kj.myapplication.entity.Album;

import java.net.URL;
import java.util.ArrayList;


public class AlbumsRequest extends Request<ArrayList<Album>> {

    private long mAnketaId;

    public AlbumsRequest(long anketaId) {
        setParser(new JsonAlbumsParser());
        mAnketaId = anketaId;
    }

    @Override
    public String getEventName() {
        return ApiEvents.REQUEST_ALBUMS;
    }

    @Override
    protected String getData() {
        URL url = getUrlBuilder().getAlbumsUrl(mAnketaId);

        return getNetworkRequest().makeGetRequest(url);
    }
}

