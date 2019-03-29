package com.example.dell.zxqy.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.adapter.AddressAdapter;
import com.example.dell.zxqy.my.bean.MoAddress;
import com.example.dell.zxqy.my.bean.UpdateAddressBean;
import com.example.dell.zxqy.my.bean.shopAddressBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.sucesss)
    TextView sucesss;
    @BindView(R.id.address_head_view)
    RelativeLayout addressHeadView;
    @BindView(R.id.address_rvv)
    RecyclerView addressRvv;
    @BindView(R.id.insertt_address)
    Button inserttAddress;
    @BindView(R.id.address_bottom_view)
    RelativeLayout addressBottomView;
    private AddressAdapter addressAdapter;
    private IPersentermpl iPersentermpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        iPersentermpl=new IPersentermpl(this);
        initLinear();
        setnewAddress();
    }
    //加载页面
    private void initLinear(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRvv.setLayoutManager(layoutManager);

        iPersentermpl.getRequest(Api.SearchAddessPath,shopAddressBean.class);
    }
    //新增地址
    private void setnewAddress(){
        inserttAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAddressActivity.this,NewAddess.class));
            }
        });
    }
    //修改地址
    private void updateAddress(){
        addressAdapter.nsetData(new AddressAdapter.NsetOnItemClick() {
            @Override
            public void onClick(String id, String name, String phone, String address, String code) {
                UpdateAddressBean bean = new UpdateAddressBean();
                bean.setId(id);
                bean.setName(name);
                bean.setPhone(phone);
                bean.setAddress(address);
                bean.setCode(code);
                EventBus.getDefault().postSticky(bean);
               startActivity(new Intent(MyAddressActivity.this,UpdateAddressActivity.class));
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        iPersentermpl.getRequest(Api.SearchAddessPath,shopAddressBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof shopAddressBean){
            shopAddressBean bean= (shopAddressBean) data;
            addressAdapter = new AddressAdapter(MyAddressActivity.this);
            addressRvv.setAdapter(addressAdapter);

            addressAdapter.setDatas(bean.getResult());

            addressAdapter.setData(new AddressAdapter.setOnItemClick() {
                @Override
                public void onClick(String id) {
                    Map<String,String> map=new HashMap<>();
                    map.put("id", id);
                    iPersentermpl.postRequest(Api.MoPath,MoAddress.class,map);
                }
            });
            updateAddress();
        }

        if (data instanceof MoAddress){
            MoAddress bean= (MoAddress) data;
            if(bean.getStatus().equals("0000")){
                Toast.makeText(MyAddressActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
                iPersentermpl.getRequest(Api.SearchAddessPath,shopAddressBean.class);
            }else {
                Toast.makeText(MyAddressActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFiled(String e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersentermpl.onDestory();
    }
}
