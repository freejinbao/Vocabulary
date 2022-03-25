package com.marcoedu.plentyvocabulary.ui;

import android.content.Intent;
import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcoedu.plentyvocabulary.R;
import com.marcoedu.plentyvocabulary.data.sp.SPKeys;
import com.marcoedu.plentyvocabulary.data.sp.SPUtil;
import com.marcoedu.plentyvocabulary.plan.PlanBean;
import com.marcoedu.plentyvocabulary.plan.PlanConfig;
import com.marcoedu.plentyvocabulary.util.LogUtil;
import com.marcoedu.plentyvocabulary.word.IdBean;
import com.marcoedu.plentyvocabulary.word.RandList;
import com.marcoedu.plentyvocabulary.word.WordManager;

import java.util.ArrayList;
import java.util.List;

public class DayListActivity extends AppCompatActivity {

    private Handler mHandler;
    private int mPlanId;
    private int mWordsCount;
    private ArrayList<DayBean> mListData = new ArrayList<>();

    private RecyclerView mRecycleView;
    private DayListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list);

        mHandler = new Handler(Looper.getMainLooper());
        initData();
        initViews();
    }

    private void initData() {
        mPlanId = getIntent().getIntExtra(PlanConfig.PLAN_ID, PlanConfig.PLAN_ID_10K);
        mWordsCount = (int)SPUtil.get(SPKeys.WORDS_PER_DAY, PlanConfig.WORDS_PER_DAY);
        initListData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                WordManager.instance().updatePlanList(mPlanId);
            }
        }).start();
    }

    private void initListData() {
        mListData.clear();
        PlanBean planBean = PlanConfig.getPlan(mPlanId);
        int index = 1;
        DayBean bean = null;
        for(int i = planBean.start; i <= planBean.end; i = i + mWordsCount) {
            bean = new DayBean();
            bean.start = i;
            bean.end = Math.min((i+mWordsCount-1), planBean.end);
            bean.index = index;

            StringBuilder sb = new StringBuilder();
            sb.append("Day :");
            sb.append(index);
            sb.append(" [");
            sb.append(bean.start);
            sb.append(", ");
            sb.append(bean.end);
            sb.append("]");
            bean.name = sb.toString();
            mListData.add(bean);
            ++index;
        }
    }

    private void initViews() {
        mRecycleView = findViewById(R.id.rv_word_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new DayListAdapter(this, mListData, mListener);
        mRecycleView.setAdapter(mAdapter);
    }

    public List<IdBean> getIdList(int planId, int position) {
        ArrayList<IdBean> idList = new ArrayList<>();
        DayBean dayBean = mListData.get(position);
        IdBean idBean = null;
        int baseId = PlanConfig.getPlanBaseId(mPlanId);
        for(int i=dayBean.start; i <= dayBean.end; ++i) {
            idBean = new IdBean();
            idBean.id = baseId+ RandList.get(mPlanId, (i-baseId));
            idBean.idx = 10*idBean.id;
            idList.add(idBean);
        }
        LogUtil.d("idlist size:"+idList.size());
        return idList;
    }

    private DayListAdapter.OnItemClickListener mListener = new DayListAdapter.OnItemClickListener() {

        @Override
        public void onEnterListClicked(View view, int position) {
            LogUtil.d("onEnterListClicked, position:"+position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WordManager.instance().updateWordList(getIdList(mPlanId, position));
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            gotoWordListActivity();
                        }
                    });
                }
            }).start();
        }

        @Override
        public void onStartLearnClicked(View view, int position) {
            LogUtil.d("onStartLearnClicked, position:"+position);
            WordManager.instance().updateWordList(getIdList(mPlanId, position));
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    gotoWordDetailActivity();
                }
            });
        }
    };

    private void gotoWordListActivity() {
        Intent intent = new Intent(this, WordListActivity.class);
        startActivity(intent);
    }

    private void gotoWordDetailActivity() {
        Intent intent = new Intent(this, WordDetailActivity.class);
        startActivity(intent);
    }
}
