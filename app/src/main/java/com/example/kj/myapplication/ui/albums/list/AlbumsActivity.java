package com.example.kj.myapplication.ui.albums.list;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.kj.myapplication.MyApplication;
import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Album;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity
        implements IAlbumsView {

    private AlbumsPresenter mPresenter;
    private AlbumsAdapter mAdapter;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
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
        mPresenter = (AlbumsPresenter)app.getPresenter(AlbumsPresenter.class.getSimpleName());
        mPresenter.attachView(this);

        mAdapter = new AlbumsAdapter(this);
        ListView lv = (ListView) findViewById(R.id.lvAlbums);
        lv.setAdapter(mAdapter);
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
    public void showAlbums(ArrayList<Album> albums) {
        mAdapter.clear();
        mAdapter.addAll(albums);

        mToolbar.setTitle(
            getResources().getQuantityString(R.plurals.albums, albums.size(), albums.size())
        );

    }

    @Override
    public void showProgress() {
        findViewById(R.id.layout_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.content).setVisibility(View.GONE);
        mFloatingActionButton.setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.layout_progress).setVisibility(View.GONE);
        findViewById(R.id.content).setVisibility(View.VISIBLE);
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);

    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
