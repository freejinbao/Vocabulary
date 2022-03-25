package com.marcoedu.plentyvocabulary.util;

import android.util.Log;

public class LogUtil {

    private static final String TAG_PREFIX = "vocab_";
    public static void d(String tag, String log) {
        Log.d(TAG_PREFIX+tag, log);
    }

    public static void d(String log) {
        Log.d(TAG_PREFIX, log);
    }
}
