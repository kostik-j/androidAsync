package com.example.kj.myapplication.core.MVP;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kj.myapplication.R;
import com.example.kj.myapplication.core.network.NetworkUtils;
import com.example.kj.myapplication.data.api.ApiErrorCallback;
import com.example.kj.myapplication.data.api.ApiErrorHelper;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.ui.splash.SplashActivity;

import java.util.ArrayList;

public class BasePresenter<V extends IMvpView> extends ApiErrorCallback {

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

        NetworkUtils utils = new NetworkUtils(getView().getViewContext());
        if (!utils.hasConnection()) {
            getView().showError(getView().getViewContext().getString(R.string.network_is_unreachable));
        }

        regSubscribe(getRequestManager().onApiError(this));
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

    @Override
    public void onApiError(ApiError apiError) {
        if (!isViewAttached()) {
            return;
        }
        if (ApiErrorHelper.isAuthError(apiError)) {
            Intent intent = new Intent(getView().getViewContext(), SplashActivity.class);
            getView().getViewContext().startActivity(intent);
        }

        String errorMsg = apiError.getDetailMessage();
        if (errorMsg.isEmpty()) {
            errorMsg = getView().getViewContext().getString(R.string.error) +
                    ": #" + apiError.getErrorCode();
        }

        getView().showError(errorMsg);
    }

    /**
     * Сохраняем id калбека, чтобы отписаться при детаче вьюхи
     * @param id идентификатор калбека
     */
    protected void regSubscribe(int id) {
        mCallbackIds.add(id);
    }
}
