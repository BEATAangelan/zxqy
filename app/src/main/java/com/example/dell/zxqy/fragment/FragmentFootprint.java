package com.example.dell.zxqy.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.activity.EvaluatActivity;
import com.example.dell.zxqy.form.activity.PayActivity;
import com.example.dell.zxqy.form.adapter.StringAdapter;
import com.example.dell.zxqy.form.bean.CreationMsgBean;
import com.example.dell.zxqy.form.bean.DeleteBillBean;
import com.example.dell.zxqy.form.bean.OrderBeano;
import com.example.dell.zxqy.form.bean.PaySuccess;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentFootprint extends Fragment implements View.OnClickListener,IView {
private XRecyclerView bill_xrecy;
    private IPersentermpl mIpresenterImpl;
    private int page;
    private int status=0;
    private int COUNT_ITEM=5;
    private ImageView image_allbill;
    private ImageView image_pay;
    private ImageView image_task;
    private ImageView image_appraise;
    private ImageView image_finish;
    private StringAdapter billXrecyAdapter;
    private int all_staus;
    private boolean bill_flag;
    private int index;
    private LinearLayout lin_group;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentall, container, false);
        bill_xrecy = view.findViewById(R.id.bill_xrecy);
        image_allbill = view.findViewById(R.id.bill_image_allbill);
        image_pay = view.findViewById(R.id.bill_image_pay);
        image_task = view.findViewById(R.id.bill_image_task);
        image_appraise = view.findViewById(R.id.bill_image_appraise);
        image_finish = view.findViewById(R.id.bill_image_finish);
        lin_group = view.findViewById(R.id.bill_group);
        image_allbill.setOnClickListener(this);
        image_pay.setOnClickListener(this);
        image_task.setOnClickListener(this);
        image_appraise.setOnClickListener(this);
        image_finish.setOnClickListener(this);
        //注册
        EventBus.getDefault().register(this);
        //互绑
        initPresenter();
        //初始化Xrecy
        initXrecy();
        return view;
    }
    private void initXrecy() {
        page=1;
        //布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        bill_xrecy.setLayoutManager(linearLayoutManager);
        //设置适配器
        billXrecyAdapter = new StringAdapter(getActivity());
        bill_xrecy.setAdapter(billXrecyAdapter);
        //支持刷新加载
        bill_xrecy.setPullRefreshEnabled(true);
        bill_xrecy.setLoadingMoreEnabled(true);
        bill_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initBilllUrl(all_staus,page);
            }

            @Override
            public void onLoadMore() {
                if (bill_flag){
                    bill_xrecy.loadMoreComplete();
                }else{
                    bill_xrecy.setLoadingMoreEnabled(true);
                }

                initBilllUrl(all_staus,page);
            }
        });
        initBilllUrl(status,page);

        //删除订单
        billXrecyAdapter.setDelete(new StringAdapter.ClickDelete() {
            @Override
            public void delete(final String orderId, final int position) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("确定删除订单吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIpresenterImpl.deleteRequest(String.format(Api.DelectPath,orderId),DeleteBillBean.class);
                        index = position;
                    }
                });
                builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });
        //付款
        billXrecyAdapter.setGo(new StringAdapter.ClickGo() {
            @Override
            public void go(String orderId,String all_price) {
                Intent intent=new Intent(getActivity(),PayActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("all_price",all_price);
                startActivity(intent);
            }
        });
        //确认收货
        billXrecyAdapter.setNext(new StringAdapter.ClickNext() {
            @Override
            public void next(String orderId) {
                Map<String,String> params=new HashMap<>();
                params.put("orderId",orderId);
                mIpresenterImpl.putRequest(Api.OKSHOUPATH,PaySuccess.class,params);
            }
        });
        billXrecyAdapter.setEva(new StringAdapter.ClickEvaluate() {
            @Override
            public void setEvaluat(List<OrderBeano.OrderListBean.DetailListBean> list, int position, OrderBeano.OrderListBean item, int i) {
                Intent intent = new Intent(getActivity(),EvaluatActivity.class);
                String[] split = list.get(position).getCommodityPic().split("\\,");
                intent.putExtra("image",split[0]);
                intent.putExtra("id",list.get(position).getCommodityId()+"");
                intent.putExtra("orderId",item.getOrderId());
                intent.putExtra("name",list.get(position).getCommodityName());
                intent.putExtra("price","￥"+list.get(position).getCommodityPrice());
                startActivity(intent);
            }
        });
    }
    public void onSuccess(Object data) {
        if (data instanceof OrderBeano){
            OrderBeano billShopBean= (OrderBeano) data;
            if (page==1){
                billXrecyAdapter.setList(billShopBean.getOrderList());
            }else{
                billXrecyAdapter.addList(billShopBean.getOrderList());
            }
            //停止刷新加载
            bill_xrecy.refreshComplete();
            bill_xrecy.loadMoreComplete();
            page++;
            if (billShopBean.getOrderList().size()==0){
                bill_flag=true;
            }else{
                bill_flag=false;
            }
        }
        if (data instanceof DeleteBillBean){
            DeleteBillBean deleteBillBean= (DeleteBillBean) data;
            if (deleteBillBean.getStatus().equals("0000")){
                billXrecyAdapter.deleteBill(index);
                Toast.makeText(getActivity(), deleteBillBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof PaySuccess) {
            PaySuccess nextBean= (PaySuccess) data;
            if (nextBean.getStatus().equals("0000")){
                mIpresenterImpl.getRequest(String.format(Api.OKSHOUPATH,2,page,COUNT_ITEM),OrderBeano.class);
                Toast.makeText(getActivity(), nextBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFiled(String e) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //查看全部
            case R.id.bill_image_allbill:
                status=0;
                page=1;
                initBilllUrl(status,page);
                break;
            //查询待付款
            case R.id.bill_image_pay:
                status=1;
                page=1;
                initBilllUrl(status,page);
                break;
            //查看待收货
            case R.id.bill_image_task:
                status=2;
                page=1;
                initBilllUrl(status,page);
                break;
            //查看带评价
            case R.id.bill_image_appraise:
                status=3;
                page=1;
                initBilllUrl(status,page);
                break;
            //查看已完成
            case R.id.bill_image_finish:
                status=9;
                page=1;
                initBilllUrl(status,page);
                break;
        }
    }
    //根据订单的状态查询订单
    private void initBilllUrl(int status, int page) {
        all_staus = status;
        mIpresenterImpl.getRequest(String.format(Api.INDENT_FIND,status,page,COUNT_ITEM),OrderBeano.class);
    }
    //互绑
    private void initPresenter() {
        mIpresenterImpl=new IPersentermpl(this);
    }
    //打开未支付
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getPay(CreationMsgBean msgBean){
        if (msgBean.getFlag().equals("open")){
            mIpresenterImpl.getRequest(String.format(Api.INDENT_FIND,1,page,COUNT_ITEM),OrderBeano.class);
            EventBus.getDefault().removeStickyEvent(new CreationMsgBean());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //反注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        mIpresenterImpl.onDestory();
    }
}
