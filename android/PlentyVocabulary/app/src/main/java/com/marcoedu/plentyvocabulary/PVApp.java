package com.marcoedu.plentyvocabulary;

import android.app.Application;
import android.content.Context;

import com.marcoedu.plentyvocabulary.data.db.DBManager;
import com.marcoedu.plentyvocabulary.word.RandList;

public class PVApp extends Application {

    private static Context sInstance;

    public static Context getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initApp();
    }

    private void initApp() {
        DBManager.instance().init(sInstance);
        RandList.init();
    }
}
