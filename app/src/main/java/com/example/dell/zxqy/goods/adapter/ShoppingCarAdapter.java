package com.example.dell.zxqy.goods.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.bean.GoodsShapping;
import com.example.dell.zxqy.goods.myview.ShoppingCarView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCarAdapter extends RecyclerView.Adapter<ShoppingCarAdapter.ViewHolder> {
    private Context context;
    private List<GoodsShapping.ResultBean> list;

    public ShoppingCarAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }
    public void setList(List<GoodsShapping.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ShoppingCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoppingcar, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCarAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.shoppingtitle.setText(list.get(i).getCommodityName());
        viewHolder.shopprice.setText("¥"+list.get(i).getPrice());
        Glide.with(context).load(list.get(i).getPic()).into(viewHolder.shoppinghead);
        viewHolder.shopcheck.setChecked(list.get(i).isIscheck());
        viewHolder.shoppingCarView.setData(this,list,i);
        viewHolder.shoppingCarView.setOnClick(new ShoppingCarView.OnClick() {
            @Override
            public void OnClick(int num) {
                list.get(i).setCount(num);
            }
        });
        viewHolder.shopcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setIscheck(isChecked);
                shopCarListener.callBack(list);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopCarListener!=null){
                    shopCarListener.callBack(list);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shoppinghead;
        TextView shoppingtitle,shopprice;
        CheckBox shopcheck;
        LinearLayout bottom_wrapper;
        ShoppingCarView shoppingCarView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shoppinghead = itemView.findViewById(R.id.shoppinghide);
            shoppingtitle = itemView.findViewById(R.id.shoppingtitle);
            shopprice = itemView.findViewById(R.id.shopprice);
            shopcheck = itemView.findViewById(R.id.shopcheck);
            shoppingCarView = itemView.findViewById(R.id.ShoppingCarView);
            bottom_wrapper = itemView.findViewById(R.id.bottom_wrapper);
            itemView.findViewById(R.id.bottom_wrapper).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    shopCarListener.delItem(adapterPosition);
                }
            });
        }
    }
    private ShopCarListener shopCarListener;
    public void setShopCarListener(ShopCarListener shopCarListener) {
        this.shopCarListener = shopCarListener;
    }
    public interface ShopCarListener {
        void callBack(List<GoodsShapping.ResultBean> list);
        void delItem(int position);
    }
    public void delData(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
}
