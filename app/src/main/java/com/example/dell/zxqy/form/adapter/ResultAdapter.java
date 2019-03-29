package com.example.dell.zxqy.form.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.bean.GoodsShapping;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private Context context;
    private List<GoodsShapping.ResultBean> list;

    public ResultAdapter(Context context, List<GoodsShapping.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.item_form, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder viewHolder, int i) {
        viewHolder.shopprice.setText("¥"+list.get(i).getPrice()+"");
        viewHolder.text_form.setText(list.get(i).getCommodityName());
        Glide.with(context).load(list.get(i).getPic()).into(viewHolder.image_form);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_form;
        TextView text_form,shopprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_form = itemView.findViewById(R.id.image_form);
            text_form = itemView.findViewById(R.id.text_form);
            shopprice = itemView.findViewById(R.id.shopprice);
        }
    }
}
