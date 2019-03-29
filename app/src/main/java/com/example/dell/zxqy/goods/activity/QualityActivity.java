package com.example.dell.zxqy.goods.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.adapter.QualiAdapter;
import com.example.dell.zxqy.goods.bean.Quality;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class QualityActivity extends AppCompatActivity implements IView {
    private XRecyclerView qu_recycler;
    private QualiAdapter qualiAdapter;
    IPersentermpl mPersentermpl;
    private int Page=1;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality);
        qu_recycler = findViewById(R.id.qu_recycler);
        Page=1;
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        qualiAdapter=new QualiAdapter(this);
        qu_recycler.setAdapter(qualiAdapter);
        qu_recycler.setLayoutManager(layoutManager);
        qu_recycler.setPullRefreshEnabled(true);
        qu_recycler.setLoadingMoreEnabled(true);
        qu_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        mPersentermpl = new IPersentermpl(this);
        mPersentermpl.getRequest(String.format(Api.ShowAll,id,Page,5),Quality.class);

    }

    @Override
    public void onSuccess(Object data) {
        Quality bean= (Quality) data;
        List<Quality.ResultBean> result = bean.getResult();
        if (Page==1){
            qualiAdapter.setList(result);
        }
        else {
            qualiAdapter.addList(result);
        }
        Page++;
        qu_recycler.loadMoreComplete();
        qu_recycler.refreshComplete();
    }

    @Override
    public void onFiled(String e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersentermpl.onDestory();
    }
}

