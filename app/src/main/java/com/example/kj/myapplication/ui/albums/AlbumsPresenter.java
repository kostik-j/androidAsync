package com.example.kj.myapplication.ui.albums;

import com.example.kj.myapplication.R;
import com.example.kj.myapplication.core.MVP.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.ApiError;

import java.util.ArrayList;

final public class AlbumsPresenter extends BasePresenter<IAlbumsView> {

    final String REQ_TAG_NEW_ALBUM = "new";

    private IPreferenceProvider mPreferenceProvider;

    public AlbumsPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    @Override
    protected void onViewAttached() {
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

        regSubscribe(getRequestManager().onApiError(this));
        regSubscribe(
            getRequestManager().onNewAlbum(
                new Callback<Album>() {
                    @Override
                    public void execute(Album result) {
                        getRequestManager().getAlbums(mPreferenceProvider.getAnketaId());
                    }
                }
            )
        );
    }

    /**
     * Обрабатываем ошибки тут
     * @param apiError
     */
    @Override
    public void onApiError(ApiError apiError) {
        if (apiError.getTag().equals(REQ_TAG_NEW_ALBUM)) {
            getView().hideProgress();
            // сообщаем пользователю что он мутант
            String errorMsg = apiError.getDetailMessage();
            if (!apiError.getDetailMessage().isEmpty()) {
                getView().showErrorCreateAlbum(errorMsg);
                return;
            }
        }

        super.onApiError(apiError);
    }

    public void createNewAlbum(String name) {
        getView().showProgress();
        getRequestManager().createAlbum(name, REQ_TAG_NEW_ALBUM);
    }

    public void onAddAlbumClick() {
        getView().showNewAlbumDialog();
    }
}
