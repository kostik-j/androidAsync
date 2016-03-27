package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.Contact;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class AlbumsRequest extends Request<ArrayList<Album>> {

    private MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    private NetworkRequest networkRequest = new NetworkRequest();
    private long mAnketaId;

    public AlbumsRequest(long anketaId, JsonBaseParser<ArrayList<Album>> jsonBaseParser) {
        setParser(jsonBaseParser);
        mAnketaId = anketaId;
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            url = new URL(mMambaUrlBuilder.getAbums(mAnketaId).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return networkRequest.makeGetRequest(url);
    }
}

