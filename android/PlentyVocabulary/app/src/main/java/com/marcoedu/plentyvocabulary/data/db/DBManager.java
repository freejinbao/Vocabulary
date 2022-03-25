package com.marcoedu.plentyvocabulary.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.marcoedu.plentyvocabulary.util.LogUtil;
import com.marcoedu.plentyvocabulary.word.IdBean;
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

    public WordBean getWord(String word) {
        WordBean bean = null;
        AssetsDatabaseManager dbMgr = AssetsDatabaseManager.getInstance();
        SQLiteDatabase db = dbMgr.getDatabase(WORD_DB_FILE);
        String selection = WordTable.COL_WORD+"=\'"+word+"\'";
        Cursor cursor = db.query(WordTable.NAME, null, selection, null, null, null, null);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                bean = new WordBean();
                convertWord(bean, cursor);
                break;
            }
            cursor.close();
        }
        LogUtil.d("get word:"+bean);
        return bean;
    }

    public List<IdBean> getIdList(int planId) {
        ArrayList<IdBean> list = new ArrayList<>();
        AssetsDatabaseManager dbMgr = AssetsDatabaseManager.getInstance();
        SQLiteDatabase db = dbMgr.getDatabase(WORD_DB_FILE);
        String selection = WordTable.COL_GROUP+"="+planId;
        Cursor cursor = db.query(WordTable.NAME, null, selection, null, null, null, null);
        if(cursor != null) {
            IdBean idBean = null;
            while (cursor.moveToNext()) {
                idBean = new IdBean();
                idBean.id = cursor.getInt(cursor.getColumnIndex(WordTable.COL_ID));
                idBean.idx = cursor.getInt(cursor.getColumnIndex(WordTable.COL_IDX));
                list.add(idBean);
            }
            cursor.close();
        }
        LogUtil.d("plan list size:"+list.size());
        return list;
    }

    public List<WordBean> getWordList(List<IdBean> idList) {
        ArrayList<WordBean> list = new ArrayList<>();
        AssetsDatabaseManager dbMgr = AssetsDatabaseManager.getInstance();
        SQLiteDatabase db = dbMgr.getDatabase(WORD_DB_FILE);

        if(idList != null) {
            WordBean wordBean = null;
            String selection = null;
            Cursor cursor = null;
            for(IdBean idBean : idList) {
                selection = WordTable.COL_ID+"="+idBean.id;
                cursor = db.query(WordTable.NAME, null, selection, null, null, null, null);
                if(cursor != null) {
                    wordBean = null;
                    while (cursor.moveToNext()) {
                        wordBean = new WordBean();
                        convertWord(wordBean, cursor);
                        list.add(wordBean);
                    }
                    if(wordBean == null) {
                        LogUtil.d("word id:"+idBean.id+" can not query!");
                    }
                    cursor.close();
                }
            }
        }
        LogUtil.d("query list size:"+list.size());
        return list;
    }

    private void convertWord(WordBean wordBean, Cursor cursor) {
        if(wordBean != null && cursor != null) {
            wordBean.id = cursor.getInt(cursor.getColumnIndex(WordTable.COL_ID));
            wordBean.idx = cursor.getInt(cursor.getColumnIndex(WordTable.COL_IDX));
            wordBean.word = cursor.getString(cursor.getColumnIndex(WordTable.COL_WORD));
            wordBean.meaning_zh = cursor.getString(cursor.getColumnIndex(WordTable.COL_MEANING_ZH));
            wordBean.collected = cursor.getInt(cursor.getColumnIndex(WordTable.COL_COLLECTED));
            wordBean.learned = cursor.getInt(cursor.getColumnIndex(WordTable.COL_LEARNED));
            wordBean.review = cursor.getInt(cursor.getColumnIndex(WordTable.COL_REVIEW));
            wordBean.grp = cursor.getInt(cursor.getColumnIndex(WordTable.COL_GROUP));

            byte[] meaningBlob = cursor.getBlob(cursor.getColumnIndex(WordTable.COL_MEANING));
            try {
                wordBean.meaning = new String(meaningBlob, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                LogUtil.d("getWordList fail, word:"+wordBean.word+", e:"+e.toString());
            }
        }
    }
}
