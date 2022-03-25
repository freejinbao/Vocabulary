package com.marcoedu.plentyvocabulary.word;

import android.content.Context;

import com.marcoedu.plentyvocabulary.PVApp;
import com.marcoedu.plentyvocabulary.R;
import com.marcoedu.plentyvocabulary.plan.PlanConfig;
import com.marcoedu.plentyvocabulary.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RandList {

    private static ArrayList<Integer> sRandList = new ArrayList<>();
    private static ArrayList<Integer> sLastRandList = new ArrayList<>();

    private static final int LAST_INDEX = 1727;

    public static void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initRandList();
            }
        }).start();
    }

    public static void initRandList() {
        sRandList.clear();
        sLastRandList.clear();
        String randStr = readRandFromFile();
        randStr.replace("\n", "");
        randStr.replace(" ", "");
        String[] randList = randStr.split(",");
        int num = 0;
        for(String numberStr : randList) {
            num = Integer.valueOf(numberStr.trim());
            sRandList.add(num);
            if(num <= LAST_INDEX) {
                sLastRandList.add(num);
            }
        }
        LogUtil.d("rand list size:"+sRandList.size());
    }

    private static String readRandFromFile() {
        String ret = null;
        Context context = PVApp.getContext();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = context.getResources().openRawResource(R.raw.rand_list);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            ret = sb.toString();
        } catch (IOException e) {
            LogUtil.d("readRandFromFile01 fail, e:"+e.toString());
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
                if(isr != null) {
                    isr.close();
                }
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                LogUtil.d("readRandFromFile02 fail, e:"+e.toString());
            }
        }
        return ret;
    }

    public static int get(int planId, int index) {
        if(planId == PlanConfig.PLAN_ID_52K) {
            if(index >= 0 && index < sLastRandList.size()) {
                return sLastRandList.get(index);
            }
        } else {
            if(index >= 0 && index < sRandList.size()) {
                return sRandList.get(index);
            }
        }

        return 1;
    }


    /*private static void initRandList() {
        ArrayList<Integer> sortList = new ArrayList<Integer>();
        ArrayList<Integer> randList = new ArrayList<>();
        for(int i = 1; i<=10000; i++) {
            sortList.add(i);
        }
        Random rand = new Random();
        int hit;
        for(int i=1; i<=10000; i++) {
            hit = rand.nextInt(sortList.size());
            randList.add(sortList.get(hit));
            sortList.remove(hit);
        }
        StringBuilder sb = new StringBuilder();
        String line = "";
        int count = 0;
        for(Integer r : randList) {
            ++count;
            line = line + r + ", ";
            if(count % 10 == 0) {
                line = line + "\n";
                sb.append(line);
                line = "";
            }
        }
        String path = this.getApplicationContext().getFilesDir().getAbsolutePath()+"/rand.txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(path, true);
            fw.write(sb.toString());
        } catch (IOException e) {
            //e.printStackTrace();
            LogUtil.d("error01, e:"+e.toString());
        } finally {
            try {
                if(fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException e) {
                LogUtil.d("error02, e:"+e.toString());
            }
        }

        //LogUtil.d("random list:"+sb.toString());
    }*/

}
