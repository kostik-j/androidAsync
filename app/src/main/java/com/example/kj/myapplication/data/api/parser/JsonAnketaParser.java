package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.entity.Anketa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonAnketaParser extends JsonBaseParser<Anketa> {
    final String FIELD_ID = "id";
    final String FIELD_NAME = "name";
    final String FIELD_AGE = "age";
    final String FIELD_PHOTO = "photo";
    final String FIELD_PHOTO_SIZE = "hugePhotoUrl";
    final String FIELD_INTERESTS = "interests";
    final String FIELD_INTERESTS_COUNT = "count";
    final String FIELD_INTERESTS_ITEMS = "items";
    final String FIELD_INTERESTS_TITLE = "title";
    final String FIELD_ABOUTME = "aboutmeBlock";
    final String FIELD_ABOUTME_FIELDS = "fields";
    final String FIELD_ABOUTME_KEY = "key";
    final String FIELD_ABOUTME_KEYE_VALUE = "aboutme";
    final String FIELD_ABOUTME_VALUE = "value";

    @Override
    protected Anketa mapResponseToObject(JSONObject object) throws JSONException {
        JSONObject anketa = object.getJSONObject("anketa");
        long id = anketa.getLong(FIELD_ID);
        String name = anketa.getString(FIELD_NAME);
        int age = anketa.getInt(FIELD_AGE);
        String photo = "";
        if (anketa.has(FIELD_PHOTO) && !anketa.isNull(FIELD_PHOTO)) {
            JSONObject photoObject = anketa.getJSONObject(FIELD_PHOTO);
            photo = photoObject.getString(FIELD_PHOTO_SIZE);
        }
        String[] interests = null;
        if (anketa.has(FIELD_INTERESTS) && !anketa.isNull(FIELD_INTERESTS)) {
            JSONObject interestsObject = anketa.getJSONObject(FIELD_INTERESTS);
            int iCount = interestsObject.getInt(FIELD_INTERESTS_COUNT);
            interests = new String[iCount];
            if (iCount > 0) {
                JSONArray interestsArray =
                        interestsObject.getJSONArray(FIELD_INTERESTS_ITEMS);
                for (int i = 0; i < iCount; i++) {
                    interests[i] = interestsArray.getJSONObject(i)
                            .getString(FIELD_INTERESTS_TITLE);
                }
            }
        }

        String helloText = "";
        if (anketa.has(FIELD_ABOUTME) && !anketa.isNull(FIELD_ABOUTME)) {
            JSONObject aboutmeBlock = anketa.getJSONObject(FIELD_ABOUTME);
            JSONArray fields = aboutmeBlock.getJSONArray(FIELD_ABOUTME_FIELDS);
            for (int i = 0; i < fields.length(); i++) {

                JSONObject item = fields.getJSONObject(i);
                if (item.getString(FIELD_ABOUTME_KEY).equals(FIELD_ABOUTME_KEYE_VALUE)) {
                    helloText = item.getString(FIELD_ABOUTME_VALUE);
                    break;
                }
            }
        }
        return new Anketa(id, name, photo, interests, age, helloText);
    }
}
