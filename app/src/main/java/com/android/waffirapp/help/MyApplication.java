package com.android.waffirapp.help;

import android.app.Application;

import com.android.waffirapp.realm.RealmController;

import io.realm.Realm;
/**
 * Created by Ahmad.Samhan.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmController.init();
    }
}
