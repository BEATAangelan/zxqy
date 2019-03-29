package com.example.dell.zxqy.form.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.activity.CreateFormActivity;
import com.example.dell.zxqy.form.bean.AddressListBean;
import com.example.dell.zxqy.form.bean.ShopAddressBean;
import com.example.dell.zxqy.my.bean.shopAddressBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {
    private Context context;
    private List<shopAddressBean.ResultBean> list;

    public PopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<shopAddressBean.ResultBean> list){
            this.list=list;
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopactivity_popitem,null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(this,view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull PopAdapter.ViewHolder viewHolder, final int i) {
         viewHolder.tName .setText(list.get(i).getRealName());
          viewHolder.tPhone .setText(list.get(i).getPhone());
          //0viewHolder.tPhone .setText(list.get(i).getPhone());
//          AddressListBean addressListBean = new Gson().fromJson(list.get(i).getAddress(), AddressListBean.class);
         viewHolder.tAddress.setText(list.get(i).getAddress());
         viewHolder.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickChange!=null){
                    mClickChange.onClick(list.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_pop_name)
        TextView tName;
        @BindView(R.id.shop_pop_num)
        TextView tPhone;
        @BindView(R.id.shop_pop_addre)
        TextView tAddress;
        @BindView(R.id.choose)
        TextView choose;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

//    public void getdata(final ShopAddressBean.ResultBean item, Context context, int i) {
//        tName .setText(item.getRealName());
//        tPhone.setText(item.getPhone());
//        AddressListBean addressListBean = new Gson().fromJson(item.getAddress(), AddressListBean.class);
//        tAddress.setText(addressListBean.getCity()+addressListBean.getAddress());
//        choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mClickChange!=null){
//                    mClickChange.onClick(item.getId());
//                }
//            }
//        });
//    }
}
    private ClickChange mClickChange;
    public void setChange(ClickChange mClickChange){
        this.mClickChange=mClickChange;
    }
    public interface ClickChange{
        void onClick(int id);
    }
}
