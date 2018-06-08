package com.seawind.indicham.Util;
/* Created by admin on 08-Jun-18.*/

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

