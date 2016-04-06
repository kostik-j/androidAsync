package com.example.kj.myapplication.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.kj.myapplication.MyApplication;
import com.example.kj.myapplication.R;
import com.example.kj.myapplication.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity
        implements ISplashView {

    SplashPresenter mPresenter;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MyApplication app = (MyApplication)getApplication();
        mPresenter = (SplashPresenter)app.getPresenter(SplashActivity.class.getSimpleName());
        mPresenter.attachView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mPresenter.isViewAttached()) {
            mPresenter.attachView(this);
        }
        mPresenter.onResult(requestCode, resultCode, data);
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
    public void close() {
        finish();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginForm(int id) {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivityForResult(intent, id);
    }
}
