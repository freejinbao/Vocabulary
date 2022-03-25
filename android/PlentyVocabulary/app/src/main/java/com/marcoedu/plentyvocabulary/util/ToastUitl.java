package com.marcoedu.plentyvocabulary.util;

import android.widget.Toast;

import com.marcoedu.plentyvocabulary.PVApp;

public class ToastUitl {
    public static void showToast(String msg) {
        Toast.makeText(PVApp.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
