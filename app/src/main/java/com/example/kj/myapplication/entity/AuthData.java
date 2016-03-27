package com.example.kj.myapplication.entity;

public class AuthData {
    private long mAnketaId;
    private String mName;
    private String mLogin;
    private SecretToken mSecret;

    public AuthData(long anketaId, String name, String login, SecretToken secret) {
        mAnketaId = anketaId;
        mName = name;
        mLogin = login;
        mSecret = secret;
    }

    public long getAnketaId() {
        return mAnketaId;
    }

    public String getName() {
        return mName;
    }

    public String getLogin() {
        return mLogin;
    }

    public SecretToken getSecret() {
        return mSecret;
    }
}
