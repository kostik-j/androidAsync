package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.data.api.ApiErrorException;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class JsonBaseParser<T> {
    final String FIELD_IS_AUTH = "isAuth";
    final String FIELD_ERRORS = "errors";
    final String FIELD_INTERNAL = "internal";
    final String FIELD_ERROR_CODE = "errorCode";

    public T parse(String str) throws ApiErrorException {
        int errorCode = -1;
        String message = "";
        try {
            JSONObject jsonObject = new JSONObject(str);
            boolean isAuth = jsonObject.getBoolean(FIELD_IS_AUTH);
            if (!jsonObject.has(FIELD_ERROR_CODE) && isAuth) {
                return mapResponseToObject(jsonObject);
            }

            if (jsonObject.has(FIELD_ERRORS)) {
                JSONObject errObject = jsonObject.getJSONObject(FIELD_ERRORS);
                if (errObject.has(FIELD_INTERNAL)) {
                    message = errObject.getString(FIELD_INTERNAL);
                }
            }

            // в лббой не понятной ситуации кидаем на авторизацию
            errorCode = isAuth ? jsonObject.getInt(FIELD_ERROR_CODE) : 31;
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
