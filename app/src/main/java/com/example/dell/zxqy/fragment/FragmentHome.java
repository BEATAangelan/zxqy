package com.example.dell.zxqy.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.activity.DetailsActivity;
import com.example.dell.zxqy.goods.activity.HotShowActivity;
import com.example.dell.zxqy.goods.activity.MasigActivity;
import com.example.dell.zxqy.goods.activity.QualityActivity;
import com.example.dell.zxqy.goods.adapter.HomeViewPagerAdpter;
import com.example.dell.zxqy.goods.adapter.HotAdapter;
import com.example.dell.zxqy.goods.adapter.MagicAdapter;
import com.example.dell.zxqy.goods.adapter.OneAdapter;
import com.example.dell.zxqy.goods.adapter.PagerTransformer;
import com.example.dell.zxqy.goods.adapter.QualityAdapter;
import com.example.dell.zxqy.goods.adapter.SearchAdapter;
import com.example.dell.zxqy.goods.adapter.TwoAdapter;
import com.example.dell.zxqy.goods.adapter.TwoSearchAdapter;
import com.example.dell.zxqy.goods.bean.BannerBean;
import com.example.dell.zxqy.goods.bean.One;
import com.example.dell.zxqy.goods.bean.SearchBean;
import com.example.dell.zxqy.goods.bean.Shop;
import com.example.dell.zxqy.goods.bean.Two;
import com.example.dell.zxqy.goods.bean.TwoSearch;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * FragmentHome
 * @author njx
 * @Time 2019-2-21
 */
