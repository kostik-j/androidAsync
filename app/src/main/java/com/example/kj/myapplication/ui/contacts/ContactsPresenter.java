package com.example.kj.myapplication.ui.contacts;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.kj.myapplication.R;
import com.example.kj.myapplication.core.MVP.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.entity.ContactCollection;

final public class ContactsPresenter extends BasePresenter<IContactsView> {

    final static String LOG_TAG = ContactsPresenter.class.getSimpleName();

    /**
     * количество загруженых страниц
     */
    private int pageLoaded = 0;

    /**
     * размер загружаемых страниц
     */
    private final int pageSize = 20;

    /**
     * общее число контактов, получем припервом запросе
     */
    private int mTotalCount = 0;

    public ContactsPresenter(ApiRequestManager requestManager) {
        super(requestManager);
    }

    @Override
    protected void onViewAttached() {
        regSubscribe(getRequestManager().onGetContacts(new Callback<ContactCollection>() {
            @Override
            public void execute(ContactCollection result) {
                if (!isViewAttached()) {
                    return;
                }
                // страница которая пришла
                int page = (result.getOffset() / pageSize) + 1;
                boolean isFirstPage = page == 1;

                // проверим что нам пришло то что ждали
                if (page != pageLoaded + 1) {
                    return;
                }
                // если это первый запрос контактов
                if (isFirstPage) {
                    getView().showContacts(result.getContacts());
                    getView().setTitle(result.getTotalCount());
                    getView().hideProgress();
                    mTotalCount = result.getTotalCount();
                } else {
                    getView().appendContacts(result.getContacts());
                }
                ++pageLoaded;
            }

        }));
        if (pageLoaded == 0) {
            loadNextPage();
            getView().showProgress();
        }
    }

    private void loadNextPage() {
        // смещение для следующей страницы
        int offset = pageLoaded * pageSize;
        boolean isFirstPage = pageLoaded == 0;

        if (isFirstPage) {
            getView().showProgress();
        }

        Log.d(LOG_TAG, String.format("Start loading offset=%d, limit=%d", offset, pageSize));
        getRequestManager().getContacts(offset, pageSize);
    }

    public void onShowDetailContact(Contact contact) {
        Log.d(LOG_TAG, "show detail contact: " + contact.getId());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("mamba", "anketa", ""));
        intent.putExtra("anketaId", contact.getId());
        getView().getViewContext().startActivity(intent);
    }

    public void needMoreContacts(int currentCount) {
        if (currentCount < mTotalCount) {
            loadNextPage();
        } else {
            Log.d(LOG_TAG, "All contacts loaded");
            String text = getView().getViewContext().getString(R.string.all_contacts_loaded);
            Toast.makeText(getView().getViewContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
