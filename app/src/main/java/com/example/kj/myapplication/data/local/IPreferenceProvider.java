package com.example.kj.myapplication.data.local;

import com.example.kj.myapplication.entity.SecretToken;

public interface IPreferenceProvider {
    void setSecretToken(SecretToken secretToken);
    SecretToken getSecretToken();
    long getAnketaId();
    void setAnketaId(long anketaId);
}