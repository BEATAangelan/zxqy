package com.example.dell.zxqy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.activity.CreateFormActivity;
import com.example.dell.zxqy.goods.adapter.ShoppingCarAdapter;
import com.example.dell.zxqy.goods.bean.GoodsShapping;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.example.dell.zxqy.user.activity.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentCar extends BaseFragment implements IView {
    private ShoppingCarAdapter shoppingCarAdapter;
    private RecyclerView shoprecyclerView;
    private IPersentermpl iPersentermpl;
    private TextView money;
    CheckBox checkAll;
    Button button_sum;
    double totalprice=0;
    //查询路径
    private GoodsShapping bean;
    private List<GoodsShapping.ResultBean> result;
    @Override
    protected void intiData() {
        init();
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issetCheck(checkAll.isChecked());
            }
        });
        getFocus();
    }
    private void init(){
        iPersentermpl=new IPersentermpl(this);
        iPersentermpl.getRequest(Api.SearchCarPath,GoodsShapping.class);
        shoppingCarAdapter=new ShoppingCarAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shoprecyclerView.setLayoutManager(linearLayoutManager);
        shoprecyclerView.setAdapter(shoppingCarAdapter);
        shoppingCarAdapter.setShopCarListener(new ShoppingCarAdapter.ShopCarListener() {
            @Override
            public void callBack(List<GoodsShapping.ResultBean> list) {
                int num=0;
                int goodsnum=0;
                double price=0;
                for (int i=0;i<list.size();i++){
                    num+=list.get(i).getCount();
                    if (list.get(i).isIscheck()){
                        price+=result.get(i).getPrice()*result.get(i).getCount();
                        totalprice+=list.get(i).getCount()*list.get(i).getPrice();
                        goodsnum+=list.get(i).getCount();
                    }
                }
                money.setText(price+"");
                if(num==goodsnum){
                    checkAll.setChecked(true);
                }else
                {
                    checkAll.setChecked(false);
                }
            }

            @Override
            public void delItem(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("删除")
                        .setMessage("确定要删除吗")
                        .setIcon(R.drawable.ic_launcher_background)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                shoppingCarAdapter.delData(position);
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.show();
            }
        });
    }
    //全选框
    private void issetCheck(boolean b){
        int price=0;
        for (int i=0;i<result.size();i++){
           price+=result.get(i).getPrice()*result.get(i).getCount();
           result.get(i).setIscheck(b);
        }
        shoppingCarAdapter.setList(result);
        if(b){
           money.setText("¥:"+price);
        }
        else {
            money.setText("¥:"+0.0);
        }
    }

    @OnClick(R.id.button_sum)
    public  void setsumbut(){
        List<GoodsShapping.ResultBean> form=new ArrayList<>();
        for (int i=0;i<result.size();i++){
            if (result.get(i).isIscheck()){
                form.add(new GoodsShapping.ResultBean(
                        result.get(i).getCommodityId(),
                        result.get(i).getCommodityName(),
                        result.get(i).getCount(),
                        result.get(i).getPic(),
                        result.get(i).getPrice(),
                        result.get(i).getIscheck()
                ));
            }
        }
        Intent intent = new Intent(getActivity(), CreateFormActivity.class);
        intent.putExtra("form", (Serializable) form);
        startActivity(intent);
    }
    @Override
    protected void initView(View view) {
        shoprecyclerView = view.findViewById(R.id.shoprecycler);
        checkAll = view.findViewById(R.id.checkAll);
        button_sum = view.findViewById(R.id.button_sum);
        money = view.findViewById(R.id.money);
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getViewLayout() {

        return R.layout.fragmentcar;
    }

    @Override
    public void onSuccess(Object data) {
    //查询购物车
        if(data instanceof GoodsShapping) {
            bean = (GoodsShapping) data;
            result = bean.getResult();
            shoppingCarAdapter.setList(result);
        }
        shoppingCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFiled(String e) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersentermpl.onDestory();
    }
    private long exitTime=0;
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }
}
