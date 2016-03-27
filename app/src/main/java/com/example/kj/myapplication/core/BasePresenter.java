package com.example.kj.myapplication.core;

import com.example.kj.myapplication.data.api.ApiRequestManager;

import java.util.ArrayList;

public class BasePresenter<V> {
    private V mView;
    private ArrayList<Integer> mCallbackIds = new ArrayList<>();
    private ApiRequestManager mRequestManager;

    public BasePresenter(ApiRequestManager requestManager) {
        mRequestManager = requestManager;
    }

    protected ApiRequestManager getRequestManager() {
        return mRequestManager;
    }

    public void attachView(V view) {
        mView = view;
        init();
    }

    public boolean isViewAttached() {
        return getView() != null;
    }

    /**
     * В этом методи инициализируем все подписки
     */
    protected void init() {}

    public V getView() {
        return mView;
    }

    public void detachView() {
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
