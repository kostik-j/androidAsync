package com.example.kj.myapplication.ui.contacts;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.ui.anketa.AnketaActivity;

import java.util.ArrayList;

final public class ContactsPresenter extends BasePresenter<IContactsView> {

    public ContactsPresenter(ApiRequestManager requestManager) {
        super(requestManager);
    }


    @Override
    protected void init() {
        regSubscribe(
            getRequestManager().onGetContacts(new Callback<ArrayList<Contact>>() {
                @Override
                public void execute(ArrayList<Contact> result) {
                if (isViewAttached()) {
                    getView().showContacts(result);
                    getView().hideProgress();
                }
                }
        })
        );
        getView().showProgress();
        getRequestManager().getContacts();

        regSubscribe(
                getRequestManager().onApiError(new Callback<ApiError>() {
                    @Override
                    public void execute(ApiError result) {
                        Log.d("asd", "asdasd");
                    }
                })
        );
    }

    public void onContactClick(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("mamba", "anketa", ""));
        intent.putExtra("anketaId", contact.getId());
        getView().getViewContext().startActivity(intent);
    }
}
