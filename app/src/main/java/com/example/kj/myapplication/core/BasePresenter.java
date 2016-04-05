package com.example.kj.myapplication.core;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kj.myapplication.data.api.ApiRequestManager;

import java.util.ArrayList;

public class BasePresenter<V> {
    final static String LOG_TAG = BasePresenter.class.getSimpleName();

    private V mView;
    private ArrayList<Integer> mCallbackIds = new ArrayList<>();
    private ApiRequestManager mRequestManager;

    public BasePresenter(ApiRequestManager requestManager) {
        mRequestManager = requestManager;
        init();
    }

    protected ApiRequestManager getRequestManager() {
        return mRequestManager;
    }

    public void attachView(@NonNull V view) {
        Log.d(LOG_TAG, "Attach view: " + view.getClass().getSimpleName());
        mView = view;
        onViewAttached();
    }

    public boolean isViewAttached() {
        return getView() != null;
    }
    /**
     * В этом методи инициализируем все подписки
     */
    protected void onViewAttached() {}

    /**
     * метод вызывает при создании презентера
     */
    protected void init() {}

    public V getView() {
        return mView;
    }

    public void detachView() {
        Log.d(LOG_TAG, "Detach view: " + getView().getClass().getSimpleName());
        for (int id: mCallbackIds) {
            mRequestManager.unbindCallback(id);
        }
        mView = null;
    }

    /**
     * Сохраняем id калбека, чтобы отписаться при детаче вьюхи
     * @param id идентификатор калбека
     */
    protected void regSubscribe(int id) {
        mCallbackIds.add(id);
    }
}
