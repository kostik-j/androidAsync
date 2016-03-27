package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;
import com.example.kj.myapplication.entity.Anketa;

import java.net.MalformedURLException;
import java.net.URL;


public class AnketaRequest extends Request<Anketa> {

    private MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    private NetworkRequest networkRequest = new NetworkRequest();
    private long mAnketaId;

    public AnketaRequest(long anketaId, JsonBaseParser<Anketa> jsonBaseParser) {
        setParser(jsonBaseParser);
        mAnketaId = anketaId;
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            url = new URL(mMambaUrlBuilder.getAnketa(mAnketaId).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return networkRequest.makeGetRequest(url);
    }
}

