package com.example.dell.zxqy.goods.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.bean.Shop;

import java.util.ArrayList;
import java.util.List;

public class MagicAdapter extends RecyclerView.Adapter<MagicAdapter.ViewHolder> {
    private Context context;
    private List<Shop.ResultBean.MlssBean.CommodityListBeanXX> list;

    public MagicAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<Shop.ResultBean.MlssBean.CommodityListBeanXX> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MagicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.magic_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MagicAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.magic_title.setText(list.get(i).getCommodityName());
        viewHolder.magic_price.setText(list.get(i).getPrice()+"");
        Uri parse = Uri.parse(list.get(i).getMasterPic());
        viewHolder.magic_image.setImageURI(parse);
        viewHolder.magic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick!=null){
                    onClick.Click(list.get(i).getCommodityId()+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView magic_image;
        TextView magic_title,magic_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            magic_image = itemView.findViewById(R.id.magic_image);
            magic_price = itemView.findViewById(R.id.magic_price);
            magic_title = itemView.findViewById(R.id.magic_title);
        }
    }
    OnClick onClick;
    public void setOnClick(OnClick onClick){
        this.onClick=onClick;
    }
    public interface OnClick{
        void Click(String id);
    }
}