package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.data.api.ApiErrorException;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class JsonBaseParser<T> {
    final String FIELD_IS_AUTH = "isAuth";
    final String FIELD_ERRORS = "errors";
    final String FIELD_INTERNAL = "internal";
    final String FIELD_ERROR_CODE = "errorCode";
    final int AUTH_ERROR_CODE = 31;

    private String getErrorMessage(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has(FIELD_ERRORS)) {
            JSONObject errObject = jsonObject.getJSONObject(FIELD_ERRORS);
            if (errObject.has(FIELD_INTERNAL)) {
                return errObject.getString(FIELD_INTERNAL);
            }
        }
        return "";
    }

    protected Boolean isAuthMustHave = true;

    public T parse(String str) throws ApiErrorException {
        int errorCode = 0;
        boolean isAuth;
        String message = "";
        try {
            JSONObject jsonObject = new JSONObject(str);
            isAuth = jsonObject.getBoolean(FIELD_IS_AUTH) || !isAuthMustHave;
            if (jsonObject.has(FIELD_ERROR_CODE)) {
                errorCode = jsonObject.getInt(FIELD_ERROR_CODE);
            } else {
                errorCode = isAuth ? errorCode : AUTH_ERROR_CODE;
            }

            if (errorCode == 0) {
                return mapResponseToObject(jsonObject);
            }

            message = getErrorMessage(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ApiErrorException(errorCode, new Throwable(message));
    }

    /**
     * Мапим Json объект в value object, при любой непонятке кидаем exception
     * @param object
     * @return T
     * @throws JSONException
     */
    abstract protected T mapResponseToObject(JSONObject object) throws JSONException;
}
