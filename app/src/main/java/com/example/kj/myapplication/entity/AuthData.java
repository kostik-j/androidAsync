package com.example.kj.myapplication.entity;

public class AuthData {
    private long mAnketaId;
    private SecretToken mSecret;

    public AuthData(long anketaId, SecretToken secret) {
        mAnketaId = anketaId;
        mSecret = secret;
    }

    public long getAnketaId() {
        return mAnketaId;
    }

    public SecretToken getSecret() {
        return mSecret;
    }
}
