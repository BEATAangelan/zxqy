package com.example.dell.zxqy.my.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.bean.MyFootBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.http.Url;

public class FootAdapter extends RecyclerView.Adapter<FootAdapter.ViewHolder> {
    private Context context;
    private List<MyFootBean.ResultBean> list;

    public FootAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<MyFootBean.ResultBean> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<MyFootBean.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FootAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.myfoot, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FootAdapter.ViewHolder viewHolder, int i) {
      viewHolder.title_text.setText(list.get(i).getCommodityName());
      viewHolder.look_text.setText("浏览"+list.get(i).getBrowseNum()+"次");
      viewHolder.price_text.setText("¥"+list.get(i).getPrice()+"");
      String date = new SimpleDateFormat("yyyy-MM-dd").format( new java.util.Date(list.get(i).getBrowseTime()));
      viewHolder.time_text.setText(date);
      Uri parse = Uri.parse(list.get(i).getMasterPic());
      viewHolder.foot_img.setImageURI(parse);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView foot_img;
        TextView title_text,price_text,look_text,time_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foot_img = itemView.findViewById(R.id.foot_img);
            title_text = itemView.findViewById(R.id.title_text);
            price_text = itemView.findViewById(R.id.price_text);
            look_text = itemView.findViewById(R.id.look_text);
            time_text = itemView.findViewById(R.id.time_text);
        }
    }
}
