package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.entity.AuthData;
import org.json.JSONObject;

public class JsonAuthParser implements Parser<AuthData> {
    final String FIELD_IS_AUTH = "isAuth";
    final String FIELD_AUTH_SECRET = "authSecret";
    final String FIELD_PROFILE = "profile";
    final String FIELD_SID = "sid";
    final String FIELD_ANKETA_ID = "id";
    final String FIELD_NAME = "name";
    final String FIELD_LOGIN = "login";

    @Override
    public AuthData parse(String string) {
        try {
            JSONObject response = new JSONObject(string);
            boolean isAuth = response.getBoolean(FIELD_IS_AUTH);
            if (isAuth) {
                String secretStr = response.getString(FIELD_AUTH_SECRET);
                SecretToken secret = new SecretToken(secretStr);
                String sid = response.getString(FIELD_SID);
                JSONObject profile = response.getJSONObject(FIELD_PROFILE);
                long anketaId = profile.getLong(FIELD_ANKETA_ID);
                String name = profile.getString(FIELD_NAME);
                String login = profile.getString(FIELD_LOGIN);
                return new AuthData(anketaId, name, login, sid, secret);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
