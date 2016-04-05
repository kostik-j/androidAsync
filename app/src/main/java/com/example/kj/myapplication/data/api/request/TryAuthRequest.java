package com.example.kj.myapplication.data.api.request;

import android.util.Log;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.data.api.ApiErrorHelper;
import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.parser.JsonAuthParser;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.SecretToken;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * проверяем сохраненные куки, если куки устарели авторизуемся по secret
 * если ничего не помогло выдаем ошибку авторизации 31
 */
public class TryAuthRequest extends Request<AuthData>{
    private final static String LOG_TAG = TryAuthRequest.class.getSimpleName();
    private SecretToken mSecretToken;
    private Boolean mIsPost = false;

    public TryAuthRequest(SecretToken secretToken) {
        mSecretToken = secretToken;
    }

    private AuthData checkCurrentCookie() throws ApiErrorException {
        String data = getNetworkRequest().makeGetRequest(getUrlBuilder().getProfileUrl());
        if (data == null) {
            throw new ApiErrorException(0, new Throwable("Network error"));
        }
        try {
            AuthData authData = (new JsonAuthParser()).parse(data);
            Log.d(LOG_TAG, "Was auth by restored cookies");
            return authData;
        } catch (ApiErrorException e) {
            return null;
        }
    }

    private AuthData authBySecret() throws ApiErrorException {
        if (mSecretToken == null) {
            throw new ApiErrorException(ApiErrorHelper.AUTH_ERROR_CODE);
        }

        String data = getNetworkRequest().makePostRequest(
            getUrlBuilder().getSecretLoginUrl(),
            getPostJsonData(mSecretToken)
        );
        if (data == null) {
            throw new ApiErrorException(0, new Throwable("Network error"));
        }

        try {
            AuthData authData = (new JsonAuthParser()).parse(data);
            Log.d(LOG_TAG, "Was auth by secret");
            return authData;
        } catch (Exception e) {
            // ошибки впи пробрасываем дальше
            if (e instanceof ApiErrorException) {
                throw e;
            }
            e.printStackTrace();
        }
        return null;
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
        return "";
    }

    @Override
    public Boolean isPost() {
        return mIsPost;
    }

    @Override
    public String getEventName() {
        return ApiEvents.REQUEST_AUTH;
    }

    @Override
    public AuthData loadData() throws ApiErrorException {
        AuthData authData = null;
        if (getNetworkRequest().getCookie() != null) {
            authData = checkCurrentCookie();
        }
        if (authData != null) {
            return authData;
        }
        mIsPost = true;
        return authBySecret();
    }
}
