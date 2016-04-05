package com.example.kj.myapplication.ui.anketa;

import android.content.Intent;
import android.widget.Toast;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.ContactCollection;
import com.example.kj.myapplication.ui.albums.AlbumsActivity;
import com.example.kj.myapplication.ui.contacts.ContactsActivity;

import java.util.ArrayList;

final public class AnketaPresenter extends BasePresenter<IAnketaView> {
    private IPreferenceProvider mPreferenceProvider;

    public AnketaPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    private Boolean isOwn(long anketaId) {
        return mPreferenceProvider.getAnketaId() == anketaId;
    }

    public void loadAnketa(long id) {
        getView().showProgress();
        getRequestManager().getAnketa(id);
        if (isOwn(id)) {
            getRequestManager()
                .getAlbums(id)
                .getContacts(0);
        }
    }

    public void initByIntent(Intent intent) {
        long anketaId = intent.getLongExtra("anketaId", 0);
        if (anketaId > 0) {
            loadAnketa(anketaId);
        }
    }

    @Override
    protected void onViewAttached() {
        regSubscribe(
            getRequestManager().onGetAnketa(new Callback<Anketa>() {
                @Override
                public void execute(Anketa result) {
                    if (isViewAttached()) {
                        getView().hideProgress();
                        final boolean isOwnAnketa = isOwn(result.getId());
                        getView().showAnketa(result, isOwnAnketa);
                        if (isOwnAnketa) {
                            getView().showButtonsBlock();
                        } else {
                            getView().showBackButton();
                        }
                    }
                }
            })
        );

        regSubscribe(
            getRequestManager().onGetAlbums(new Callback<ArrayList<Album>>() {
                @Override
                public void execute(ArrayList<Album> result) {
                    if (isViewAttached()) {
                        getView().showAlbumsCount(result.size());
                    }
                }
            })
        );

        regSubscribe(
            getRequestManager().onGetContacts(new Callback<ContactCollection>() {
                @Override
                public void execute(ContactCollection result) {
                    if (isViewAttached()) {
                        getView().showContactsCount(result.getTotalCount());
                    }
                }
            })
        );

        regSubscribe(
                getRequestManager().onApiError(new Callback<ApiError>() {
                    @Override
                    public void execute(ApiError result) {
                        Toast.makeText(getView().getViewContext(), result.getDetailMessage(), Toast.LENGTH_SHORT).show();
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
