package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.data.api.parser.JsonAnketaParser;
import com.example.kj.myapplication.entity.Anketa;

import java.net.MalformedURLException;
import java.net.URL;


public class AnketaRequest extends Request<Anketa> {

    private MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    private long mAnketaId;

    @Override
    public String getEventName() {
        return ApiEvents.REQUEST_ANKETA;
    }

    public AnketaRequest(long anketaId) {
        setParser(new JsonAnketaParser());
        mAnketaId = anketaId;
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            url = new URL(mMambaUrlBuilder.getAnketaUrl(mAnketaId).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return getNetworkRequest().makeGetRequest(url);
    }
}

