package com.example.dell.zxqy.Base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.utils.NetWorkUtils;
import com.example.dell.zxqy.user.activity.MainActivity;
import com.example.dell.zxqy.user.bean.Login;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        initView();
        initData();
    }

    //这个里面写页面
    protected abstract int getView();
    //这个里面写id
    protected abstract void initView();
    //继承后这个里面写内容
    protected abstract void initData();


}
