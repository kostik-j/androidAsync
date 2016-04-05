package com.example.kj.myapplication.ui.contacts;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kj.myapplication.MyApplication;
import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.entity.ContactCollection;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity
        implements IContactsView, ContactsAdapter.Listener {

    private Toolbar mToolbar;
    private ContactsPresenter mPresenter;

    private RecyclerView mRecyclerView;
    private ContactsAdapter mContactsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MyApplication app = (MyApplication)getApplication();
        mPresenter = (ContactsPresenter)app.getPresenter(ContactsPresenter.class.getSimpleName());
        mPresenter.attachView(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mContactsAdapter = new ContactsAdapter(this, this);
        mRecyclerView.setAdapter(mContactsAdapter);
    }

    @Override
    public void onReachedFloor() {
        mPresenter.needMoreContacts(mContactsAdapter.getItemCount());
    }

    @Override
    public void onShowDetailContact(Contact contact) {
        mPresenter.onShowDetailContact(contact);
    }

    @Override
    public void appendContacts(ArrayList<Contact> contacts) {
        mContactsAdapter.addItems(contacts);
    }

    public void setTitle(int count) {
        mToolbar.setTitle(
            getResources().getQuantityString(R.plurals.contacts, count, count)
        );
    }

    @Override
    public void showContacts(ArrayList<Contact> contacts) {
        mContactsAdapter.replaceItems(contacts);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mPresenter.isViewAttached()) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    @Override
    public void showProgress() {
        findViewById(R.id.layout_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.rvContacts).setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.layout_progress).setVisibility(View.GONE);
        findViewById(R.id.rvContacts).setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
