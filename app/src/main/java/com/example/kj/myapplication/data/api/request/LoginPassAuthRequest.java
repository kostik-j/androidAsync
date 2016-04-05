package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.parser.JsonAuthParser;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPassAuthRequest extends Request<AuthData> {

    AuthIdentity mAuthIdentity;

    public LoginPassAuthRequest(AuthIdentity authIdentity) {
        mAuthIdentity = authIdentity;
        setParser(new JsonAuthParser());
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
    public String getEventName() {
        return ApiEvents.REQUEST_AUTH;
    }

    @Override
    public Boolean isPost() {
        return true;
    }

    @Override
    protected String getData() {
        return getNetworkRequest().makePostRequest(
                getUrlBuilder().getLoginUrl(),
                getPostJsonData(mAuthIdentity)
        );
    }
}

