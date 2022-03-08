package com.marcoedu.plentyvocabulary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.marcoedu.plentyvocabulary.word.WordBean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static String WORD_DB_FILE = "words_dict.db";

    private static volatile Object sLock = new Object();
    private static DBManager sInstance = null;
    public static DBManager instance() {
        if(sInstance == null) {
            synchronized (sLock) {
                if(sInstance == null) {
                    sInstance = new DBManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        AssetsDatabaseManager.initManager(context);
    }

    public List<WordBean> getWordList(int planId) {
        ArrayList<WordBean> list = new ArrayList<>();
        AssetsDatabaseManager dbMgr = AssetsDatabaseManager.getInstance();
        SQLiteDatabase db = dbMgr.getDatabase(WORD_DB_FILE);
        String selection = WordTable.COL_GROUP+"="+planId;
        Cursor cursor = db.query(WordTable.NAME, null, selection, null, null, null, null);
        if(cursor != null) {
            int index = 0;
            while (cursor.moveToNext()) {
                if(index > 10) {
                    break;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("word:"+cursor.getString(cursor.getColumnIndex(WordTable.COL_WORD)));
                sb.append(", group:"+cursor.getInt(cursor.getColumnIndex(WordTable.COL_GROUP)));
                //sb.append(", meaning:"+cursor.getString(cursor.getColumnIndex(WordTable.COL_MEANING)));

                byte[] meaningBlob = cursor.getBlob(cursor.getColumnIndex(WordTable.COL_MEANING));
                String meaning = "null";
                try {
                    meaning = new String(meaningBlob, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sb.append(", meaning:"+meaning);
                Log.d("wordtag", sb.toString());


                ++index;
            }
        }
        return list;
    }
}
