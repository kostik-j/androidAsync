package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.entity.LogicException;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonAuthParser extends JsonBaseParser<AuthData> {
    final String FIELD_IS_AUTH = "isAuth";
    final String FIELD_AUTH_SECRET = "authSecret";
    final String FIELD_PROFILE = "profile";
    final String FIELD_ANKETA_ID = "id";
    final String FIELD_NAME = "name";
    final String FIELD_LOGIN = "login";

    @Override
    protected AuthData mapResponseToObject(JSONObject object) throws JSONException {
        try {
            boolean isAuth = object.getBoolean(FIELD_IS_AUTH);
            if (isAuth) {
                String secretStr = object.getString(FIELD_AUTH_SECRET);
                SecretToken secret = new SecretToken(secretStr);
                JSONObject profile = object.getJSONObject(FIELD_PROFILE);
                long anketaId = profile.getLong(FIELD_ANKETA_ID);
                String name = profile.getString(FIELD_NAME);
                String login = profile.getString(FIELD_LOGIN);
                return new AuthData(anketaId, name, login, secret);
            }
        } catch (LogicException e) {
            e.printStackTrace();
        }

        return null;
    }
}
