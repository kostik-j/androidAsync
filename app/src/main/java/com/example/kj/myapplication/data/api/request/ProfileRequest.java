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
    private String mSid;

    public ProfileRequest(String sid, Parser<Profile> parser) {
        setParser(parser);
        mSid = sid;
    }

    @Override
    public String getName() {
        return "ProfileRequest";
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            url = new URL(
                mMambaUrlBuilder.getProfile().toString()
                + "?sid=" + mSid + "&lang_id=en&dateType=timestamp"
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return networkRequest.makeGetRequest(url);
    }
}

