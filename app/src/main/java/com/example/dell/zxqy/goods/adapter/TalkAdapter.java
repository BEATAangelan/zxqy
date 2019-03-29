package com.example.dell.zxqy.goods.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.circle.adapter.CircleAdapter;
import com.example.dell.zxqy.circle.bean.Circle;
import com.example.dell.zxqy.goods.bean.TalkBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.ViewHolder> {
    List<TalkBean.ResultBean> list;
    Context context;
    public TalkAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }
    public void setList(List<TalkBean.ResultBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<TalkBean.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.talk_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(list.get(i).getHeadPic());
        viewHolder.img_head.setImageURI(uri);
        viewHolder.text_title.setText(list.get(i).getNickName());
        viewHolder.talk_name.setText(list.get(i).getContent());
        Uri parse = Uri.parse(list.get(i).getImage());
        viewHolder.talk_shop.setImageURI(parse);
        String date = new SimpleDateFormat("yyyy-MM-dd").format( new Date(list.get(i).getCreateTime()));
        viewHolder.text_hour.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img_head,talk_shop;
        TextView text_title,text_hour,talk_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_head = itemView.findViewById(R.id.img_head);
            text_title=itemView.findViewById(R.id.text_title);
            text_hour = itemView.findViewById(R.id.text_hour);
            talk_name = itemView.findViewById(R.id.talk_name);
            talk_shop = itemView.findViewById(R.id.talk_shop);
        }
    }
}
