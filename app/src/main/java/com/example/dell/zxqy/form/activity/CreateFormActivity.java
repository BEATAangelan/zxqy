package com.example.dell.zxqy.form.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.adapter.PopAdapter;
import com.example.dell.zxqy.form.adapter.ResultAdapter;
import com.example.dell.zxqy.form.bean.AddressListBean;
import com.example.dell.zxqy.form.bean.CreatFormBean;
import com.example.dell.zxqy.form.bean.CreatShopBean;
import com.example.dell.zxqy.form.bean.ShopAddressBean;
import com.example.dell.zxqy.goods.bean.GoodsShapping;
import com.example.dell.zxqy.my.bean.MoAddress;
import com.example.dell.zxqy.my.bean.shopAddressBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateFormActivity extends AppCompatActivity implements IView {

    @BindView(R.id.goods_address_name)
    TextView tName;
    @BindView(R.id.goods_address_phone)
    TextView tPhone;
    @BindView(R.id.goods_address)
    TextView tAddress;
    @BindView(R.id.address_drop_down)
    ImageView imgDropDown;
    @BindView(R.id.addre)
    RelativeLayout showAdd;
    @BindView(R.id.addre_no)
    RelativeLayout hideAdd;
    @BindView(R.id.show_goods)
    RecyclerView show_goods;
    @BindView(R.id.scll)
    ScrollView scll;
    @BindView(R.id.address_describe_text)
    TextView address_describe_text;
    @BindView(R.id.address_describe_num)
    TextView address_describe_num;
    @BindView(R.id.address_describe_text1)
    TextView addressDescribeText1;
    @BindView(R.id.address_describe_price)
    TextView address_describe_price;
    @BindView(R.id.btn_commit_goods)
    Button btn_commit_goods;
    @BindView(R.id.pop_rv)
    RecyclerView popRv;
    @BindView(R.id.pop_show)
    RelativeLayout relativeLayoutPop;
    RecyclerView pop_recy;
    private int num = 0;
    private boolean isShow;
    private PopupWindow popupWindow;
    private double goodsPrice = 0.00;
    IPersentermpl iPersentermpl;
    PopAdapter popAdapter;
    private List<GoodsShapping.ResultBean> list;
    private shopAddressBean shopAddressBean;
    private ResultAdapter resultAdapter;
    private int addressId;
    private Unbinder bind;
    private List<com.example.dell.zxqy.my.bean.shopAddressBean.ResultBean> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);
        bind = ButterKnife.bind(this);
        iPersentermpl = new IPersentermpl(this);
        Intent intent = getIntent();
        list = (List<GoodsShapping.ResultBean>) intent.getSerializableExtra("form");
        initAddress();
        initPoPupwindow();
        commitGoods();
        showGoods();
        initPopRecy();
    }
    private void initPoPupwindow() {
        // 用于PopupWindow的View
        View contentView=LayoutInflater.from(this).inflate(R.layout.creation_bill_pop, null, false);
        pop_recy = contentView.findViewById(R.id.pop_creation_recy);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
       /* // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);*/
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.coloroo));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        //点击弹出
        imgDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.showAsDropDown(v,0,26);

                imgDropDown.setSelected(!imgDropDown.isSelected());

            }
        });
        popupWindow.dismiss();

    }

    //点击弹出pop
    private void initPopRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        pop_recy.setLayoutManager(linearLayoutManager);
        popAdapter = new PopAdapter(this);
        popAdapter.setChange(new PopAdapter.ClickChange() {
            @Override
            public void onClick(int id) {
                Map<String,String> params=new HashMap<>();
                params.put("id",String.valueOf(id));
                iPersentermpl.postRequest(Api.MoPath,MoAddress.class,params);
            }
        });
    }
    public void initAddress(){
        iPersentermpl.getRequest(Api.SearchAddessPath,shopAddressBean.class);
    }


    public void commitGoods() {
        btn_commit_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CreatShopBean> listCreat = new ArrayList<>();
                LinearLayoutManager layoutManager = new LinearLayoutManager(CreateFormActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                show_goods.setLayoutManager(layoutManager);
                for (GoodsShapping.ResultBean bean : list) {
                    CreatShopBean creatShopBean = new CreatShopBean();
                    creatShopBean.setAmount(bean.getCount());
                    creatShopBean.setCommodityId(bean.getCommodityId());
                    listCreat.add(creatShopBean);
                }
                String json = new Gson().toJson(listCreat);
                Map<String, String> map = new HashMap<>();
                map.put("orderInfo", json);
                map.put("totalPrice", goodsPrice + "");
                map.put("addressId", result.get(0).getId() + "");

                iPersentermpl.postRequest(Api.CreatFrom, CreatFormBean.class, map);
            }
        });
    }

    private void showGoods() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        show_goods.setLayoutManager(layoutManager);
        resultAdapter = new ResultAdapter(this, list);
        show_goods.setAdapter(resultAdapter);
        for (int i = 0; i < list.size(); i++) {
            int count = list.get(i).getCount();
            num += count;
            goodsPrice += list.get(i).getPrice() * list.get(i).getCount();
        }
        //底部商品描述
        address_describe_num.setText(num + "");
        address_describe_price.setText(goodsPrice + "");
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof shopAddressBean) {
            //查询地址
            shopAddressBean bean = (shopAddressBean) data;
            result = bean.getResult();
            popAdapter.setData(result);
            pop_recy.setAdapter(popAdapter);
            for (int i = 0; i< result.size(); i++){
                if(bean.getResult().get(i).getWhetherDefault()==1){
                    tName.setText(result.get(i).getRealName());
                    tPhone.setText(result.get(i).getPhone());
                    tAddress.setText(result.get(i).getAddress());
                    AddressListBean addressListBean = new Gson().fromJson(result.get(i).getAddress(), AddressListBean.class);
                    tAddress.setText(addressListBean.getCity()+addressListBean.getAddress());
                    addressId= result.get(i).getId();
                }
            }

        }
        if (data instanceof CreatFormBean) {
            //判断订单的状态
            CreatFormBean bean = (CreatFormBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(CreateFormActivity.this, bean.getMessage().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Log.i("ADDRESS", bean.getMessage().toString());
                Toast.makeText(this, "创建失败", Toast.LENGTH_SHORT).show();
            }
        }if (data instanceof MoAddress){
            MoAddress bean= (MoAddress) data;
            if (bean.getStatus().equals("0000")){
                Toast.makeText(this,bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
            initAddress();
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
