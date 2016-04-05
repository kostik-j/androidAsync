package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.entity.ContactCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonContactsParser extends JsonBaseParser<ContactCollection> {
    final String FIELD_COUNT = "count";
    final String FIELD_CONTACTS = "contacts";
    final String FIELD_MESSAGES = "messages";
    final String FIELD_NAME = "contactName";
    final String FIELD_ANKETA = "anketa";
    final String FIELD_ANKETA_ID = "id";
    final String FIELD_ANKETA_AGE = "age";
    final String FIELD_ANKETA_PHOTO = "smallPhotoUrl";
    private int mOffset = 0;
    private int mLimit = 0;

    public JsonContactsParser(int offset, int limit) {
        mLimit = limit;
        mOffset = offset;
    }

    @Override
    protected ContactCollection mapResponseToObject(JSONObject object) throws JSONException {
        int totalCount = object.getInt(FIELD_COUNT);
        ArrayList<Contact> contacts = new ArrayList<>();
        JSONArray contactsArray = object.getJSONArray(FIELD_CONTACTS);
        for (int i = 0; i < contactsArray.length(); i++) {
            try {
                contacts.add(getContact(contactsArray.getJSONObject(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ContactCollection(totalCount, mLimit, mOffset, contacts);
    }

    private Contact getContact(JSONObject object) throws JSONException {
        String name = object.getString(FIELD_NAME);
        int messageCount = object.getInt(FIELD_MESSAGES);
        JSONObject anketa = object.getJSONObject(FIELD_ANKETA);
        long id = anketa.getLong(FIELD_ANKETA_ID);
        int age = anketa.has(FIELD_ANKETA_AGE) ? anketa.getInt(FIELD_ANKETA_AGE) : 0;

        String photo = "";
        if (anketa.has(FIELD_ANKETA_PHOTO)) {
            try {
                photo = anketa.getString(FIELD_ANKETA_PHOTO);
                new URL(photo);

            } catch (MalformedURLException malformedURLException) {
                photo = "";
            }
        }

        return new Contact(id, name, age, messageCount, photo);
    }
}
