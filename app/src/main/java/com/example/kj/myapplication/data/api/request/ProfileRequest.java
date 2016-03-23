package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.data.api.parser.Parser;
import com.example.kj.myapplication.entity.Profile;

import java.net.MalformedURLException;
import java.net.URL;


public class ProfileRequest extends Request<Profile> {

    private MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    private NetworkRequest networkRequest = new NetworkRequest();

    public ProfileRequest(Parser<Profile> parser) {
        setParser(parser);
    }

    @Override
    public String getName() {
        return "ProfileRequest";
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            String sid = getSid() == null ? "" : getSid().toString();
            url = new URL(
                mMambaUrlBuilder.getProfile().toString()
                + "?sid=" + sid + "&lang_id=en&dateType=timestamp"
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return networkRequest.makeGetRequest(url);
    }
}

