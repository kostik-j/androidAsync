package com.example.kj.myapplication.entity;

final public class SecretToken {
    private String mSecret;

    public SecretToken(String secret) throws LogicException {
        if (secret.isEmpty()) {
            throw new LogicException("Empty secret");
        }
        mSecret = secret;
    }

    public String getSecret() {
        return mSecret;
    }

    @Override
    public String toString() {
        return getSecret();
    }

    public boolean equals(SecretToken secretToken) {
        return getSecret().equals(secretToken.getSecret());
    }
}
