package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;
import com.example.kj.myapplication.entity.Contact;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ContactsRequest extends Request<ArrayList<Contact>> {

    private MambaUrlBuilder mMambaUrlBuilder = new MambaUrlBuilder();
    private NetworkRequest networkRequest = new NetworkRequest();

    public ContactsRequest(JsonBaseParser<ArrayList<Contact>> jsonBaseParser) {
        setParser(jsonBaseParser);
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            url = new URL(mMambaUrlBuilder.getAllContacts().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return networkRequest.makeGetRequest(url);
    }
}

