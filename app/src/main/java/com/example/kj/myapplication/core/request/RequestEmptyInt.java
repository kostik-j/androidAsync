package com.example.kj.myapplication.core.request;

import java.util.Random;

public class RequestEmptyInt implements Request<Integer> {
    @Override
    public String getName() {
        return "RequestEmptyInt";
    }

    @Override
    public Integer loadData() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (new Random()).nextInt();
    }
}

