package com.example.kj.myapplication.data.local;

import com.example.kj.myapplication.entity.SecretToken;
import java.util.List;

public interface IPreferenceProvider {

    void setSecretToken(SecretToken secretToken);

    SecretToken getSecretToken();

    long getAnketaId();

    void setAnketaId(long anketaId);

    void setCookie(List<String> cookie);

    List<String> getCookie();
}