package com.example.dell.zxqy.goods.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.adapter.MagicxAdapter;
import com.example.dell.zxqy.goods.bean.Magic;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class MasigActivity extends AppCompatActivity implements IView {
    private XRecyclerView ma_recycler;
    private MagicxAdapter magicxAdapter;
    IPersentermpl iPersentermpl;
    private int Page=1;
    String path="commodity/v1/findCommodityListByLabel?labelId=%d&page=%d&count=%d";
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masig);
        ma_recycler = findViewById(R.id.ma_recycler);
        Page=1;
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        magicxAdapter=new MagicxAdapter(this);
        ma_recycler.setAdapter(magicxAdapter);
        ma_recycler.setLayoutManager(layoutManager);
        ma_recycler.setPullRefreshEnabled(true);
        ma_recycler.setLoadingMoreEnabled(true);
        ma_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Page=1;
                init();
            }
            @Override
            public void onLoadMore() {
                init();
            }
        });
        init();
    }
    private void init(){
        iPersentermpl = new IPersentermpl(this);
        iPersentermpl.getRequest(String.format(Api.ShowAll,id,Page,5),Magic.class);

    }

    @Override
    public void onSuccess(Object data) {
        Magic bean= (Magic) data;
        List<Magic.ResultBean> result = bean.getResult();
        if (Page==1){
            magicxAdapter.setList(result);
        }
        else {
            magicxAdapter.addList(result);
        }
        Page++;
        ma_recycler.loadMoreComplete();
        ma_recycler.refreshComplete();
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
