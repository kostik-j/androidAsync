package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPassAuthRequest extends Request<AuthData> {

    AuthIdentity mAuthIdentity;
    MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();

    public LoginPassAuthRequest(AuthIdentity authIdentity, JsonBaseParser<AuthData> jsonBaseParser) {
        mAuthIdentity = authIdentity;
        setParser(jsonBaseParser);
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
        return getNetworkRequest().makePostRequest(
                mMambaUrlBuilder.getLoginUrl(),
                getPostJsonData(mAuthIdentity)
        );
    }
}

