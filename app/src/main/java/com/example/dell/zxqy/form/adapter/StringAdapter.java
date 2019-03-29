package com.example.dell.zxqy.form.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.bean.OrderBeano;

import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {
    private Context context;
    private List<OrderBeano.OrderListBean> list;
    private int status;

    public StringAdapter(Context context, int status) {
        this.context = context;
        this.status = status;
        list= new ArrayList<>();
    }

    public void setList(List<OrderBeano.OrderListBean> list) {
        this.list.clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_text,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getOrderId());
        int count=0;
        double price=0.0;
        for (int j=0;j< list.get(i).getDetailList().size();j++){
            count += Integer.valueOf(list.get(i).getDetailList().get(j).getCommodityCount());
            price +=  Double.valueOf(Integer.valueOf(list.get(i).getDetailList().get(j).getCommodityCount()) *Double.valueOf( list.get(i).getDetailList().get(j).getCommodityPrice()));
        }
        viewHolder.allordersCount.setText(count+"");
        viewHolder.allordersPrice.setText(price+"");
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(manager);
        AlldorInfoByStatusAdapter adapter=new AlldorInfoByStatusAdapter(context);
        viewHolder.recyclerView.setAdapter(adapter);
        adapter.setmList(list.get(i).getDetailList());
        final double finalPrice = price;
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopCarListener!=null){
                    shopCarListener.callBack(list.get(i), finalPrice +"");
                }
            }
        });
        String orderStatus = list.get(i).getOrderStatus();
        int i1 = Integer.parseInt(orderStatus);
        switch (i1){
            case 1:
                viewHolder.button.setText("去支付");
                break;
            case 2:
                viewHolder.button.setText("点击收货");
                break;
            case 3:
                viewHolder.button.setText("去评价");
                break;
            case 9:
                viewHolder.button.setText("完成");
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,allordersCount,allordersPrice;
        RecyclerView recyclerView;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.orderId);
            recyclerView=itemView.findViewById(R.id.recycle);
            button=itemView.findViewById(R.id.pay);
            allordersCount = itemView.findViewById(R.id.allordersCount);
            allordersPrice = itemView.findViewById(R.id.allordersPrice);
        }
    }
    private ShopCarListener shopCarListener;
    public void setShopCarListener(ShopCarListener shopCarListener) {
        this.shopCarListener = shopCarListener;
    }
    public interface ShopCarListener {
        void callBack(OrderBeano.OrderListBean list,String pr);
    }
}
