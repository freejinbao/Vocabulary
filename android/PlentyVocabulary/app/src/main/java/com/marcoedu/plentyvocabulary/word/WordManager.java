package com.marcoedu.plentyvocabulary.word;

import android.text.TextUtils;

import com.marcoedu.plentyvocabulary.data.db.DBManager;
import com.marcoedu.plentyvocabulary.plan.PlanConfig;

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<IdBean> mPlanBeans = new ArrayList<>();
    private int mPlanId = PlanConfig.PLAN_ID_INVALID;

    private ArrayList<WordBean> mWordBeans = new ArrayList<>();

    public void updatePlanList(int planId) {
        if(mPlanId != planId) {
            mPlanBeans.clear();
            List<IdBean> tmpList = DBManager.instance().getIdList(planId);
            mPlanBeans.addAll(tmpList);
            tmpList.clear();
        }
    }

    public List<IdBean> getIdList(int planId) {
        updatePlanList(planId);
        return mPlanBeans;
    }

    public void updateWordList(List<IdBean> idList) {
        getWordList(idList);
    }

    public List<WordBean> getWordList() {
        return mWordBeans;
    }

    public List<WordBean> getWordList(List<IdBean> idList) {
        if(idList != null && idList.size() == mWordBeans.size()) {
            if(mWordBeans.size() > 0) {
                int curFirstIdx = mWordBeans.get(0).idx;
                int requestFirstIdx = idList.get(0).idx;
                if(curFirstIdx == requestFirstIdx) {
                    return mWordBeans;
                }
            }
        }
        //need query from db
        mWordBeans.clear();
        List<WordBean> tmpList = DBManager.instance().getWordList(idList);
        mWordBeans.addAll(tmpList);
        return mWordBeans;
    }

    public WordBean getWord(String word) {
        WordBean wordBean = null;
        if(!TextUtils.isEmpty(word)) {
            wordBean = DBManager.instance().getWord(word);
        }
        return wordBean;
    }
}
