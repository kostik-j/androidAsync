package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.data.api.parser.Parser;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class SecretAuthRequest extends Request<AuthData> {

    SecretToken mSecretToken;
    MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    NetworkRequest networkRequest = new NetworkRequest();

    public SecretAuthRequest(SecretToken secretToken, Parser<AuthData> parser) {
        mSecretToken = secretToken;
        setParser(parser);
    }

    @Override
    public String getName() {
        return "SecretAuthRequest";
    }

    private JSONObject getPostJsonData(SecretToken authIdentity) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("authSecret", mSecretToken.getSecret());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }

    @Override
    protected String getData() {
        return networkRequest.makePostRequest(
                mMambaUrlBuilder.getSecretLoginUrl(),
                getPostJsonData(mSecretToken)
        );
    }
}

