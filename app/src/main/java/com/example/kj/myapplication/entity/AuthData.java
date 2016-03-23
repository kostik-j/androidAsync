package com.example.kj.myapplication.entity;

public class AuthData {
    private long mAnketaId;
    private String mName;
    private String mLogin;
    private String mSid;
    private SecretToken mSecret;

    public AuthData(long anketaId, String name, String login, String sid, SecretToken secret) {
        mAnketaId = anketaId;
        mName = name;
        mLogin = login;
        mSid = sid;
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

    public String getSid() {
        return mSid;
    }

    public SecretToken getSecret() {
        return mSecret;
    }
}
