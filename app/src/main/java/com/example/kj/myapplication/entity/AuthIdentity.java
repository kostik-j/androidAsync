package com.example.kj.myapplication.entity;

public class AuthIdentity {
    private String mLogin;
    private String mPassword;

    public AuthIdentity(String mLogin, String mPassword) {
        this.mLogin = mLogin;
        this.mPassword = mPassword;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getPassword() {
        return mPassword;
    }

    public boolean equals(AuthIdentity authIdentity) {
        return getLogin().equals(authIdentity.getLogin())
                && getPassword().equals(authIdentity.getPassword());
    }
}
