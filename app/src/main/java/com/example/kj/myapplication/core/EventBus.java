package com.example.kj.myapplication.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {

    private class Subscription{
        private Callback mCallback;
        private String mEvent;

        public Subscription(String event, Callback callback) {
            mCallback = callback;
            mEvent = event;
        }

        public Callback getCallback() {
            return mCallback;
        }

        public String getEvent() {
            return mEvent;
        }
    }


    private ConcurrentHashMap<Integer, Subscription> mSubscriptions = new ConcurrentHashMap<>();
    private int idCounter = 1;

    public int subscribe(String event, Callback callback) {
        int id = idCounter++;
        mSubscriptions.put(
                id,
                new Subscription(event, callback)
        );

        return id;
    }

    public void unsubcribe(int identity) {
        if (mSubscriptions.containsKey(identity)) {
            mSubscriptions.remove(identity);
        }
    }

    public void dispatch(String event, Object responseObject) {
        for (Map.Entry<Integer, Subscription> subscriptionEntry: mSubscriptions.entrySet()) {
            Subscription subscription = subscriptionEntry.getValue();
            if (event.equals(subscription.getEvent())) {
                subscription.getCallback().execute(responseObject);
            }
        }
    }
}
