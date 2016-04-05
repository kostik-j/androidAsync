package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.parser.JsonContactsParser;
import com.example.kj.myapplication.entity.ContactCollection;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


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

    @Override
    public String getEventName() {
        return ApiEvents.REQUEST_CONTACTS;
    }

    private void init(int offset, int limit) {
        mOffset = offset;
        mLimit = limit;
        setParser(new JsonContactsParser(mOffset, mLimit));
    }

    @Override
    protected String getData() {
        URL url = getUrlBuilder().getAllContactsUrl();
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

