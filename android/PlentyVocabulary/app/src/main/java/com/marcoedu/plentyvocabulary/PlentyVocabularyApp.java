package com.marcoedu.plentyvocabulary;

import android.app.Application;
import android.content.Context;

import com.marcoedu.plentyvocabulary.db.DBManager;

public class PlentyVocabularyApp extends Application {

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
    }
}
