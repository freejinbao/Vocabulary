package com.marcoedu.plentyvocabulary.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.marcoedu.plentyvocabulary.R;
import com.marcoedu.plentyvocabulary.util.LogUtil;
import com.marcoedu.plentyvocabulary.util.ToastUitl;
import com.marcoedu.plentyvocabulary.word.MeaningBean;
import com.marcoedu.plentyvocabulary.word.WordBean;
import com.marcoedu.plentyvocabulary.word.WordManager;

import java.util.List;
import java.util.Locale;

public class WordDetailActivity extends AppCompatActivity {

    private Handler mHandler;
    private TextToSpeech mSpeech;
    private boolean mTtsInited;

    private TextView mWordView;
    private WordBean mWordBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        mHandler = new Handler(Looper.getMainLooper());
        initTts();
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uninitTts();
    }

    private void initTts() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSpeech = new TextToSpeech(WordDetailActivity.this, new TTSListener());
            }
        }, 200);
    }

    private void uninitTts() {
        if(mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
            mSpeech = null;
        }
    }

    private void initData() {
        mWordBean = null;
        /*int pos = getIntent().getIntExtra(WordListActivity.POSITION, 0);
        List<WordBean> wordList = WordManager.instance().getWordList();
        if(wordList != null && pos >= 0 && pos < wordList.size()) {
            mWordBean = wordList.get(pos);
        }*/
        //mWordBean = WordManager.instance().getWord("abandon");
        mWordBean = WordManager.instance().getWord("contact");
        LogUtil.d("wordBean:"+mWordBean);
    }

    private void initView() {
        mWordView = findViewById(R.id.tv_word);
        if(mWordBean != null) {
            mWordView.setText(mWordBean.getWord());
        }
    }

    private void initListener() {
        mWordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //speakWord(mWordView.getText().toString());
                testParseJson();
            }
        });
    }

    private void testParseJson() {
        if(mWordBean != null) {
            Gson gson = new Gson();
            MeaningBean[] meanList = gson.fromJson(mWordBean.getMeaning(), MeaningBean[].class);
            LogUtil.d("parse json:"+mWordBean.getMeaning());
            LogUtil.d("parse MeaningBean:"+MeaningBean.toStr(meanList));
        } else {
            ToastUitl.showToast("单词数据为空！");
        }

    }

    private void speakWord(String word) {
        if(!TextUtils.isEmpty(word)) {
            if(mSpeech != null && mTtsInited) {
                mSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, String.valueOf(System.currentTimeMillis()));
            }
        } else {
            ToastUitl.showToast("语音播报的文本为空！");
        }
    }

    private class TTSListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int status) {
            if(status == TextToSpeech.SUCCESS) {
                mSpeech.setLanguage(Locale.ENGLISH);
                mSpeech.setSpeechRate(0.8f);
                mTtsInited = true;
            } else {
                mTtsInited = false;
            }
        }
    }
}
