package com.example.dell.zxqy.form.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.bean.PaySuccess;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;

import java.util.HashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity implements IView {
    private Button button_go;
    private RadioGroup radioGroup;
    private IPersentermpl mIpresenterImpl;
    private int payType=0;
    private String orderId;
    private TextView text_success;
    private TextView text_finish;
    private TextView text_falied;
    private RelativeLayout rela_success;
    private RelativeLayout rela_falied;
    private ConstraintLayout con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_go = findViewById(R.id.pay_button_go);
        radioGroup = findViewById(R.id.pay_group);
        text_success = findViewById(R.id.pay_text_success);
        text_finish = findViewById(R.id.pay_text_finish);
        text_falied = findViewById(R.id.pay_text_falied);
        rela_success = findViewById(R.id.pay_rela_success);
        rela_falied = findViewById(R.id.pay_rela_falied);
        con = findViewById(R.id.pay_con);
        //互绑
        initPresenter();
        con.setVisibility(View.VISIBLE);
        rela_falied.setVisibility(View.GONE);
        rela_success.setVisibility(View.GONE);
        initData();
    }
    protected void initData() {
        //获取值
        initIntent();
        //选择支付方式
        initPay();
        //确定按钮
        initGo();
    }
        private void initGo() {
            button_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> params=new HashMap<>();
                    params.put("orderId",orderId);
                    params.put("payType",String.valueOf(payType));
                    mIpresenterImpl.postRequest(Api.PAY_SHOP,PaySuccess.class,params);
                }
            });
        }

        private void initIntent() {
            Intent intent=getIntent();
            orderId = intent.getStringExtra("orderId");
            String all_prices = intent.getStringExtra("all_price");
            button_go.setText("余额支付"+all_prices+"元");
        }

        private void initPay() {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.pay_money:
                            payType=1;
                            break;
                        case R.id.pay_weixin:
                            payType=2;
                            break;
                        case R.id.pay_zhifubao:
                            payType=3;
                            break;
                    }
                }
            });
        }

        private void initPresenter() {
            mIpresenterImpl=new IPersentermpl(this);
        }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof PaySuccess){
            PaySuccess payBean= (PaySuccess) data;
            if (payBean.getStatus().equals("0000")){
                rela_falied.setVisibility(View.GONE);
                rela_success.setVisibility(View.VISIBLE);
                text_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                text_success.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        con.setVisibility(View.VISIBLE);
                        rela_falied.setVisibility(View.GONE);
                        rela_success.setVisibility(View.GONE);
                        finish();
                    }
                });
            }else{
                rela_falied.setVisibility(View.VISIBLE);
                rela_success.setVisibility(View.GONE);
                text_falied.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        con.setVisibility(View.VISIBLE);
                        rela_falied.setVisibility(View.GONE);
                        rela_success.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onFiled(String e) {

    }
}
