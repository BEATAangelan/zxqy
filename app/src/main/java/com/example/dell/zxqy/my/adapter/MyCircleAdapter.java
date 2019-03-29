package com.example.dell.zxqy.my.adapter;

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
import com.example.dell.zxqy.circle.adapter.CircleAdapter;
import com.example.dell.zxqy.circle.bean.Circle;
import com.example.dell.zxqy.my.bean.MyCircler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.ViewHolder> {
    private Context context;
    private List<MyCircler.ResultBean> list;

    public MyCircleAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }
    public void setList(List<MyCircler.ResultBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<MyCircler.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyCircleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.circle_layout, viewGroup, false);
        MyCircleAdapter.ViewHolder viewHolder = new MyCircleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCircleAdapter.ViewHolder viewHolder, final int i) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format( new java.util.Date(list.get(i).getCreateTime()));
        viewHolder.text_time.setText(date);
        viewHolder.circle_title.setText(list.get(i).getContent());
        viewHolder.text_head.setText(list.get(i).getNickName());
        Uri parse = Uri.parse(list.get(i).getHeadPic());
        viewHolder.image_head.setImageURI(parse);
        Uri parse1 = Uri.parse(list.get(i).getImage());
        viewHolder.image_shop.setImageURI(parse1);
        viewHolder.num_text.setText(list.get(i).getGreatNum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_head,image_shop,like;
        TextView text_time,circle_title,text_head,num_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_head = itemView.findViewById(R.id.image_head);
            image_shop = itemView.findViewById(R.id.image_shop);
            text_time = itemView.findViewById(R.id.text_time);
            circle_title = itemView.findViewById(R.id.circle_title);
            text_head = itemView.findViewById(R.id.text_head);
            like = itemView.findViewById(R.id.like);
            num_text = itemView.findViewById(R.id.num_text);
        }
    }
    private CircleAdapter.GreatClick greatClick;
    public void setGreatClick(CircleAdapter.GreatClick greatClick) {
        this.greatClick = greatClick;
    }
    public interface GreatClick{
        void click(int circleId,boolean isGreat);
    }
}
