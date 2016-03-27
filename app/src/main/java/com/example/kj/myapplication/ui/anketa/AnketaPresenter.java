package com.example.kj.myapplication.ui.anketa;

import android.content.Intent;
import android.util.Log;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.ui.albums.list.AlbumsActivity;
import com.example.kj.myapplication.ui.contacts.ContactsActivity;

final public class AnketaPresenter extends BasePresenter<IAnketaView> {
    private IPreferenceProvider mPreferenceProvider;

    public AnketaPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    public void loadAnketa(long id) {
        getView().showProgress();
        getRequestManager().getAnketa(id);
    }

    public void initByIntent(Intent intent) {
        long anketaId = intent.getLongExtra("anketaId", 0);
        if (anketaId > 0) {
            loadAnketa(anketaId);
        }
    }

    @Override
    protected void init() {
        regSubscribe(
            getRequestManager().onGetAnketa(new Callback<Anketa>() {
                @Override
                public void execute(Anketa result) {
                if (isViewAttached()) {
                    getView().hideProgress();
                    boolean isOwnAnketa = mPreferenceProvider.getAnketaId() == result.getId();
                    getView().showAnketa(result, isOwnAnketa);
                    if (isOwnAnketa) {
                        getView().showButtons();
                    } else {
                        getView().showBackButton();
                    }
                }
                }
            })
        );

        regSubscribe(
                getRequestManager().onApiError(new Callback<ApiError>() {
                    @Override
                    public void execute(ApiError result) {
                        Log.d("asd", "asdasd");
                    }
                })
        );
    }

    public void onAlbumsClick() {
        Intent intent = new Intent(getView().getViewContext(), AlbumsActivity.class);
        getView().getViewContext().startActivity(intent);
    }

    public void onContactsClick() {
        Intent intent = new Intent(getView().getViewContext(), ContactsActivity.class);
        getView().getViewContext().startActivity(intent);
    }
}
