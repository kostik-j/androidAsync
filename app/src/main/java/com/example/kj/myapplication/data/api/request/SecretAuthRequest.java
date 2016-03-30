package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class SecretAuthRequest extends Request<AuthData> {

    SecretToken mSecretToken;
    MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();

    public SecretAuthRequest(SecretToken secretToken, JsonBaseParser<AuthData> jsonBaseParser) {
        mSecretToken = secretToken;
        setParser(jsonBaseParser);
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
        return getNetworkRequest().makePostRequest(
                mMambaUrlBuilder.getSecretLoginUrl(),
                getPostJsonData(mSecretToken)
        );
    }
}

