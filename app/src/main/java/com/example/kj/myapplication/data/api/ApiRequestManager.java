package com.example.kj.myapplication.data.api;

import android.os.Handler;
import android.util.Log;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.core.network.NetworkRequest;
import com.example.kj.myapplication.core.network.NetworkUtils;
import com.example.kj.myapplication.core.network.RequestThread;
import com.example.kj.myapplication.data.api.request.AlbumsRequest;
import com.example.kj.myapplication.data.api.request.ContactsRequest;
import com.example.kj.myapplication.data.api.request.CreateAlbumRequest;
import com.example.kj.myapplication.data.api.request.TryAuthRequest;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.data.api.request.LoginPassAuthRequest;
import com.example.kj.myapplication.data.api.request.AnketaRequest;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.ContactCollection;
import com.example.kj.myapplication.entity.SecretToken;

import java.util.ArrayList;

public class ApiRequestManager {
    private final static String LOG_TAG = ApiRequestManager.class.getSimpleName();

    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;
    private IPreferenceProvider mPreferenceProvider;
    private NetworkUtils mNetworkUtils;

    public ApiRequestManager(
            EventDispatcher eventDispatcher,
            Handler mainHandler,
            IPreferenceProvider preferenceProvider,
            NetworkUtils networkUtils
    ) {
        mMainHandler = mainHandler;
        mEventDispatcher = eventDispatcher;
        mPreferenceProvider = preferenceProvider;
        mNetworkUtils = networkUtils;

        mEventDispatcher.subscribe(ApiEvents.ERROR_UPDATE_COOKIE, new Callback<ArrayList<String>>() {
            @Override
            public void execute(ArrayList<String> result) {
                Log.d(LOG_TAG, "Update cookie");
                mPreferenceProvider.setCookie(result);
            }
        });
    }

    private void runRequest(Request request, String tag) {
        if (mNetworkUtils.hasConnection()) {
            request.setNetworkRequest(new NetworkRequest());
            request.setCookie(mPreferenceProvider.getCookie());
            request.setTag(tag);

            RequestThread thread = new RequestThread(request, mEventDispatcher, mMainHandler);
            thread.start();
        }
    }

    public int onAuth(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(ApiEvents.REQUEST_AUTH, callback);
    }

    public int onGetAnketa(Callback<Anketa> callback) {
        return mEventDispatcher.subscribe(ApiEvents.REQUEST_ANKETA, callback);
    }

    public int onGetContacts(Callback<ContactCollection> callback) {
        return mEventDispatcher.subscribe(ApiEvents.REQUEST_CONTACTS, callback);
    }

    public int onGetAlbums(Callback<ArrayList<Album>> callback) {
        return mEventDispatcher.subscribe(ApiEvents.REQUEST_ALBUMS, callback);
    }

    public int onNewAlbum(Callback<Album> callback) {
        return mEventDispatcher.subscribe(ApiEvents.REQUEST_CREATE_ALBUM, callback);
    }

    public int onApiError(Callback<ApiError> callback) {
        return mEventDispatcher.subscribe(ApiEvents.ERROR_EVENT, callback);
    }

    public void unbindCallback(int id) {
        mEventDispatcher.unsubcribe(id);
    }


    /**
     * Пробуем авторизоваться по сохраненным кукам и по секрету
     */
    public ApiRequestManager tryAuth() {
        return tryAuth("");
    }

    public ApiRequestManager tryAuth(String tag) {
        SecretToken token = mPreferenceProvider.getSecretToken();
        runRequest(new TryAuthRequest(token), tag);

        return this;
    }

    /**
     * Авторизируемся по паре login/password
     * @param identity identity login/password
     */
    public ApiRequestManager auth(AuthIdentity identity) {
        return auth(identity, "") ;
    }

    public ApiRequestManager auth(AuthIdentity authIdentity, String tag) {
        runRequest(new LoginPassAuthRequest(authIdentity), tag);

        return this;
    }

    /**
     * Загрузить Анкетц пользователя
     * @param anketaId мамба id
     */
    public ApiRequestManager getAnketa(long anketaId) {
        return getAnketa(anketaId, "");
    }

    public ApiRequestManager getAnketa(long anketaId, String tag) {
        runRequest(new AnketaRequest(anketaId), tag);

        return this;
    }

    /**
     * Загрузить список контактов
     * @param offset смещение от начала списка
     */
    public ApiRequestManager getContacts(int offset) {
        return getContacts(offset, "");
    }

    public ApiRequestManager getContacts(int offset, String tag) {
        runRequest(new ContactsRequest(offset), tag);

        return this;
    }

    /**
     * Загрузить список контактов
     * @param offset смещение от начала списка
     * @param limit количество загружаемых контактов
     */
    public ApiRequestManager getContacts(int offset, int limit) {
        return getContacts(offset, limit, "");
    }

    public ApiRequestManager getContacts(int offset, int limit, String tag) {
        runRequest(new ContactsRequest(offset, limit), tag);

        return this;
    }

    /**
     * Загрузить все альбомы пользователя
     * @param anketaId мамба id
     */
    public ApiRequestManager getAlbums(long anketaId) {
        return getAlbums(anketaId, "");
    }

    public ApiRequestManager getAlbums(long anketaId, String tag) {
        runRequest(new AlbumsRequest(anketaId), tag);

        return this;
    }

    /**
     * Создаем новый альбом
     * @param name название нового альбома
     */
    public ApiRequestManager createAlbum(String name) {
        return createAlbum(name, "");
    }

    public ApiRequestManager createAlbum(String name, String tag) {
        runRequest(new CreateAlbumRequest(name), tag);

        return this;
    }
}