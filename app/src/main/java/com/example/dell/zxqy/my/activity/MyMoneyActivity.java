package com.example.dell.zxqy.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.adapter.MoneyAdapter;
import com.example.dell.zxqy.my.bean.MoneyBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMoneyActivity extends AppCompatActivity implements IView {

    @BindView(R.id.money_image)
    ImageView moneyImage;
    @BindView(R.id.money_yue)
    TextView moneyYue;
    @BindView(R.id.money_qian)
    TextView moneyQian;
    @BindView(R.id.money_xiaofei)
    TextView moneyXiaofei;
    @BindView(R.id.money_shijain)
    TextView moneyShijain;
    @BindView(R.id.money_lin)
    LinearLayout moneyLin;
    @BindView(R.id.heng)
    View heng;
    private  boolean money_flag;
    @BindView(R.id.money_xrecy)
    XRecyclerView moneyXrecy;
    IPersentermpl iPersentermpl;
    private MoneyAdapter moneyAdapter;
    int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        ButterKnife.bind(this);
        iPersentermpl=new IPersentermpl(this);
        initRecy();
    }
    private void initRecy() {
        page=1;
        //布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        moneyXrecy.setLayoutManager(linearLayoutManager);
        //允许刷新和加载
        moneyXrecy.setLoadingMoreEnabled(true);
        moneyXrecy.setPullRefreshEnabled(true);
        moneyAdapter = new MoneyAdapter(this);
        moneyXrecy.setAdapter(moneyAdapter);
        moneyXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initUrl(page);
            }

            @Override
            public void onLoadMore() {
                if (money_flag){
                    moneyXrecy.loadMoreComplete();
                }else{
                    moneyXrecy.setLoadingMoreEnabled(true);
                }
                initUrl(page);
            }
        });
        initUrl(page);
    }

    private void initUrl(int page) {
        iPersentermpl.getRequest(String.format(Api.SHOW_SELECT_MONEY_URL,page),MoneyBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof MoneyBean){
            MoneyBean moneyBean= (MoneyBean) data;
            moneyQian.setText(moneyBean.getResult().getBalance()+"");
            if (page==1){
                moneyAdapter.setList(moneyBean.getResult().getDetailList());
            }else{
                moneyAdapter.addList(moneyBean.getResult().getDetailList());
            }
            //停止刷新加载
            moneyXrecy.refreshComplete();
            moneyXrecy.loadMoreComplete();
            page++;
            if (moneyBean.getResult().getDetailList().size()==0){
                money_flag=true;
            }else{
                money_flag=false;
            }
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
