package com.marcoedu.plentyvocabulary.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.marcoedu.plentyvocabulary.word.WordBean;
import com.marcoedu.plentyvocabulary.word.WordManager;

import java.util.ArrayList;

public class WordListActivity extends AppCompatActivity {

    public static final String POSITION = "position";

    private int mPlanId;
    private int mWordsCount;
    private ArrayList<WordBean> mListData = new ArrayList<>();

    private RecyclerView mRecycleView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        initData();
        initViews();
    }

    private void initData() {
        mListData.clear();
        mListData.addAll(WordManager.instance().getWordList());
    }

    private void initViews() {
        mRecycleView = findViewById(R.id.rv_word_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new WordListAdapter(this, mListData, mListener);
        mRecycleView.setAdapter(mAdapter);
    }

    private WordListAdapter.OnItemClickListener mListener = new WordListAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(View view, int position) {
            LogUtil.d("onItemClicked, position:"+position);
            Intent intent = new Intent(WordListActivity.this, WordListActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    };
}
