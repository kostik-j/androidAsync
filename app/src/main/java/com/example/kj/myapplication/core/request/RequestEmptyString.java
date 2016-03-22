package com.example.kj.myapplication.core.request;

import java.util.Random;

public class RequestEmptyString implements Request<String> {
    @Override
    public String getName() {
        return "RequestEmptyString";
    }

    @Override
    public String loadData() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "asfasdf sadf sdagf asd" + String.valueOf((new Random()).nextInt());
    }
}

