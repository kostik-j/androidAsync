package com.example.kj.myapplication.data.api;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.core.RequestThread;
import com.example.kj.myapplication.data.api.request.AlbumsRequest;
import com.example.kj.myapplication.data.api.request.ContactsRequest;
import com.example.kj.myapplication.data.api.request.CreateAlbumRequest;
import com.example.kj.myapplication.data.api.request.TryAuthRequest;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.data.api.request.LoginPassAuthRequest;
import com.example.kj.myapplication.data.api.request.AnketaRequest;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.ContactCollection;
import com.example.kj.myapplication.entity.SecretToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ApiRequestManager {
    private final static String LOG_TAG = ApiRequestManager.class.getSimpleName();

    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;
    private IPreferenceProvider mPreferenceProvider;

    public ApiRequestManager(
            EventDispatcher eventDispatcher,
            Handler mainHandler,
            IPreferenceProvider preferenceProvider
    ) {
        mMainHandler = mainHandler;
        mEventDispatcher = eventDispatcher;
        mPreferenceProvider = preferenceProvider;

        mEventDispatcher.subscribe(ApiEvents.ERROR_UPDATE_COOKIE, new Callback<ArrayList<String>>() {
            @Override
            public void execute(ArrayList<String> result) {
                Log.d(LOG_TAG, "Update cookie");
                mPreferenceProvider.setCookie(result);
            }
        });
    }

    private RequestThread buildThread(Request request) {
        request.setNetworkRequest(new NetworkRequest());
        request.setCookie(mPreferenceProvider.getCookie());
        return new RequestThread(request, mEventDispatcher, mMainHandler);
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
        SecretToken token = mPreferenceProvider.getSecretToken();
        buildThread(new TryAuthRequest(token)).start();

        return this;
    }

    /**
     * Авторизируемся по паре login/password
     * @param authIdentity identity login/password
     */
    public ApiRequestManager auth(AuthIdentity authIdentity) {
        buildThread(new LoginPassAuthRequest(authIdentity)).start();

        return this;
    }

    /**
     * Загрузить Анкетц пользователя
     * @param anketaId мамба id
     */
    public ApiRequestManager getAnketa(long anketaId) {
        buildThread(new AnketaRequest(anketaId)).start();

        return this;
    }

    /**
     * Загрузить список контактов
     * @param offset смещение от начала списка
     */
    public ApiRequestManager getContacts(int offset) {
        buildThread(new ContactsRequest(offset)).start();

        return this;
    }

    /**
     * Загрузить список контактов
     * @param offset смещение от начала списка
     * @param limit количество загружаемых контактов
     */
    public ApiRequestManager getContacts(int offset, int limit) {
        buildThread(new ContactsRequest(offset, limit)).start();

        return this;
    }

    /**
     * Загрузить все альбомы пользователя
     * @param anketaId мамба id
     */
    public ApiRequestManager getAlbums(long anketaId) {
        buildThread(new AlbumsRequest(anketaId)).start();

        return this;
    }

    /**
     * Создаем новый альбом
     * @param name название нового альбома
     */
    public ApiRequestManager createAlbum(String name) {
        buildThread(new CreateAlbumRequest(name)).start();

        return this;
    }
}
