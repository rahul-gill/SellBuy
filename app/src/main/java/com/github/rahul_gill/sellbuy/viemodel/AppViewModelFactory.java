package com.github.rahul_gill.sellbuy.viemodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.rahul_gill.sellbuy.api.VolleySingleton;

public class AppViewModelFactory implements ViewModelProvider.Factory {
    private final VolleySingleton mVolley;
    private Application mApp;

    public AppViewModelFactory(Application app, VolleySingleton volley) {
        mVolley = volley;
        mApp = app;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AppViewModel(mApp, mVolley);

    }
}

