package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.parser.Parser;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.SidToken;

public abstract class Request<T> {
    private SidToken mSid;

    /**
     * Парсер разбирает json и возвращает модель, либо ошибку
     */
    private Parser<T> mParser;

    /**
     * Загружаем данные из сети
     * @return T
     */
    protected abstract String getData();

    public SidToken getSid() {
        return mSid;
    }

    public void setSid(SidToken sid) {
        this.mSid = sid;
    }

    /**
     * Идентификатор запроса использется для подписки
     * @return String
     */
    public abstract String getName();

    public Parser<T> getParser() {
        return mParser;
    }

    public void setParser(Parser<T> parser) {
        mParser = parser;
    }

    public T loadData() {
        try {
            return getParser().parse(getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
