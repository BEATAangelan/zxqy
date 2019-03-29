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
import com.example.dell.zxqy.goods.bean.Quality;

import java.util.ArrayList;
import java.util.List;

public class QualiAdapter extends RecyclerView.Adapter<QualiAdapter.ViewHolder> {
    private Context context;
    private List<Quality.ResultBean> list;

    public QualiAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<Quality.ResultBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<Quality.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public QualiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotlayout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QualiAdapter.ViewHolder viewHolder, int i) {
        viewHolder.hot_title.setText(list.get(i).getCommodityName());
        viewHolder.hot_price.setText(list.get(i).getPrice()+"");
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.hot_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hot_image;
        TextView hot_title,hot_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hot_image = itemView.findViewById(R.id.image_hot1);
            hot_title = itemView.findViewById(R.id.title_hot1);
            hot_price = itemView.findViewById(R.id.price_hot1);
        }
    }
}
