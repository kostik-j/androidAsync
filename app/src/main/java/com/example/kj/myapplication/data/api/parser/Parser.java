package com.example.kj.myapplication.data.api.parser;

import com.example.kj.myapplication.data.api.ApiErrorException;

public interface Parser<T> {
    T parse(String str) throws ApiErrorException;
}
