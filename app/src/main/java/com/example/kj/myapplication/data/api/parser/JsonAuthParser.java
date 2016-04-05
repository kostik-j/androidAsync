package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.entity.LogicException;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.entity.AuthData;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonAuthParser extends JsonBaseParser<AuthData> {
    final String FIELD_AUTH_SECRET = "authSecret";
    final String FIELD_PROFILE = "profile";
    final String FIELD_ANKETA_ID = "id";

    @Override
    protected AuthData mapResponseToObject(JSONObject object) throws JSONException {
        try {
            SecretToken secret = null;
            if (object.has(FIELD_AUTH_SECRET)) {
                secret = new SecretToken(object.getString(FIELD_AUTH_SECRET));
            }

            JSONObject profile = object.getJSONObject(FIELD_PROFILE);
            long anketaId = profile.getLong(FIELD_ANKETA_ID);
            return new AuthData(anketaId, secret);
        } catch (LogicException e) {
            e.printStackTrace();
        }

        return null;
    }
}
