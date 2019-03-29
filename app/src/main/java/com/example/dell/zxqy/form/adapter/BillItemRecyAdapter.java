package com.example.dell.zxqy.form.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.bean.OrderBeano;

import java.util.ArrayList;
import java.util.List;

public class BillItemRecyAdapter extends RecyclerView.Adapter<BillItemRecyAdapter.ViewHolder> {
    private List<OrderBeano.OrderListBean.DetailListBean> list;
    private Context context;
    private int status;
    private final int STATUS_PAY=1;
    private final int STATUS_TASK=2;
    private final int STATUS_APPRAISE=3;
    private final int STATUS_FINISH=9;
    private final int STATUS_TEN=10;
    public BillItemRecyAdapter(Context context, int status) {
        this.context = context;
        this.status = status;
        list=new ArrayList<>();
    }

    public void setList(List<OrderBeano.OrderListBean.DetailListBean> mlist) {
        if (mlist!=null){
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (status==STATUS_PAY){
            return STATUS_PAY;
        }else if (status==STATUS_TASK){
            return STATUS_TASK;
        }else if (status==STATUS_APPRAISE){
            return STATUS_APPRAISE;
        }else if (status==STATUS_FINISH){
            return STATUS_FINISH;
        }else{
            return STATUS_TEN;
        }
    }
    @NonNull
    @Override
    public BillItemRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==STATUS_PAY){
            View payView = LayoutInflater.from(context).inflate(R.layout.recy_item_pay_item, viewGroup, false);
            return new ViewHolder(payView);
        }else if (i==STATUS_TASK){
            View taskView = LayoutInflater.from(context).inflate(R.layout.recy_item_task_item, viewGroup, false);
            return new ViewHolder(taskView);
        }else if (i==STATUS_APPRAISE){
            View appraiseView = LayoutInflater.from(context).inflate(R.layout.recy_item_appraise_item, viewGroup, false);
            return new ViewHolder(appraiseView);
        }else if (i==STATUS_FINISH){
            View finishView = LayoutInflater.from(context).inflate(R.layout.recy_item_finish_item, viewGroup, false);
            return new ViewHolder(finishView);
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BillItemRecyAdapter.ViewHolder viewHolder, final int i) {
        String[] split = list.get(i).getCommodityPic().split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
        viewHolder.text_name.setText(list.get(i).getCommodityName());
        viewHolder.text_price.setText("¥"+list.get(i).getCommodityPrice());
        if (viewHolder.text_num!=null){
         viewHolder.text_num.setText("* "+list.get(i).getCommodityCount()+" *");
        }
       if (viewHolder.button_evaluate!=null){
         viewHolder.button_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickEvaluate!=null){
                        mClickEvaluate.setEvaluat(list,viewHolder.getAdapterPosition());
                    }
                }
            });
      }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView text_name;
        private TextView text_price;
        private TextView text_num;
        private Button button_evaluate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recy_item_bill_image);
            text_name=itemView.findViewById(R.id.recy_item_bill_text_name);
            text_price=itemView.findViewById(R.id.recy_item_bill_text_price);
            text_num=itemView.findViewById(R.id.recy_item_bill_text_num);
            button_evaluate=itemView.findViewById(R.id.recy_item_bill_button_evaluate);
        }
    }
    public ClickEvaluate mClickEvaluate;
    public void setEva(ClickEvaluate mClickEvaluate){
        this.mClickEvaluate=mClickEvaluate;
    }
    public interface ClickEvaluate{
        void setEvaluat(List<OrderBeano.OrderListBean.DetailListBean> list,int position);
    }
}
