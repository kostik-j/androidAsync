package com.example.kj.myapplication.entity;

public class AuthData {
    private long mAnketaId;
    private String mName;
    private String mLogin;
    private SidToken mSid;
    private SecretToken mSecret;

    public AuthData(long anketaId, String name, String login, SidToken sid, SecretToken secret) {
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

    public SidToken getSid() {
        return mSid;
    }

    public SecretToken getSecret() {
        return mSecret;
    }
}
