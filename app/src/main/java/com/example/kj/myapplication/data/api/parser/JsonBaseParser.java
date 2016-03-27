package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.data.api.ApiErrorException;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class JsonBaseParser<T> {
    final String FIELD_IS_AUTH = "isAuth";

    final String FIELD_ERROR_CODE = "errorCode";

    public T parse(String str) throws ApiErrorException {
        int errorCode = -1;
        try {
            JSONObject jsonObject = new JSONObject(str);
            if (!jsonObject.has(FIELD_ERROR_CODE)) {
                return mapResponseToObject(jsonObject);
            }

            errorCode = jsonObject.getInt(FIELD_ERROR_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ApiErrorException(errorCode);
    }

    /**
     * Мапим Json объект в value object, при любой непонятке кидаем exception
     * @param object
     * @return T
     * @throws JSONException
     */
    abstract protected T mapResponseToObject(JSONObject object) throws JSONException;
}
