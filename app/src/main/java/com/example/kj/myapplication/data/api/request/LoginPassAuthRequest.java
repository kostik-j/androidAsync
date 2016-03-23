package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.data.api.parser.Parser;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPassAuthRequest extends Request<AuthData> {

    AuthIdentity mAuthIdentity;
    MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    NetworkRequest networkRequest = new NetworkRequest();

    public LoginPassAuthRequest(AuthIdentity authIdentity, Parser<AuthData> parser) {
        mAuthIdentity = authIdentity;
        setParser(parser);
    }

    @Override
    public String getName() {
        return "LoginPassAuthRequest";
    }

    private JSONObject getPostJsonData(AuthIdentity authIdentity) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("login", authIdentity.getLogin());
            postData.put("password", authIdentity.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }

    @Override
    protected String getData() {
        return networkRequest.makePostRequest(
                mMambaUrlBuilder.getLoginUrl(),
                getPostJsonData(mAuthIdentity)
        );
    }
}