public class FragmentHome extends BaseFragment implements IView {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private IPersentermpl iPersentermpl;
    private HomeViewPagerAdpter adapter;
    private List<BannerBean.ResultBean> result;
    private Unbinder bind;
    private List<String> list;
    private int currentItem;
    //弹框
    private PopupWindow popupWindow;
    @BindView(R.id.switchover)
    ImageView switchover;
    //输入框
    @BindView(R.id.find_edit)
    EditText find_edit;
    //搜索
    @BindView(R.id.search_text)
    TextView search_text;
    //热销商品
    @BindView(R.id.spot_hot)
    TextView spot;
    //查找
    @BindView(R.id.glass)
    ImageView glass;
    //品质生活
    private MagicAdapter magicadapter;
    @BindView(R.id.spot_quality)
    TextView spot_quality;
    //滑动
    @BindView(R.id.score)
    ScrollView scrollView;
    //搜索页面
    @BindView(R.id.search_recycle)
    XRecyclerView search_recycle;
    //魔力商品
    @BindView(R.id.spot_magic)
    TextView spot_magic;
    @BindView(R.id.recyc)
    RecyclerView hotrecyclerView;
    @BindView(R.id.recyc_mgci)
    RecyclerView recyc_mgci;
    @BindView(R.id.recyc_quality)
    RecyclerView recyc_quality;
    @BindView(R.id.show_linear)
    LinearLayout  show_linear;
    @BindView(R.id.search_linear)
    LinearLayout  search_linear;
    @BindView(R.id.back_img)
    ImageView back_img;
    RecyclerView top_recyc;
    RecyclerView bottom_recyc;
    RelativeLayout re;
    private HotAdapter hotadapter;
    //查询的adapter
    private SearchAdapter searchadapter;
    //魔力时尚
    private QualityAdapter qualityadapter;
    //一级联动适配器
    private OneAdapter oneAdapter;
    //二级联动适配器
    private TwoAdapter twoAdapter;
    private TwoSearchAdapter twoSearchAdapter;
    String name;
    int Page=1;
    //线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                sendEmptyMessageDelayed(0,2000);
            }

        }
    };
    private int id;

    @Override
    protected void initView(View view) {
        bind = ButterKnife.bind(this,view);
        iPersentermpl = new IPersentermpl(this);
        re = view.findViewById(R.id.re);
        top_recyc = view.findViewById(R.id.top_recyc);
        bottom_recyc = view.findViewById(R.id.bottom_recyc);
    }
    @Override
    protected void intiData() {
        initbanner();
        initlayout();
        initexit();
        popuShow();
        initbottom();
        initRight();
        twoSerach();
    }
    //轮播画廊实现
    private void initbanner(){
        viewPager.setPageMargin(10);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageTransformer(true,new PagerTransformer());
        currentItem=viewPager.getCurrentItem();
        handler.sendEmptyMessageDelayed(currentItem,1000);
        iPersentermpl.getRequest(Api.Banner_path,BannerBean.class);
    }
    //主页面布局
    public void initlayout(){
        //热销布局
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        hotrecyclerView.setLayoutManager(layoutManager);
        //魔力布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyc_mgci.setLayoutManager(linearLayoutManager);
        //品质生活
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyc_quality.setLayoutManager(layoutManager1);
        iPersentermpl.getRequest(Api.Show_path,Shop.class);
    }
    //判断输入框里面的内容
    public void initexit(){
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               show_linear.setVisibility(View.INVISIBLE);
               search_linear.setVisibility(View.VISIBLE);
               scrollView.setVisibility(View.INVISIBLE);
               search_recycle.setVisibility(View.VISIBLE);
               re.setVisibility(View.INVISIBLE);
            }
        });
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.INVISIBLE);
                search_recycle.setVisibility(View.INVISIBLE);
                re.setVisibility(View.INVISIBLE);
                if(find_edit.getText().toString().length()==0){
                    show_linear.setVisibility(View.VISIBLE);
                    search_linear.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    search_recycle.setVisibility(View.INVISIBLE);
                    re.setVisibility(View.INVISIBLE);
                }else {
                    initsearch();
                }
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_linear.setVisibility(View.VISIBLE);
                search_linear.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                search_recycle.setVisibility(View.INVISIBLE);
                re.setVisibility(View.INVISIBLE);
                search_recycle.removeView(search_recycle);
                find_edit.setText("");
            }
        });
    }
    //实现查找
    public void initsearch(){
        name = find_edit.getText().toString();
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        search_recycle.setLayoutManager(layoutManager1);
        searchadapter = new SearchAdapter(getActivity());
        search_recycle.setLoadingMoreEnabled(true);
        search_recycle.setPullRefreshEnabled(true);
        search_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Page=1;
                iPersentermpl.getRequest(String.format(Api.Search_path,name,Page),SearchBean.class);
            }

            @Override
            public void onLoadMore() {
                iPersentermpl.getRequest(String.format(Api.Search_path,name,Page),SearchBean.class);
            }
        });
        iPersentermpl.getRequest(String.format(Api.Search_path,name,Page),SearchBean.class);
        search_recycle.setAdapter(searchadapter);
    }
    //点击选择图片弹出popupwindow
    private void popuShow(){
        // 用于PopupWindow的View
        View contentView=LayoutInflater.from(getActivity()).inflate(R.layout.switchover_layout, null, false);
        top_recyc = contentView.findViewById(R.id.top_recyc);
        bottom_recyc = contentView.findViewById(R.id.bottom_recyc);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色
        ColorDrawable dw = new ColorDrawable(getActivity().getResources().getColor(R.color.coloroo));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        switchover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v,0,25);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                oneAdapter=new OneAdapter(getActivity());
                top_recyc.setLayoutManager(linearLayoutManager);
                top_recyc.setAdapter(oneAdapter);
                iPersentermpl.getRequest(Api.One_path,One.class);
                oneAdapter.setOnClick(new OneAdapter.OnClick() {
                    @Override
                    public void Click(String commodityId) {
                        getData(commodityId);
                    }
                });
            }
        });
    }
    //点击热销商品跳转
    @OnClick(R.id.spot_hot)
    public void hotShow(){
        Intent intent = new Intent(getActivity(),HotShowActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    //点击魔力时尚跳转
    @OnClick(R.id.spot_magic)
    public void magisShow(){
        Intent intent = new Intent(getActivity(),MasigActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    //点击品质跳转
    @OnClick(R.id.spot_quality)
    public void quShow(){
        Intent intent = new Intent(getActivity(),QualityActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    private void getData(String uid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("cid",uid);
        iPersentermpl.getRequest( String.format(Api.Two_path,uid),Two.class);
    }
    //二级联动上面
    private void initRight() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        bottom_recyc.setLayoutManager(linearLayoutManager);
        bottom_recyc.setAdapter(twoAdapter);
        twoAdapter=new TwoAdapter(getActivity());
        bottom_recyc.setAdapter(twoAdapter);
        Map<String,String> map=new HashMap<>();
        map.put("cid",1+"");
    }
    //二级联动下面
    private void initbottom(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        bottom_recyc.setLayoutManager(linearLayoutManager);
        twoAdapter=new TwoAdapter(getActivity());
        bottom_recyc.setAdapter(twoAdapter);
    }
    //根据二级联动查找
    private void twoSerach(){
        twoAdapter.setOnClick(new TwoAdapter.OnItemClick() {
            @Override
            public void Click(final String id) {
                Page=1;
                Toast.makeText(getActivity(),id+"",Toast.LENGTH_SHORT).show();
                GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);
                layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                search_recycle.setLayoutManager(layoutManager1);
                search_recycle.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                search_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        Page = 1;
                        iPersentermpl.getRequest(String.format(Api.TwoSeache_path,id,Page,5),TwoSearch.class);
                        search_recycle.refreshComplete();
                    }
                    @Override
                    public void onLoadMore() {
                        iPersentermpl.getRequest(String.format(Api.TwoSeache_path,id,Page,5),TwoSearch.class);
                        search_recycle.loadMoreComplete();
                    }
                });
                iPersentermpl.getRequest(String.format(Api.TwoSeache_path,id,Page,5),TwoSearch.class);
                twoSearchAdapter=new TwoSearchAdapter(getActivity());
                search_recycle.setAdapter(twoSearchAdapter);
            }
        });
    }
    @Override
    protected int getViewLayout() {
        return R.layout.fragmenthome;
    }

    @Override
    public void onSuccess(Object data) {
        //banner的数据
        if (data instanceof BannerBean) {
            BannerBean bean = (BannerBean) data;
            List<BannerBean.ResultBean> result = bean.getResult();
            adapter = new HomeViewPagerAdpter(result, getContext());
            viewPager.setAdapter(adapter);
        }
        if (data instanceof Shop) {
            //热销商品的方法
            Shop beanhot = (Shop) data;
            Shop.ResultBean result = beanhot.getResult();
            Shop.ResultBean.RxxpBean rxxp = result.getRxxp();
            List<Shop.ResultBean.RxxpBean.CommodityListBean> commodityList = beanhot.getResult().getRxxp().getCommodityList();
            hotadapter = new HotAdapter(getActivity());
            hotrecyclerView.setAdapter(hotadapter);
            this.id = rxxp.getId();;
            hotadapter.setList(commodityList);
            hotadapter.setOnClick(new HotAdapter.OnClick() {
                @Override
                public void Click(String id) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
            //魔力时尚
            List<Shop.ResultBean.MlssBean.CommodityListBeanXX> mlss = beanhot.getResult().getMlss().getCommodityList();
            magicadapter = new MagicAdapter(getActivity());
            recyc_mgci.setAdapter(magicadapter);
            magicadapter.setList(mlss);
            magicadapter.setOnClick(new MagicAdapter.OnClick() {
                @Override
                public void Click(String id) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
            //品质生活
            List<Shop.ResultBean.PzshBean.CommodityListBeanX> pzsh = beanhot.getResult().getPzsh().getCommodityList();
            qualityadapter = new QualityAdapter(getActivity(), pzsh);
            recyc_quality.setAdapter(qualityadapter);
            qualityadapter.setOnClick(new QualityAdapter.OnClick() {
                @Override
                public void Click(String id) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        }
        if(data instanceof SearchBean){
            SearchBean bean = (SearchBean) data;
            List<SearchBean.ResultBean> result = bean.getResult();
            if (!result.isEmpty()) {
                search_recycle.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    re.setVisibility(View.INVISIBLE);
                    if (Page == 1) {
                    searchadapter.setList(result);
                } else {
                    searchadapter.addList(result);
                }

                Page++;
                search_recycle.loadMoreComplete();
                search_recycle.refreshComplete();
                return;
            }
            search_recycle.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            re.setVisibility(View.VISIBLE);
        }
        if(data instanceof One){
            One bean= (One) data;
            List<One.ResultBean> result = bean.getResult();
            oneAdapter.setList(result);
            oneAdapter.notifyDataSetChanged();
        }
        if(data instanceof Two){
            Two bean= (Two) data;
            List<Two.ResultBean> result = bean.getResult();
            twoAdapter.setList(result);
        }
        if (data instanceof TwoSearch){
            TwoSearch bean = (TwoSearch) data;
             List<TwoSearch.ResultBean> resultb = bean.getResult();
            if (!resultb.isEmpty()) {
                search_recycle.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                re.setVisibility(View.INVISIBLE);
                if (Page == 1) {
                    twoSearchAdapter.setList(resultb);
                } else {
                    twoSearchAdapter.addList(resultb);
                }
                Page++;
                search_recycle.loadMoreComplete();
                search_recycle.refreshComplete();
                return;
            }
        }
    }
    @Override
    public void onFiled(String e) {
        Toast.makeText(getActivity(),""+"网络请求失败",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        handler.removeCallbacksAndMessages(null);
        iPersentermpl.onDestory();
    }
}
