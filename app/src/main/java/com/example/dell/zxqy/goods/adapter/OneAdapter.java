package com.example.dell.zxqy.goods.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.bean.One;

import java.util.ArrayList;
import java.util.List;

public class OneAdapter extends RecyclerView.Adapter<OneAdapter.ViewHolder> {
    private Context context;
    private List<One.ResultBean> list;

    public OneAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<One.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.one_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OneAdapter.ViewHolder viewHolder, final int i) {
        //viewHolder.oneTextViwe.setText("lalala");
        viewHolder.oneTextViwe.setText(list.get(i).getName());
        viewHolder.oneTextViwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Click(list.get(i).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView oneTextViwe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            oneTextViwe = itemView.findViewById(R.id.item_one_text);
        }
    }
    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void Click(String commodityId);
    }
}
