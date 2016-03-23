package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.parser.Parser;
import com.example.kj.myapplication.entity.AuthData;

public abstract class Request<T> {
    protected AuthData authData;

    /**
     * Парсер разбирает json и возвращает модель, либо ошибку
     */
    private Parser<T> mParser;

    /**
     * Загружаем данные из сети
     * @return T
     */
    protected abstract String getData();

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
