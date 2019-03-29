package com.example.dell.zxqy.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.bean.AddAddressBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.IvParameterSpec;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAddess extends AppCompatActivity implements IView {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.but_save)
    Button butSave;
    @BindView(R.id.insert_address_name)
    EditText insertAddressName;
    @BindView(R.id.insert_address_phone)
    EditText insertAddressPhone;
    @BindView(R.id.te)
    TextView te;
    @BindView(R.id.insert_address_region)
    TextView insertAddressRegion;
    @BindView(R.id.but_city)
    Button butCity;
    @BindView(R.id.rela)
    RelativeLayout rela;
    @BindView(R.id.insert_address)
    EditText insertAddress;
    @BindView(R.id.insert_address_code)
    EditText insertAddressCode;
   IPersentermpl iPersentermpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_addess);
        ButterKnife.bind(this);
        iPersentermpl=new IPersentermpl(this);
        insertShow();
    }
    //添加页面
    public void insertShow(){
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = insertAddressName.getText().toString();
                String phone = insertAddressPhone.getText().toString();
                String address = insertAddress.getText().toString();
                String code = insertAddressCode.getText().toString();
                Map<String,String> map=new HashMap<>();
                map.put("realName",name);
                map.put("phone",phone);
                map.put("address",insertAddressRegion.getText().toString());
                map.put("zipCode",code);
                iPersentermpl.postRequest(Api.AddAddaessPath,AddAddressBean.class,map);
                finish();
            }
        });

        insertAddressRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }
    //三级联动
    private void showPicker() {
        CityPicker cityPicker = new CityPicker.Builder(NewAddess.this)
                .textSize(18)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("北京市")
                .city("北京市")
                .district("昌平区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                insertAddressRegion.setText(province+" "+city+" "+district+" ");
            }
        });
    }
    @Override
    public void onSuccess(Object data) {
      if(data instanceof AddAddressBean){
          AddAddressBean bean= (AddAddressBean) data;
          if(bean.getStatus().equals("0000")){
              Toast.makeText(NewAddess.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
          }
          else {
              Toast.makeText(NewAddess.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
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
