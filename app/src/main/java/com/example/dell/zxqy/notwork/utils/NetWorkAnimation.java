package com.example.dell.zxqy.notwork.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.dell.zxqy.R;

public class NetWorkAnimation {
    String message=null;
    private  Dialog dialog;
    private  Context context;
    public NetWorkAnimation(Context context) {
        this.context = context;
    }

    public NetWorkAnimation(Context context, String message) {
        this.context = context;
        this.message=message;

    }
    public void show(){
        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_style,null,false);
        dialog.setContentView(view);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow()
                .getAttributes();
        lp.width =  ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
