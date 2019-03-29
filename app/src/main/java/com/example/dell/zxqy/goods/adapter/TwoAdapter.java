package com.example.dell.zxqy.goods.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.bean.Two;

import java.util.ArrayList;
import java.util.List;

public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.ViewHolder> {
    private Context context;
    private List<Two.ResultBean> list;

    public TwoAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<Two.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(context, R.layout.two_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TwoAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.twoText.setText(list.get(i).getName());
        viewHolder.twoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick!=null){
                    onClick.Click(list.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView twoText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            twoText = itemView.findViewById(R.id.two_text);
        }
    }
    OnItemClick onClick;
    public void setOnClick(OnItemClick onClick)
    {
        this.onClick=onClick;
    }
    public interface OnItemClick{
        void Click(String id);
    }
}
