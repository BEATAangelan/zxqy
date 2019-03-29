package com.example.dell.zxqy.goods.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.adapter.TalkAdapter;
import com.example.dell.zxqy.goods.bean.AddShopJsonBean;
import com.example.dell.zxqy.goods.bean.Details;
import com.example.dell.zxqy.goods.bean.Goods;
import com.example.dell.zxqy.goods.bean.GoodsShapping;
import com.example.dell.zxqy.goods.bean.Shopping;
import com.example.dell.zxqy.goods.bean.TalkBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements IView {
    Banner banner_de;
    TextView text_details;
    TextView sold;
    TextView text_details_price;
    WebView webView,showweb;
    Button button_addshop;
    Button button_buyshop;
    TalkAdapter talkAdapter;
    private IPersentermpl mPersentermpl;
    //商品详情
    private String id;
    int page=1;
    private TabLayout mTabLayout;
    private RecyclerView mRecyclerView;
    private XRecyclerView talk_xrecycle;
    private LinearLayoutManager mManager;
    private TabRecyclerAdapter mAdapter;
    private String titles[] = new String[]{"商品", "详情", "评论"};

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mPersentermpl = new IPersentermpl(this);
        mTabLayout = findViewById(R.id.tab_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        banner_de = findViewById(R.id.banner_de);
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initTab();
        mAdapter = new TabRecyclerAdapter();
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //滑动RecyclerView list的时候，根据最上面一个Item的position来切换tab
                mTabLayout.setScrollPosition(mManager.findFirstVisibleItemPosition(), 0, true);
            }
        });
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
        mTabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(this, R.color.colorPrimary));
        mTabLayout.setBackgroundColor(this.getResources().getColor(R.color.coloroo));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击tab的时候，RecyclerView自动滑到该tab对应的item位置
                mManager.scrollToPositionWithOffset(tab.getPosition(), 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initTab() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles[0]).setTag(Constants.TAG_ZERO));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles[1]).setTag(Constants.TAG_ONE));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles[2]).setTag(Constants.TAG_TWO));
    }


    private class Constants {
        public static final int TAG_ZERO = 0;
        public static final int TAG_ONE = 1;
        public static final int TAG_TWO = 2;
    }

    public class TabRecyclerAdapter extends RecyclerView.Adapter {

        public static final int VIEW_TYPE_ITEM = 0;
        public static final int VIEW_TYPE_TWO=1;
        public static final int VIEW_TYPE_FOOTER = 3;
        public static final int VIEW_TYPE_THREE=2;
        private int parentHeight;
        private int itemHeight;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("aa",viewType+"");
            if (viewType == VIEW_TYPE_ITEM) {
                Log.e("sssss",viewType+"");
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
                banner_de = view.findViewById(R.id.banner_de);
                text_details = view.findViewById(R.id.text_details);
                sold = view.findViewById(R.id.sold);
                text_details_price =view. findViewById(R.id.text_details_price);
                webView = view.findViewById(R.id.webView);
                button_addshop = view.findViewById(R.id.but_addshop);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        parentHeight = mRecyclerView.getHeight();
                        itemHeight = view.getHeight();
                    }
                });
                return new ItemViewHolder(view);
            }
            if(viewType==VIEW_TYPE_TWO){
                Log.e("bb",viewType+"");
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragmentdetails, parent, false);
                showweb = view.findViewById(R.id.show_web);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        parentHeight = mRecyclerView.getHeight();
                        itemHeight = view.getHeight();
                    }
                });
                return new ItemViewHolder(view);
            }
            if(viewType==VIEW_TYPE_THREE){
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragmenttalk, parent, false);
                talk_xrecycle = view.findViewById(R.id.talk_xrecycle);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        parentHeight = mRecyclerView.getHeight();
                        itemHeight = view.getHeight();
                    }
                });
                return new ItemViewHolder(view);
            }
            else {
                //Footer是最后留白的位置，以便最后一个item能够出发tab的切换
                View view = new View(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parentHeight - itemHeight));
                return new FooterViewHolder(view);
            }
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != titles.length) {
                ((ItemViewHolder) holder).setData(position);
            }
        }

        @Override
        public int getItemCount() {
            return titles.length + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == titles.length) {
                return VIEW_TYPE_FOOTER;
            }
            else if (position==0){
                return VIEW_TYPE_ITEM;
            }
            else if(position==1){
                return VIEW_TYPE_TWO;
            }
            else {
                return VIEW_TYPE_THREE;
            }
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {

            public ItemViewHolder(View itemView) {
                super(itemView);
            }

            public void setData(int position) {
                switch (position) {
                    case Constants.TAG_ZERO:
                        Toast.makeText(DetailsActivity.this,position+"",Toast.LENGTH_SHORT).show();
                        mPersentermpl.getRequest(String.format(Api.Detailspath, id), Details.class);
                        banner_de.setBannerStyle(BannerConfig.NUM_INDICATOR);
                        banner_de.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {
                            @Override
                            public void displayImage(Context context, Object path, SimpleDraweeView imageView) {
                                Uri uri = Uri.parse((String) path);
                                imageView.setImageURI(uri);
                            }

                            @Override
                            public SimpleDraweeView createImageView(Context context) {
                                SimpleDraweeView draweeView = new SimpleDraweeView(context);
                                return draweeView;
                            }
                        });
                        button_addshop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searachCar();
                            }
                        });
                        break;
                    case Constants.TAG_ONE:
                        final View view = View.inflate(DetailsActivity.this,R.layout.fragmentdetails, null);
                        mPersentermpl.getRequest(String.format(Api.Detailspath, id), Details.class);
                        break;
                    case Constants.TAG_TWO:
                        talk_xrecycle.setLoadingMoreEnabled(true);
                        talk_xrecycle.setPullRefreshEnabled(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        talk_xrecycle.setLayoutManager(layoutManager);
                        talkAdapter = new TalkAdapter(DetailsActivity.this);
                        talk_xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
                            @Override
                            public void onRefresh() {
                                page=1;
                                settalk();
                            }

                            @Override
                            public void onLoadMore() {
                               settalk();
                            }
                        });
                        settalk();
                        break;
                }
            }
        }
    }
    private void settalk(){
        mPersentermpl.getRequest(String.format(Api.TalkPAth,page,id),TalkBean.class);
    }
    private void searachCar() {
        mPersentermpl.getRequest(Api.SearchCarPath, GoodsShapping.class);
    }



    public void addShopping(List<Goods> list) {
        String string = "[";
        if (list.size() == 0) {
            list.add(new Goods(Integer.valueOf(id), 1));

        } else {
            for (int i = 0; i < list.size(); i++) {
                if (Integer.valueOf(id) == list.get(i).getCommodityId()) {
                    int count = list.get(i).getCount();
                    count++;
                    list.get(i).setCount(count);
                    break;
                } else if (i == list.size() - 1) {
                    list.add(new Goods(Integer.valueOf(id), 1));
                    break;
                }
            }
        }
        for (Goods goods : list) {
            string += "{\"commodityId\":" + goods.getCommodityId() + ",\"count\":" + goods.getCount() + "},";
        }
        String substring = string.substring(0, string.length() - 1);
        substring += "]";
        Log.i("TAG", substring);
        Map<String, String> map = new HashMap<>();
        map.put("data", substring);
        mPersentermpl.putRequest(Api.AddShopping, Shopping.class, map);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof Details) {
            Details bean = (Details) data;
            Details.ResultBean result = bean.getResult();
            text_details.setText(result.getCategoryName());
            text_details_price.setText("¥:" + result.getPrice());
            sold.setText("已销售" + result.getSaleNum() + "件");
            String picture = result.getPicture();
            String[] split = picture.split("\\,");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            banner_de.setImages(list);
            banner_de.start();
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//支持JS
            String js = "<script type=\"text/javascript\">" +
                    "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                    "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                    "imgs[i].style.width = '100%';" +  // 宽度改为100%
                    "imgs[i].style.height = 'auto';" +
                    "}" +
                    "</script>";
            webView.loadDataWithBaseURL(null, bean.getResult().getDetails() + js, "text/html", "utf-8", null);
            showweb.loadDataWithBaseURL(null,bean.getResult().getDetails() + js, "text/html", "utf-8", null);

        }
        if (data instanceof Shopping) {
            Shopping bean = (Shopping) data;
            if (bean.getMessage().equals("同步成功")) {
                Toast.makeText(this, "添加成功哦！", Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof GoodsShapping) {
            GoodsShapping bean = (GoodsShapping) data;
            if (bean.getStatus().equals("0000")) {
                List<Goods> list = new ArrayList<>();
                List<GoodsShapping.ResultBean> result = bean.getResult();
                for (GoodsShapping.ResultBean results : result) {
                    list.add(new Goods(results.getCommodityId(), results.getCount()));
                }
                addShopping(list);
            }
        }
        if(data instanceof TalkBean){
            TalkBean bean= (TalkBean) data;
            List<TalkBean.ResultBean> result = bean.getResult();

            if (page==1){
                 talkAdapter.setList(result);
            }else {
                talkAdapter.addList(result);
            }
            talk_xrecycle.setAdapter(talkAdapter);
            page++;
            talk_xrecycle.loadMoreComplete();
            talk_xrecycle.refreshComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersentermpl.onDestory();
    }

    @Override
    public void onFiled(String e) {
        if (e.equals("HTTP 500 ")) {
            AddShopJsonBean creationJsonBean = new AddShopJsonBean();
            creationJsonBean.setCommodityId(Integer.valueOf(id));
            creationJsonBean.setCount(1);
            List<AddShopJsonBean> list = new ArrayList<>();
            list.add(creationJsonBean);

            String data = new Gson().toJson(list);
            Map<String, String> params = new HashMap<>();
            params.put("data", data);
            mPersentermpl.putRequest(Api.AddShopping, Shopping.class, params);
        }
    }

}
