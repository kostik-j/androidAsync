package com.example.kj.myapplication.ui.anketa;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kj.myapplication.MyApplication;
import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Anketa;
import com.squareup.picasso.Picasso;

public class AnketaActivity extends AppCompatActivity
    implements IAnketaView, View.OnClickListener {

    final String LOG_TAG = AnketaActivity.class.getSimpleName();

    private AnketaPresenter mPresenter;
    private Toolbar mToolbar;
    private TextView mTvInterests;
    private TextView mTvHelloText;
    private ImageView mIvPhoto;
    private Button mBtnContacts;
    private Button mBtnAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvInterests = (TextView)findViewById(R.id.tvInterests);
        mTvHelloText = (TextView)findViewById(R.id.tvHelloText);
        mIvPhoto = (ImageView)findViewById(R.id.imageView);
        mBtnAlbums = (Button)findViewById(R.id.btnContacts);
        mBtnContacts = (Button)findViewById(R.id.btnAlbums);
        mBtnAlbums.setOnClickListener(this);
        mBtnContacts.setOnClickListener(this);

        MyApplication app = (MyApplication)getApplication();
        mPresenter = (AnketaPresenter)app.getPresenter(AnketaPresenter.class.getSimpleName());
        mPresenter.attachView(this);
        mPresenter.initByIntent(getIntent());

        setSupportActionBar(mToolbar);
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
    public void showAnketa(Anketa anketa, boolean isOwnAnketa) {
        mToolbar.setTitle(
            String.format(
                "%s, %s %s",
                    anketa.getName(),
                getResources().getQuantityString(R.plurals.age, anketa.getAge(), anketa.getAge()),
                isOwnAnketa ? "(" + getString(R.string.anketa_its_you) + ")" : ""
            )
        );
        mTvHelloText.setText(anketa.getHelloText());
        mTvInterests.setText(TextUtils.join(", ", anketa.getInterests()));
        if (!anketa.getPhoto().isEmpty()) {
            Picasso.with(this).load(anketa.getPhoto()).into(mIvPhoto);
        } else {
            mIvPhoto.setImageResource(R.drawable.no_photo_man);
        }
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
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showButtons() {
        mBtnContacts.setVisibility(View.VISIBLE);
        mBtnAlbums.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAlbums) {
            mPresenter.onAlbumsClick();
        }

        if (v.getId() == R.id.btnContacts) {
            mPresenter.onContactsClick();
        }
    }
}
