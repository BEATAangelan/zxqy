package com.example.dell.zxqy.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.circle.adapter.CircleAdapter;
import com.example.dell.zxqy.circle.bean.Circle;
import com.example.dell.zxqy.circle.bean.RegBean;
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

public class FragmentCircle extends BaseFragment implements IView {
    IPersentermpl iPersentermpl;
    CircleAdapter circleAdapter;
    int Page=1;
    Unbinder unbinder;
    @BindView(R.id.circle_xrecycle)
    XRecyclerView circle_xrecycle;
    @Override
    protected void initView(View view) {
        circle_xrecycle = view.findViewById(R.id.circle_xrecycle);
        unbinder = ButterKnife.bind(this, view);

    }
    @Override
    protected void intiData()
    {
        //实例化
        iPersentermpl = new IPersentermpl(this);
        linear();
        circle_xrecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //计算Recyclerview第一个item的位置是否可见
                int topRowVerticalPosition
                        = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();

                //当第一个item不可见时，设置SwipeRefreshLayout不可用
              //  mSwipeRefreshLayout.setPullRefreshEnable(topRowVerticalPosition >= 0);
            }


        });
        circle_xrecycle.setPullRefreshEnabled(true);
        circle_xrecycle.setLoadingMoreEnabled(true);
        circle_xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
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
    @Override
    protected int getViewLayout()
    {
        return R.layout.fragmentcircle;
    }
    //进行网络加载
    private void init(){
        iPersentermpl.getRequest(String.format(Api.Circle_path,Page),Circle.class);
    }
    //布局管理器
    private void linear(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        circle_xrecycle.setLayoutManager(layoutManager);
        circleAdapter = new CircleAdapter(getActivity());
        circleAdapter.setGreatClick(new CircleAdapter.GreatClick() {
            @Override
            public void click(int circleId, boolean isGreat) {
                Map<String,String> map=new HashMap<>();
                map.put("circleId",circleId+"");
                if (isGreat){
                    iPersentermpl.postRequest(Api.GreatPath,RegBean.class,map);
                }else {
                    iPersentermpl.deleteRequest(Api.NoGreatPath,RegBean.class);
                }
                circleAdapter.notifyDataSetChanged();

            }
        });
        circle_xrecycle.setAdapter(circleAdapter);
    }
    @Override
    public void onSuccess(Object data) {
        if (data instanceof Circle) {
            Circle bean = (Circle) data;
            List<Circle.ResultBean> result = bean.getResult();
            if (Page == 1) {
                circleAdapter.setList(result);
            } else {
                circleAdapter.addList(result);
            }
            Page++;
            circle_xrecycle.loadMoreComplete();
            circle_xrecycle.refreshComplete();
        }

    if(data instanceof RegBean){
        RegBean regBean= (RegBean) data;
        Toast.makeText(getActivity(),regBean.getMessage(),Toast.LENGTH_SHORT).show();
      }
    }
    @Override
    public void onFiled(String e) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersentermpl.onDestory();
    }
}
