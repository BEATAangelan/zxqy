package com.example.dell.zxqy.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.adapter.FootAdapter;
import com.example.dell.zxqy.my.bean.MyFootBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFootActivity extends AppCompatActivity implements IView {

    @BindView(R.id.myfoot_xrecycle)
    XRecyclerView xRecyclerView;
    FootAdapter footAdapter;
    private Unbinder unbinder;
    IPersentermpl iPersentermpl;
    int Page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot);
        unbinder = ButterKnife.bind(this);
        iPersentermpl = new IPersentermpl(this);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        linear();
    }
    private void init(){
        iPersentermpl.getRequest(String.format(Api.MyFootPath,Page),MyFootBean.class);
    }
    private void linear(){
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        footAdapter = new FootAdapter(this);
        xRecyclerView.setAdapter(footAdapter);
    }
    @Override
    public void onSuccess(Object data) {
        MyFootBean bean= (MyFootBean) data;
        List<MyFootBean.ResultBean> result = bean.getResult();
        if(Page==1){
            footAdapter.setList(result);
        }else {
            footAdapter.addList(result);
        }
        Page++;
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
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
