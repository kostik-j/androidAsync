package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.entity.Profile;

public class JsonProfileParser implements Parser<Profile> {

    @Override
    public Profile parse(String string) {
        return new Profile();
    }
}
