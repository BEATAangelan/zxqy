package com.example.dell.zxqy.goods.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.dell.zxqy.goods.bean.AddShopJsonBean;
import com.example.dell.zxqy.goods.bean.Details;
import com.example.dell.zxqy.goods.bean.Goods;
import com.example.dell.zxqy.goods.bean.GoodsShapping;
import com.example.dell.zxqy.goods.bean.Shopping;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragmentgoods extends Fragment {
}

//    @BindView(R.id.banner_de)
//    Banner banner_de;
//    @BindView(R.id.text_details)
//    TextView text_details;
//    @BindView(R.id.sold)
//    TextView sold;
//    @BindView(R.id.text_details_price)
//    TextView text_details_price;
//    @BindView(R.id.webView)
//    WebView webView;
//    @BindView(R.id.but_addshop)
//    Button button_addshop;
//    @BindView(R.id.but_buyshop)
//    Button button_buyshop;
//    private IPersentermpl mPersentermpl;
//    Unbinder bind;
//    //商品详情
//    private String id;
//    private Unbinder unbinder;

 //   @Override
  //  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragmentgoods, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        bind = ButterKnife.bind(getActivity(),view);
//        //Intent intent = getIntent();
//        //id = intent.getStringExtra("id");
//        mPersentermpl = new IPersentermpl(this);
//        mPersentermpl.getRequest(String.format(Api.Detailspath,id),Details.class);
//        banner_de.setBannerStyle(BannerConfig.NUM_INDICATOR);
//        banner_de.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {
//            @Override
//            public void displayImage(Context context, Object path, SimpleDraweeView imageView) {
//                Uri uri = Uri.parse((String) path);
//                imageView.setImageURI(uri);
//            }
//            @Override
//            public SimpleDraweeView createImageView(Context context) {
//                SimpleDraweeView draweeView = new SimpleDraweeView(context);
//                return draweeView;
//            }
//        });
        //return view;
//    }
//    private void searachCar()
//    {
//        mPersentermpl.getRequest(Api.SearchCarPath,GoodsShapping.class);
//    }
//    //添加购物车
//    @OnClick(R.id.but_addshop)
//    public void addclick(){
//        searachCar();
//    }
//    public void  addShopping(List<Goods> list){
//        String string="[";
//        if (list.size()==0){
//            list.add(new Goods(Integer.valueOf(id),1));
//
//        }else {
//            for(int i=0;i<list.size();i++) {
//                if (Integer.valueOf(id) == list.get(i).getCommodityId()) {
//                    int count=list.get(i).getCount();
//                    count++;
//                    list.get(i).setCount(count);
//                    break;
//                }
//                else if(i==list.size()-1){
//                    list.add(new Goods(Integer.valueOf(id),1));
//                    break;
//                }
//            }
//        }
//        for (Goods goods:list){
//            string+="{\"commodityId\":"+goods.getCommodityId()+",\"count\":"+goods.getCount()+"},";
//        }
//        String substring = string.substring(0, string.length() - 1);
//        substring+="]";
//        Log.i("TAG",substring);
//        Map<String,String> map=new HashMap<>();
//        map.put("data",substring);
//        mPersentermpl.putRequest(Api.AddShopping,Shopping.class,map);
//    }
//    @Override
//    public void onSuccess(Object data) {
//        if(data instanceof  Details) {
//            Details bean = (Details) data;
//            Details.ResultBean result = bean.getResult();
//            WebSettings settings = webView.getSettings();
//            settings.setJavaScriptEnabled(true);//支持JS
//            String js = "<script type=\"text/javascript\">" +
//                    "var imgs = document.getElementsByTagName('img');" + // 找到img标签
//                    "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
//                    "imgs[i].style.width = '100%';" +  // 宽度改为100%
//                    "imgs[i].style.height = 'auto';" +
//                    "}" +
//                    "</script>";
//            webView.loadDataWithBaseURL(null, bean.getResult().getDetails() + js, "text/html", "utf-8", null);
//            text_details.setText(result.getCategoryName());
//            text_details_price.setText("¥:" + result.getPrice());
//            sold.setText("已销售" + result.getSaleNum() + "件");
//            String picture = result.getPicture();
//            String[] split = picture.split("\\,");
//            List<String> list = new ArrayList<>();
//            for (int i = 0; i < split.length; i++) {
//                list.add(split[i]);
//            }
//            banner_de.setImages(list);
//            banner_de.start();
//        }
//        if(data instanceof Shopping){
//            Shopping bean= (Shopping) data;
//            if(bean.getMessage().equals("同步成功")){
//                Toast.makeText(getActivity(), "添加成功哦！", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (data instanceof GoodsShapping){
//            GoodsShapping bean= (GoodsShapping) data;
//            if (bean.getStatus().equals("0000")){
//                List<Goods> list=new ArrayList<>();
//                List<GoodsShapping.ResultBean> result = bean.getResult();
//                for(GoodsShapping.ResultBean results:result){
//                    list.add(new Goods(results.getCommodityId(),results.getCount()));
//                }
//                addShopping(list);
//            }
//        }
//    }
//
//    @Override
//    public void onFiled(String e) {
//        if (e.equals("HTTP 500 ")){
//            AddShopJsonBean creationJsonBean=new AddShopJsonBean();
//            creationJsonBean.setCommodityId(Integer.valueOf(id));
//            creationJsonBean.setCount(1);
//            List<AddShopJsonBean> list=new ArrayList<>();
//            list.add(creationJsonBean);
//
//            String data = new Gson().toJson(list);
//            Map<String,String> params = new HashMap<>();
//            params.put("data",data);
//            mPersentermpl.putRequest(Api.AddShopping,Shopping.class,params);
//        }else{
//            Toast.makeText(getActivity(),"添加失败",Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Subscribe
//    public void getMessage(String msg){
//        id=msg;
//    }
//}
