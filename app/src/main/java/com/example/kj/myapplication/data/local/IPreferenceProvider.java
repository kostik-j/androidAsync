package com.example.kj.myapplication.data.local;

import com.example.kj.myapplication.entity.AuthData;

public interface IPreferenceProvider {

    AuthData getAuthData();

    void setAuthData(AuthData authData);
}
