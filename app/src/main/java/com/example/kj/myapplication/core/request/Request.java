package com.example.kj.myapplication.core.request;

public interface Request<T> {
    T loadData();
    String getName();
}
