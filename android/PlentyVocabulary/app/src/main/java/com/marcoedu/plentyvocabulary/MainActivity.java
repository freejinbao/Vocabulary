package com.marcoedu.plentyvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marcoedu.plentyvocabulary.db.DBManager;
import com.marcoedu.plentyvocabulary.plan.PlanConfig;

public class MainActivity extends AppCompatActivity {

    private TextView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestView = findViewById(R.id.tv_start_listen);
        mTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.instance().getWordList(PlanConfig.PLAN_ID_10K);
            }
        });
    }
}