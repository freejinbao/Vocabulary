package com.marcoedu.plentyvocabulary.word;

public class WordManager {
    private static volatile Object sLock = new Object();
    private static WordManager sInstance = null;
    public static WordManager instance() {
        if(sInstance == null) {
            synchronized (sLock) {
                if(sInstance == null) {
                    sInstance = new WordManager();
                }
            }
        }
        return sInstance;
    }
}
