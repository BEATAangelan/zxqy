package com.example.dell.zxqy.form.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.zxqy.R;
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
import butterknife.Unbinder;

public class FragmentTakes extends Fragment implements IView {
    @BindView(R.id.allordersRecycle)
    RecyclerView allordersRecycle;
    Unbinder unbinder;
    private IPersentermpl persenter;
    private StringAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentall, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        persenter=new IPersentermpl(this);
        allordersRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        adapter=new StringAdapter(getActivity(),0);
        allordersRecycle.setAdapter(adapter);
        adapter.setShopCarListener(new StringAdapter.ShopCarListener() {
            @Override
            public void callBack(OrderBeano.OrderListBean list,String price) {
                Map<String,String> map=new HashMap<>();
                map.put("orderId",list.getOrderId());
                map.put("payType",1+"");
                persenter.putRequest(Api.PAY_SHOP,PaySuccess.class,map);
            }
        });
        adapter.notifyDataSetChanged();
        loadData();
    }

    private void loadData() {
        persenter.getRequest(String.format(Api.INDENT_FIND,0,1),OrderBeano.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof OrderBeano){
            OrderBeano bean= (OrderBeano) data;
            if (bean==null){
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
            }else {
                adapter.setList(bean.getOrderList());
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
        if (data instanceof PaySuccess){
            PaySuccess regBean= (PaySuccess) data;
            Toast.makeText(getActivity(),regBean.getMessage(),Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    @Override
    public void onFiled(String e) {

    }
}
