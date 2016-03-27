package com.example.kj.myapplication.ui.albums.list;

import android.util.Log;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.ApiError;

import java.util.ArrayList;

final public class AlbumsPresenter extends BasePresenter<IAlbumsView> {

    private IPreferenceProvider mPreferenceProvider;

    public AlbumsPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    @Override
    protected void init() {
        regSubscribe(
                getRequestManager().onGetAlbums(new Callback<ArrayList<Album>>() {
                    @Override
                    public void execute(ArrayList<Album> result) {
                        if (isViewAttached()) {
                            getView().showAlbums(result);
                            getView().hideProgress();
                        }
                    }
                })
        );
        getView().showProgress();
        getRequestManager().getAlbums(mPreferenceProvider.getAnketaId());

        regSubscribe(
                getRequestManager().onApiError(new Callback<ApiError>() {
                    @Override
                    public void execute(ApiError result) {
                        Log.d("asd", "asdasd");
                    }
                })
        );
    }
}
