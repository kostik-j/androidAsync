package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;

public abstract class Request<T> {

    /**
     * Парсер разбирает json и возвращает модель, либо ошибку
     */
    private JsonBaseParser<T> mJsonBaseParser;

    /**
     * Загружаем данные из сети
     * @return T
     */
    protected abstract String getData();

    public JsonBaseParser<T> getParser() {
        return mJsonBaseParser;
    }

    public void setParser(JsonBaseParser<T> jsonBaseParser) {
        mJsonBaseParser = jsonBaseParser;
    }

    public T loadData() throws ApiErrorException {
        try {
            return getParser().parse(getData());
        } catch (Exception e) {
            // ошибки впи пробрасываем дальше
            if (e instanceof ApiErrorException) {
                throw e;
            }
            e.printStackTrace();
        }
        return null;
    }
}
