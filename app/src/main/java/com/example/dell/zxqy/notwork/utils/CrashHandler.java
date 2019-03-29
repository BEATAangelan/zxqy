package com.example.dell.zxqy.notwork.utils;

import android.util.Log;

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.i("TAG","线程"+t.getName()+"错误"+e.getMessage());
    }
}
