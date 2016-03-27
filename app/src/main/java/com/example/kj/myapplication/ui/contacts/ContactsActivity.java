package com.example.kj.myapplication.ui.contacts;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kj.myapplication.MyApplication;
import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Contact;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity
        implements IContactsView, AdapterView.OnItemClickListener {

    private ContactsPresenter mPresenter;
    private ContactsAdapter mContactsAdapter;
    private Toolbar mToolbar;

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
        mContactsAdapter = new ContactsAdapter(this);
        ListView lv = (ListView) findViewById(R.id.lvContacts);
        lv.setAdapter(mContactsAdapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void showContacts(ArrayList<Contact> contacts) {
        mContactsAdapter.clear();
        mContactsAdapter.addAll(contacts);
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
        findViewById(R.id.content).setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.layout_progress).setVisibility(View.GONE);
        findViewById(R.id.content).setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact)parent.getItemAtPosition(position);
        mPresenter.onContactClick(contact);
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
