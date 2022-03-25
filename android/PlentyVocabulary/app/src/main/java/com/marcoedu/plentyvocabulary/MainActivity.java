package com.marcoedu.plentyvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.marcoedu.plentyvocabulary.data.sp.SPKeys;
import com.marcoedu.plentyvocabulary.data.sp.SPUtil;
import com.marcoedu.plentyvocabulary.plan.PlanConfig;
import com.marcoedu.plentyvocabulary.ui.DayListActivity;
import com.marcoedu.plentyvocabulary.util.LogUtil;
import com.marcoedu.plentyvocabulary.util.ToastUitl;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int PLAN_COUNTS = 6;
    private EditText mWordsPerDayView;
    private TextView mWordsPerDayConfirm;
    private ArrayList<TextView> mPlanViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DBManager.instance().getWordList(PlanConfig.PLAN_ID_10K);
        initViews();
        initListeners();
    }

    private void initViews() {
        mWordsPerDayView = findViewById(R.id.et_words_per_day);
        mWordsPerDayConfirm = findViewById(R.id.tv_words_per_day_confirm);

        int[] planViewIds = {R.id.tv_plan_10k, R.id.tv_plan_20k, R.id.tv_plan_30k,
                R.id.tv_plan_40k, R.id.tv_plan_50k, R.id.tv_plan_52k};
        int[] planIds = {PlanConfig.PLAN_ID_10K, PlanConfig.PLAN_ID_20K, PlanConfig.PLAN_ID_30K,
                PlanConfig.PLAN_ID_40K, PlanConfig.PLAN_ID_50K, PlanConfig.PLAN_ID_52K};
        TextView planView;
        for(int i = 0; i < PLAN_COUNTS; ++i) {
            planView = findViewById(planViewIds[i]);
            planView.setOnClickListener(this);
            planView.setTag(planIds[i]);
            mPlanViews.add(planView);
        }

        mWordsPerDayView.setText(String.valueOf(SPUtil.get(PVApp.getContext(), SPKeys.WORDS_PER_DAY, PlanConfig.WORDS_PER_DAY)));
    }

    private void initListeners() {
        mWordsPerDayConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_plan_10k:
            case R.id.tv_plan_20k:
            case R.id.tv_plan_30k:
            case R.id.tv_plan_40k:
            case R.id.tv_plan_50k:
            case R.id.tv_plan_52k:
                onPlanClicked((int)v.getTag());
                break;
            case R.id.tv_words_per_day_confirm:
                onConfirm();
                break;
        }
    }

    private void onPlanClicked(int planId) {
        Intent intent = new Intent(this, DayListActivity.class);
        intent.putExtra(PlanConfig.PLAN_ID, planId);
        startActivity(intent);
    }

    private void onConfirm() {
        int count = 0;
        try {
            count = Integer.valueOf(mWordsPerDayView.getText().toString());
        } catch (Exception e) {
            LogUtil.d("onConfirm error, e:"+e.toString());
        } finally {
            if(count > 0 && count < 10000) {
                SPUtil.put(PVApp.getContext(), SPKeys.WORDS_PER_DAY, count);
                ToastUitl.showToast("每日单词个数，设置成功");
            } else {
                ToastUitl.showToast("每日单词个数异常");
            }
        }
    }
}