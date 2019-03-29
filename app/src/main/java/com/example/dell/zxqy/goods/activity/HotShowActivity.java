package com.example.dell.zxqy.goods.activity;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.adapter.ClassifyAdapter;
import com.example.dell.zxqy.goods.bean.Hot;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotShowActivity extends AppCompatActivity implements IView {
    @BindView(R.id.hot_recycler)
    XRecyclerView hot_recycler;
    private ClassifyAdapter classifyAdapter;
    IPersentermpl iPersentermpl;
    private int Page=1;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_show);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        classifyAdapter=new ClassifyAdapter(this);
        hot_recycler.setLayoutManager(layoutManager);
        hot_recycler.setPullRefreshEnabled(true);
        hot_recycler.setLoadingMoreEnabled(true);
        hot_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        hot_recycler.setAdapter(classifyAdapter);
    }
    private void init() {
        iPersentermpl = new IPersentermpl(this);
        iPersentermpl.getRequest(String.format(Api.ShowAll,id,Page,5), Hot.class);
    }
    @Override
    public void onSuccess(Object data) {
        Hot bean= (Hot) data;
        List<Hot.ResultBean> result = bean.getResult();
        if (Page==1){
            classifyAdapter.setList(result);
        }
        else {
            classifyAdapter.addList(result);
        }
        Page++;
        hot_recycler.loadMoreComplete();
        hot_recycler.refreshComplete();
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
