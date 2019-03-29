package com.example.dell.zxqy.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.bean.IndentBean;
import com.example.dell.zxqy.my.bean.UpdateAddress;
import com.example.dell.zxqy.my.bean.UpdateAddressBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.update_btn)
    Button updateBtn;
    @BindView(R.id.update_name)
    EditText updateName;
    @BindView(R.id.update_phone)
    EditText updatePhone;
    @BindView(R.id.update_address)
    TextView updateAddress;
    @BindView(R.id.update_code)
    EditText updateCode;
    IPersentermpl iPersentermpl;
    String id,name,phone,address,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        if (!EventBus.getDefault().isRegistered(this)){
            ButterKnife.bind(this);
        }

        EventBus.getDefault().register(this);
        iPersentermpl=new IPersentermpl(this);
        oldshow();
        newshow();
    }
    //回显
    public void oldshow(){
        updateName.setText(name);
        updatePhone.setText(address);
        updateAddress.setText(phone);
        updateCode.setText(code);
    }
    public void newshow(){
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = updateName.getText().toString().trim();
                phone = updatePhone.getText().toString().trim();
                code = updateCode.getText().toString().trim();
                Map<String, String> map = new HashMap<>();
                map.put("id", id + "");
                map.put("realName", name);
                map.put("phone", phone);
                map.put("address", updateAddress.getText().toString());
                map.put("zipCode", code);
                iPersentermpl.putRequest(Api.UpateAddPath,UpdateAddress.class,map);
            }
        });
        updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }
    @Override
    public void onSuccess(Object data) {
        UpdateAddress bean= (UpdateAddress) data;
        if(bean.getStatus().equals("0000")){
            Toast.makeText(this,bean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    //三级联动
    private void showPicker() {
        CityPicker cityPicker = new CityPicker.Builder(UpdateAddressActivity.this)
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
                updateAddress.setText(province+" "+city+" "+district+" ");
            }
        });
    }
    @Override
    public void onFiled(String e) {

    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void setEdit(UpdateAddressBean bean){
        id= bean.getId();
        name = bean.getName();
        address = bean.getPhone();
        phone = bean.getAddress();
        code = bean.getCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersentermpl.onDestory();
    }
}
