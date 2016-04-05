package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.data.api.MambaUrlBuilder;
import com.example.kj.myapplication.data.api.parser.JsonBaseParser;

import java.util.List;

public abstract class Request<T> {
    /**
     * Парсер разбирает json и возвращает модель, либо ошибку
     */
    private JsonBaseParser<T> mJsonBaseParser;
    private MambaUrlBuilder mUrlBuilder = new MambaUrlBuilder();

    private NetworkRequest mNetworkRequest;

    public void setNetworkRequest(NetworkRequest networkRequest) {
        mNetworkRequest = networkRequest;
    }

    protected NetworkRequest getNetworkRequest() {
        return mNetworkRequest;
    }

    /**
     * Загружаем данные из сети
     * @return T
     */
    protected abstract String getData();

    public void setCookie(List<String> cookie) {
        mNetworkRequest.setCookie(cookie);
    }

    public List<String> getCookie() {
        return mNetworkRequest.getCookie();
    }

    public MambaUrlBuilder getUrlBuilder() {
        return mUrlBuilder;
    }

    public JsonBaseParser<T> getParser() {
        return mJsonBaseParser;
    }

    public void setParser(JsonBaseParser<T> jsonBaseParser) {
        mJsonBaseParser = jsonBaseParser;
    }

    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    /**
     * @return Boolean
     */
    public Boolean isPost() {
        return false;
    }

    public T loadData() throws ApiErrorException {
        try {
            String response = getData();
            return getParser().parse(response);
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
