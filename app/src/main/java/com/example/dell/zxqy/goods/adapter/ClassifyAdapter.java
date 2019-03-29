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
import com.example.dell.zxqy.goods.bean.Hot;

import java.util.ArrayList;
import java.util.List;

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ViewHolder> {
    private Context context;
    private List<Hot.ResultBean> list;

    public ClassifyAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<Hot.ResultBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<Hot.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ClassifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotlayout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassifyAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title_hot1.setText(list.get(i).getCommodityName());
        viewHolder.price_hot1.setText(list.get(i).getPrice()+"");
        Uri parse = Uri.parse(list.get(i).getMasterPic());
        viewHolder.image_hot1.setImageURI(parse);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_hot1;
        TextView title_hot1,price_hot1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_hot1 = itemView.findViewById(R.id.image_hot1);
            title_hot1 = itemView.findViewById(R.id.title_hot1);
            price_hot1 = itemView.findViewById(R.id.price_hot1);
        }
    }
}
