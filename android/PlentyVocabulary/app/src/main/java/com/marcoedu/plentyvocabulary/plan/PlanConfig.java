package com.marcoedu.plentyvocabulary.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanConfig {

    public static int WORDS_PER_DAY = 150;

    public static final String PLAN_ID = "plan_id";
    public static final int PLAN_ID_INVALID = 0;
    public static final int PLAN_ID_10K = 10;
    public static final int PLAN_ID_20K = 20;
    public static final int PLAN_ID_30K = 30;
    public static final int PLAN_ID_40K = 40;
    public static final int PLAN_ID_50K = 50;
    public static final int PLAN_ID_52K = 52;

    public static Map<Integer, PlanBean> sPlanMap = new HashMap<>();
    static {
        sPlanMap.put(PLAN_ID_10K, new PlanBean(0, 1, 10000, 10000));
        sPlanMap.put(PLAN_ID_20K, new PlanBean(10000, 10001, 20000, 10000));
        sPlanMap.put(PLAN_ID_30K, new PlanBean(20000, 20001, 30000, 10000));
        sPlanMap.put(PLAN_ID_40K, new PlanBean(30000, 30001, 40000, 10000));
        sPlanMap.put(PLAN_ID_50K, new PlanBean(40000, 40001, 50000, 10000));
        sPlanMap.put(PLAN_ID_52K, new PlanBean(50000, 50001, 51728, 1728));
    }

    public static PlanBean getPlan(int planId) {
        return sPlanMap.get(planId);
    }

    public static int getPlanBaseId(int planId) {
        return sPlanMap.get(planId).baseId;
    }
}
