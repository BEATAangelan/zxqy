package com.example.dell.zxqy.form.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.adapter.StringAdapter;
import com.example.dell.zxqy.form.bean.OrderBeano;
import com.example.dell.zxqy.form.bean.PaySuccess;
import com.example.dell.zxqy.form.activity.PayActivity;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentPay extends BaseFragment implements IView {
    @BindView(R.id.allordersRecycle)
    RecyclerView allordersRecycle;
    Unbinder unbinder;
    private StringAdapter adapter;
    IPersentermpl persenter;
    @Override
    protected void intiData() {
        persenter = new IPersentermpl(this);
        allordersRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new StringAdapter(getActivity(),1);
        allordersRecycle.setAdapter(adapter);
        loadData();
        adapter.setShopCarListener(new StringAdapter.ShopCarListener() {
            @Override
            public void callBack(OrderBeano.OrderListBean list,String all_price) {
//                Map<String, String> map = new HashMap<>();
 //              map.put("orderId", list.getOrderId());
//                map.put("payType", 1 + "");
//                persenter.postRequest(Api.PAY_SHOP,PaySuccess.class,map);
                Intent intent=new Intent(getActivity(),PayActivity.class);
                intent.putExtra("orderId",list.getOrderId());
               intent.putExtra("all_price",all_price);
                startActivity(intent);
            }
        });
        loadData();
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragmentpay;
    }
    private void loadData() {
        persenter.getRequest(String.format(Api.INDENT_FIND,1,1), OrderBeano.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        intiData();
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof OrderBeano) {
            OrderBeano bean = (OrderBeano) data;
            if (bean == null) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.setList(bean.getOrderList());
            }
        }
        if (data instanceof PaySuccess) {
            PaySuccess regBean = (PaySuccess) data;
            Toast.makeText(getActivity(), regBean.getMessage(), Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    @Override
    public void onFiled(String e) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        persenter.onDestory();
        unbinder.unbind();
    }
}
