package com.example.dell.zxqy.form.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.bean.OrderBeano;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlldorInfoByStatusAdapter extends RecyclerView.Adapter<AlldorInfoByStatusAdapter.ViewHolder> {
    private List<OrderBeano.OrderListBean.DetailListBean> mList;
    private Context mContext;

    public AlldorInfoByStatusAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }

    public void setmList(List<OrderBeano.OrderListBean.DetailListBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public List<OrderBeano.OrderListBean.DetailListBean> getmList() {
        return mList;
    }

    @NonNull
    @Override
    public AlldorInfoByStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_ding,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlldorInfoByStatusAdapter.ViewHolder viewHolder, int i) {
        String commodityPic = mList.get(i).getCommodityPic();
        String[] split = commodityPic.split("\\,");
        List<String> list = Arrays.asList(split);
        Glide.with(mContext).load(list.get(0)).into(viewHolder.sd_shop_sim);
        viewHolder.che_box.setVisibility(View.GONE);
        viewHolder.tv_shop_name.setText(mList.get(i).getCommodityName());
        viewHolder.tv_shop_price.setText(mList.get(i).getCommodityPrice()+"");
        viewHolder.count.setText(mList.get(i).getCommodityCount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sd_shop_sim)
        ImageView sd_shop_sim;
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.tv_shop_price)
        TextView tv_shop_price;
        @BindView(R.id.che_box)
        CheckBox che_box;
        @BindView(R.id.count)
        TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
