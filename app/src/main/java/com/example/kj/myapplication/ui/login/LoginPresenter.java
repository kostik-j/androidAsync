package com.example.kj.myapplication.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.ui.anketa.AnketaActivity;

final public class LoginPresenter extends BasePresenter<ILoginView> {

    public LoginPresenter(ApiRequestManager requestManager) {
        super(requestManager);
    }

    @Override
    protected void init() {
        regSubscribe(
            getRequestManager().onAuth(new Callback<AuthData>() {
                @Override
                public void execute(AuthData result) {
                    getView().hideProgress();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("mamba", "anketa", ""));
                    intent.putExtra("anketaId", result.getAnketaId());
                    getView().close();
                    getView().getViewContext().startActivity(intent);
                }
            })
        );

        regSubscribe(
            getRequestManager().onApiError(new Callback<ApiError>() {
                @Override
                public void execute(ApiError result) {
                String message = result.getDetailMessage();
                Toast.makeText(
                    getView().getViewContext(),
                    message.isEmpty() ? "Ошибка: " + String.valueOf(result.getErrorCode()) : message,
                    Toast.LENGTH_SHORT
                ).show();
                getView().hideProgress();
                }
            })
        );
    }

    public void login(String login, String password) {
        if (isViewAttached()) {
            getView().showProgress();
            getRequestManager().auth(new AuthIdentity(login, password));
        }
    }
}
