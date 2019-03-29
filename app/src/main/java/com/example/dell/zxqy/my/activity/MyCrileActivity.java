package com.example.dell.zxqy.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.circle.adapter.CircleAdapter;
import com.example.dell.zxqy.circle.bean.Circle;
import com.example.dell.zxqy.circle.bean.RegBean;
import com.example.dell.zxqy.my.adapter.MyCircleAdapter;
import com.example.dell.zxqy.my.bean.MyCircler;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCrileActivity extends AppCompatActivity implements IView {
    IPersentermpl iPersentermpl;
    MyCircleAdapter circleAdapter;
    int Page=1;
    Unbinder unbinder;
    @BindView(R.id.circle_xrecycleView)
    XRecyclerView xRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_crile);
        unbinder = ButterKnife.bind(this);
        iPersentermpl = new IPersentermpl(this);
        xRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition
                        = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();

            }


        });
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
        init1();
    }
    private void init() {
        iPersentermpl.getRequest(String.format(Api.MyCirlePath,Page),MyCircler.class);
    }
    private void init1(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        circleAdapter = new MyCircleAdapter(this);
        xRecyclerView.setAdapter(circleAdapter);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof MyCircler) {
            MyCircler bean = (MyCircler) data;
            List<MyCircler.ResultBean> result = bean.getResult();
            if (Page == 1) {
                circleAdapter.setList(result);
            } else {
                circleAdapter.addList(result);
            }
            Page++;
            xRecyclerView.loadMoreComplete();
            xRecyclerView.refreshComplete();
        }
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
