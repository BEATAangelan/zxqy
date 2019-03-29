package com.example.dell.zxqy.goods.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.bean.TwoSearch;

import java.util.ArrayList;
import java.util.List;

public class TwoSearchAdapter extends RecyclerView.Adapter<TwoSearchAdapter.ViewHolder> {
    private Context context;
    private List<TwoSearch.ResultBean> list;

    public TwoSearchAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<TwoSearch.ResultBean> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<TwoSearch.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TwoSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TwoSearchAdapter.ViewHolder viewHolder, int i) {
        viewHolder.search_title.setText(list.get(i).getCommodityName());
        viewHolder.search_price.setText(list.get(i).getPrice()+"");
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.search_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView search_image;
        TextView search_title,search_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_image = itemView.findViewById(R.id.search_image);
            search_title = itemView.findViewById(R.id.search_title);
            search_price = itemView.findViewById(R.id.search_price);
        }
    }
}
