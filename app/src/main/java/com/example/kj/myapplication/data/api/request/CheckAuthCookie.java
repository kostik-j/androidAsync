package com.example.kj.myapplication.data.api.request;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.entity.SecretToken;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * проверяем сохраненные куки, если куки устарели авторизуемся по secret
 * если ничего не помогло выдаем ошибку авторизации 31
 */
public class CheckAuthCookie extends Request<Boolean>{
    private SecretToken mSeretToken;

    public CheckAuthCookie(SecretToken seretToken) {
        mSeretToken = seretToken;
    }

    @Override
    protected String getData() {
        URL url = null;
        try {
            url = new URL(mMambaUrlBuilder.getAbums(mAnketaId).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return getNetworkRequest().makeGetRequest(url);
    }

    @Override
    public Boolean loadData() throws ApiErrorException {
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
