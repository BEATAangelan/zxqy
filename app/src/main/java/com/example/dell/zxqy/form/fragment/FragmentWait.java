package com.example.dell.zxqy.form.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.circle.bean.RegBean;
import com.example.dell.zxqy.form.adapter.StringAdapter;
import com.example.dell.zxqy.form.bean.OrderBeano;
import com.example.dell.zxqy.form.bean.PaySuccess;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentWait extends BaseFragment implements IView {
    @BindView(R.id.allordersRecycle)
    RecyclerView allordersRecycle;
    private StringAdapter adapter;
    IPersentermpl persenter;

    @Override
    protected void intiData() {
        persenter = new IPersentermpl(this);
        allordersRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new StringAdapter(getActivity(),2);
        allordersRecycle.setAdapter(adapter);
        loadData();
        adapter.setShopCarListener(new StringAdapter.ShopCarListener() {
            @Override
            public void callBack(OrderBeano.OrderListBean list,String price) {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", list.getOrderId());
                persenter.putRequest(Api.OKSHOUPATH,PaySuccess.class,map);
            }
        });
        loadData();
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragmentwait;
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
        private void loadData() {
            persenter.getRequest(String.format(Api.INDENT_FIND,2,1),OrderBeano.class);
        }
    @Override
    public void onFiled(String e) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        persenter.onDestory();
    }
}
