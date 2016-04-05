package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.parser.JsonBaseParser;
import com.example.kj.myapplication.data.api.parser.JsonContactsParser;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.entity.ContactCollection;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;


public class ContactsRequest extends Request<ContactCollection> {

    final private int DEFAULT_LIMIT = 20;

    private int mLimit = 0;

    private int mOffset = 0;

    public ContactsRequest(int offset) {
        init(offset, DEFAULT_LIMIT);
    }

    public ContactsRequest(int offset, int limit) {
        init(offset, limit);
    }

    private void init(int offset, int limit) {
        mOffset = offset;
        mLimit = limit;
        setParser(new JsonContactsParser(mOffset, mLimit));
    }

    @Override
    protected String getData() {
        URL url = getUrlBuilder().getAllContacts();
        JSONObject data = new JSONObject();
        try {
            data.put("limit", mLimit);
            data.put("offset", mOffset);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getNetworkRequest().makeGetRequest(url, data);
    }
}

