package com.example.dell.zxqy.circle.adapter;

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
import com.example.dell.zxqy.circle.bean.Circle;
import com.example.dell.zxqy.goods.adapter.HotAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    private Context context;
    private List<Circle.ResultBean> list;

    public CircleAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }
    public void setList(List<Circle.ResultBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<Circle.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CircleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.circle_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final CircleAdapter.ViewHolder viewHolder, final int i) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format( new java.util.Date(list.get(i).getCreateTime()));
        viewHolder.text_time.setText(date);
        viewHolder.circle_title.setText(list.get(i).getContent());
        viewHolder.text_head.setText(list.get(i).getNickName());
        Uri parse = Uri.parse(list.get(i).getHeadPic());
        viewHolder.image_head.setImageURI(parse);
        Uri parse1 = Uri.parse(list.get(i).getImage());
        viewHolder.image_shop.setImageURI(parse1);
        viewHolder.num_text.setText(list.get(i).getGreatNum()+"");
        if (list.get(i).getWhetherGreat()==1){
            Glide.with(context).load(R.mipmap.common_btn_prise_s_xxhdpi).into(viewHolder.like);
        }else{
            Glide.with(context).load(R.mipmap.common_btn_prise_n_xxhdpi).into(viewHolder.like);
        }

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (greatClick!=null){
                    if (list.get(i).getWhetherGreat()==1){
                        list.get(i).setWhetherGreat(2);
                        list.get(i).setGreatNum(list.get(i).getGreatNum()-1);
                    }else {
                        list.get(i).setWhetherGreat(1);
                        list.get(i).setGreatNum(list.get(i).getGreatNum()+1);
                    }
                    greatClick.click(list.get(i).getId(),list.get(i).getWhetherGreat()==1);
                    notifyItemChanged(i);
                }
            }
        });
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
    private GreatClick greatClick;
    public void setGreatClick(GreatClick greatClick)
    {
        this.greatClick = greatClick;
    }
    public interface GreatClick{
        void click(int circleId,boolean isGreat);
    }

}
