package com.example.kj.myapplication.ui.albums.create;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.ApiError;

final public class NewAlbumPresenter extends BasePresenter<INewAlbumView> {

    private IPreferenceProvider mPreferenceProvider;

    public NewAlbumPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    @Override
    protected void init() {
        getRequestManager().getAlbums(mPreferenceProvider.getAnketaId());

        regSubscribe(
            getRequestManager().onApiError(new Callback<ApiError>() {
                @Override
                public void execute(ApiError result) {
                }
            })
        );
    }
}
