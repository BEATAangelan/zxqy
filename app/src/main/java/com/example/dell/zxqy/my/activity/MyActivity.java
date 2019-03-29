package com.example.dell.zxqy.my.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.bean.MsgBean;
import com.example.dell.zxqy.my.bean.IndentBean;
import com.example.dell.zxqy.my.bean.MyBean;
import com.example.dell.zxqy.my.bean.PasswordBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.example.dell.zxqy.user.activity.MainActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyActivity extends AppCompatActivity implements IView {
    @BindView(R.id.means_name)
    TextView means_name;
    @BindView(R.id.means_password)
    TextView means_password;
    @BindView(R.id.but_update)
    Button but_update;
    @BindView(R.id.means_img)
    SimpleDraweeView means_img;
    private Unbinder unbinder;
    IPersentermpl iPersentermpl;
    private AlertDialog.Builder updateName;
    private AlertDialog.Builder updatePass;
    String paword,yuanword,hupass;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        unbinder = ButterKnife.bind(this);
        iPersentermpl = new IPersentermpl(this);
        iPersentermpl.getRequest(Api.SearchMyPath,MyBean.class);
        EventBus.getDefault().register(this);

    }
    //点击修改姓名
    @OnClick(R.id.means_name)
        public void namemeans(){
            updateName = new AlertDialog.Builder(this);
            View update_name = View.inflate(this, R.layout.alter_layout, null);
            final EditText newName = update_name.findViewById(R.id.updata_name);
            updateName.setView(update_name);
            updateName.setTitle("修改名字");
            updateName.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
        });
        updateName.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name=newName.getText().toString();
                if(name.equals("")){
                    Toast.makeText(MyActivity.this,"名称不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, String> map = new HashMap<>();
                    map.put("nickName", name);
                    iPersentermpl.putRequest(Api.UpateNamePath,IndentBean.class,map);
                    means_name.setText(name);
                }
            }
        });
        updateName.show();
        Bundle bundle = new Bundle();
        bundle.putString("nickName",name);
    }
        //点击修改姓名
        @OnClick(R.id.means_password)
         public void  passwordmean(){
            updatePass = new AlertDialog.Builder(this);
            View updat_pass = View.inflate(this, R.layout.alert_pass, null);
            final EditText newPass = updat_pass.findViewById(R.id.hpass);
            final EditText oldPass = updat_pass.findViewById(R.id.oldpass);
            updatePass.setView(updat_pass);
            updatePass.setTitle("修改密码");
            updatePass.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            updatePass.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    yuanword = oldPass.getText().toString();
                    hupass = newPass.getText().toString();
                         if(hupass.equals("")){
                             Toast.makeText(MyActivity.this,"修改后不能为空",Toast.LENGTH_SHORT).show();
                         }else {
                             Map<String,String> map=new HashMap<>();
                             map.put("oldPwd",yuanword);
                             map.put("newPwd",hupass);
                             iPersentermpl.putRequest(Api.UpatePassWordPath,PasswordBean.class,map);
                             Intent intent = new Intent(MyActivity.this, MainActivity.class);
                             startActivity(intent);
                         }
                    }

            });
            updatePass.show();
            Bundle bundle = new Bundle();
            bundle.putString("newPwd",hupass);
        }
      //点击完成
      @OnClick(R.id.but_update)
      public void setfisish(){
        EventBus.getDefault().post(new MsgBean(""));
        finish();
      }
    @Override
    public void onSuccess(Object data) {
        if(data instanceof MyBean){
            MyBean bean= (MyBean) data;
            MyBean.ResultBean result = bean.getResult();
            means_name.setText(result.getNickName());
            means_password.setText(result.getPassword());
            means_img.setImageURI(result.getHeadPic());
        }
        if(data instanceof PasswordBean){
            PasswordBean bean= (PasswordBean) data;
            if(bean.getStatus().equals("0000")){
                Toast.makeText(MyActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MyActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFiled(String e) {

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void sendpass(String password){
       paword=password;
        Log.i("tag",password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        iPersentermpl.onDestory();
    }
}
