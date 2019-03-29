package com.example.dell.zxqy.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseActivity;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.notwork.utils.NetWorkAnimation;
import com.example.dell.zxqy.user.bean.Login;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.utils.NetWorkUtils;
import com.example.dell.zxqy.notwork.utils.RegularUtils;
import com.example.dell.zxqy.notwork.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements IView {
    @BindView(R.id.edit_phone_num)
    EditText phone_num;
    @BindView(R.id.edit_phone_pwd)
    EditText phone_pwd;
    @BindView(R.id.login_button)
    Button button;
    @BindView(R.id.checkbox_remeber)
    CheckBox checkBox;
    @BindView(R.id.text_sign)
    TextView textView;
    @BindView(R.id.image_eye)
    ImageView image;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String path="user/v1/login";
    private Unbinder bind;
    private IPersentermpl iPersentermpl;
    @Override
    protected int getView()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        iPersentermpl = new IPersentermpl(this);
    }

    @Override
    protected void initData() {
        //绑定buttonknife
        bind = ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //进行判断是否记住密码
        boolean r_ischeck = sharedPreferences.getBoolean("r_ischeck", false);
        if (r_ischeck) {
            String num = sharedPreferences.getString("num", null);
            String pwd = sharedPreferences.getString("pwd", null);
            phone_num.setText(num);
            phone_pwd.setText(pwd);
        }
        checkBox.setChecked(true);
        //点击记住密码
    }
        @OnTouch(R.id.image_eye)
        public boolean SignEyeClick(MotionEvent event){
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                phone_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else if(event.getAction()==MotionEvent.ACTION_UP){
                phone_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            return true;
        }

        @OnClick(R.id.text_sign)
        public void setbutton(){
            Intent intent = new Intent(MainActivity.this, SignActivity.class);
            startActivity(intent);
        }
        @OnClick(R.id.login_button)
        public void login(){
            String phone_num1 = phone_num.getText().toString();
            String phone_pwd1 = phone_pwd.getText().toString();
            //判断手机号
            if(RegularUtils.isPhoneValidator(phone_num1)){
                //判断密码
                if (RegularUtils.isPwdValidator(phone_pwd1)){
                    Map<String,String> map=new HashMap<>();
                    map.put("phone",phone_num1);
                    map.put("pwd",phone_pwd1);
                    if(NetWorkUtils.isNetWork(this)) {
                        iPersentermpl.postRequest(Api.Login_path, Login.class, map);
                    }else{
                        Toast.makeText(this, "请链接网络", Toast.LENGTH_SHORT).show();
                        //调用网络工具类中的方法，跳转到设置网络的界面
                        NetWorkUtils.setNetworkMethod(MainActivity.this);
                    }
                }else {
                    Toast.makeText(this,"小可爱，密码是6-16位的数字哦",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this,"小可爱，手机是11位的数字哦",Toast.LENGTH_SHORT).show();
            }
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPersentermpl.onDestory();
    }

    @Override
    public void onSuccess(Object data) {
        Login bean= (Login) data;
        if(bean.getStatus().equals("0000")){
            if(NetWorkUtils.isNetWork(this)){
                if (checkBox.isChecked()){
                    editor.putString("num", phone_num.getText().toString());
                    editor.putString("pwd", phone_pwd.getText().toString());
                    editor.putBoolean("r_ischeck", true);
                }else {
                    editor.clear();
                }

                editor.putString("userId",bean.getResult().getUserId()+"");
                editor.putString("sessionId",bean.getResult().getSessionId());
                editor.commit();
                Log.i("TAG",bean.getResult().getSessionId()+bean.getResult().getUserId()+"");
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(MainActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFiled(String e) {

    }

}