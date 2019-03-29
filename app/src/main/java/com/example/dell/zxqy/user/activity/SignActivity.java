package com.example.dell.zxqy.user.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseActivity;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.user.bean.Sign;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignActivity extends BaseActivity implements IView {
    @BindView(R.id.but_login)
    TextView button;
    @BindView(R.id.phone_num)
    EditText phone_num;
    @BindView(R.id.phone_pwd)
    EditText phone_pwd;
    @BindView(R.id.but_regin)
    Button button_regin;
    IPersentermpl iPersentermpl;
    Unbinder bind;
    @Override
    protected int getView() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        iPersentermpl = new IPersentermpl(this);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent intent = new Intent(SignActivity.this, MainActivity.class);
    }
    @OnClick(R.id.but_login)
    public void login(){
        Intent intent = new Intent(SignActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.but_regin)
    public void reg(){
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone_num.getText().toString());
        map.put("pwd",phone_pwd.getText().toString());
        iPersentermpl.postRequest(Api.Sing_path,Sign.class,map);
        Intent intent = new Intent(SignActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object data) {
        Sign bean= (Sign) data;
        if(bean.getStatus().equals("0000")){
            Intent intent = new Intent(SignActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(SignActivity.this,getString(R.string.w),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(SignActivity.this,getString(R.string.e),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFiled(String e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPersentermpl.onDestory();
    }
}
