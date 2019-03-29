package com.example.dell.zxqy.form.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.bean.OrderBeano;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


//    private List<OrderBeano.OrderListBean> list;
//    private Context context;
//    private final int ITEM_COUNT_PAY=1;
//    private final int ITEM_COUNT_TASK=2;
//    private final int ITEM_COUNT_APPRAISE=3;
//    private final int ITEM_COUNT_FINISH=9;
//    private final int ITEM_COUNT_TEN=0;
//    public StringAdapter(Context context) {
//        this.context = context;
//        list=new ArrayList<>();
//    }
//
//    public void setList(List<OrderBeano.OrderListBean> mlist) {
//        list.clear();
//        if (mlist!=null){
//            list.addAll(mlist);
//        }
//        notifyDataSetChanged();
//    }
//
//    public void addList(List<OrderBeano.OrderListBean> mlist) {
//        if (mlist!=null){
//            list.addAll(mlist);
//        }
//        notifyDataSetChanged();
//    }
//    public OrderBeano.OrderListBean getItem(int position){
//        return list.get(position);
//    }
//    @Override
//    public int getItemViewType(int position) {
//        if (list.get(position).getOrderStatus().equals("1")){
//            return ITEM_COUNT_PAY;
//        }else if (list.get(position).getOrderStatus().equals("2")){
//            return ITEM_COUNT_TASK;
//        }else if (list.get(position).getOrderStatus().equals("3")){
//            return ITEM_COUNT_APPRAISE;
//        }else if (list.get(position).getOrderStatus() .equals("9")){
//            return ITEM_COUNT_FINISH;
//        }else{
//            return ITEM_COUNT_TEN;
//        }
//    }
//
//    @NonNull
//    @Override
//    public StringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        switch (i){
//            case ITEM_COUNT_PAY:
//                View payView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_pay_item, viewGroup, false);
//                return new PayViewHolder(payView);
//            case ITEM_COUNT_TASK:
//                View taskView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_task_item, viewGroup, false);
//                return new TaskViewHolder(taskView);
//            case ITEM_COUNT_APPRAISE:
//                View appraiseView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_appraise_item, viewGroup, false);
//                return new AppraiseViewHolder(appraiseView);
//            case ITEM_COUNT_FINISH:
//                View finishView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_finish_item, viewGroup, false);
//                return new FinishViewHolder(finishView);
//            default:
//                return  null;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull StringAdapter.ViewHolder viewHolder, final int i) {
//       viewHolder.pay_text_orderId.setText(list.get(i).getOrderId());
//        String times = new SimpleDateFormat("yyyy-MM-dd").format(
//                new java.util.Date(list.get(i).getOrderTime()));
//       viewHolder.pay_text_time.setText(times);
//       viewHolder.pay_text_allprice.setText(list.get(i).getPayAmount()+"");
//        initRecy(context,pay_recy,i,list.get(i),1);
//        //设置数量
//        int num=0;
//        int price=0;
//        final double finalPrice = price;
//        for (int j=0;j< list.get(i).getDetailList().size();j++){
//            num += Integer.valueOf(list.get(i).getDetailList().get(j).getCommodityCount());
//            price +=  Double.valueOf(Integer.valueOf(list.get(i).getDetailList().get(j).getCommodityCount()) *Double.valueOf( list.get(i).getDetailList().get(j).getCommodityPrice()));
//        }
//        viewHolder.pay_text_allnum.setText(""+num);
//        viewHolder.pay_button_go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (shopCarListener!=null){
//                    shopCarListener.callBack(list.get(i), finalPrice +"");
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView pay_text_orderId;
//        private TextView pay_text_time;
//        private RecyclerView pay_recy;
//        private TextView pay_text_allprice;
//        private TextView pay_text_allnum;
//        private Button pay_button_go;
//        private Button pay_button_cancel;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            pay_text_orderId=itemView.findViewById(R.id.xrecy_pay_item_text_orderId);
//            pay_text_time=itemView.findViewById(R.id.xrecy_pay_item_text_orderTime);
//            pay_text_allprice=itemView.findViewById(R.id.xrecy_pay_item_text_allprice);
//            pay_text_allnum=itemView.findViewById(R.id.xrecy_pay_item_text_allnum);
//            pay_button_go=itemView.findViewById(R.id.xrecy_pay_item_button_go);
//            pay_button_cancel=itemView.findViewById(R.id.xrecy_pay_item_button_cancel);
//            pay_recy=itemView.findViewById(R.id.xrecy_pay_item_recy);
//
//        }
//        private void initRecy(Context context, RecyclerView recy, final int i, final OrderBeano.OrderListBean item, int status) {
//            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
//            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//            recy.setLayoutManager(linearLayoutManager);
//            BillItemRecyAdapter billItemRecyAdapter=new BillItemRecyAdapter(context,status);
//            billItemRecyAdapter.setList(item.getDetailList());
//            recy.setAdapter(billItemRecyAdapter);
//            billItemRecyAdapter.setEva(new BillItemRecyAdapter.ClickEvaluate() {
//                @Override
//                public void setEvaluat(List<OrderBeano.OrderListBean.DetailListBean> list, int position) {
//                    if (mClickEvaluate!=null){
//                        mClickEvaluate.setEvaluat(list,position,item,i);
//                    }
//                }
//            });
//        }
//        //待收货的viewholder
//        private class TaskViewHolder extends RecyclerView.ViewHolder{
//
//            private TextView task_text_orderId;
//            private TextView task_text_time;
//            private RecyclerView task_recy;
//            //快递公司
//            private TextView task_text_CompName;
//            //快递单号
//            private TextView task_text_expressSn;
//            //确认按钮
//            private Button task_button_affirm;
//            public TaskViewHolder(@NonNull View itemView) {
//                super(itemView);
//                task_text_orderId=itemView.findViewById(R.id.xrecy_task_item_text_orderId);
//                task_text_time=itemView.findViewById(R.id.xrecy_task_item_text_orderTime);
//                task_text_CompName=itemView.findViewById(R.id.xrecy_task_item_text_CompName);
//                task_text_expressSn=itemView.findViewById(R.id.xrecy_task_item_text_expressSn);
//                task_button_affirm=itemView.findViewById(R.id.xrecy_task_item_button_affirm);
//                task_recy=itemView.findViewById(R.id.xrecy_task_item_recy);
//            }
//            public void getdata(final BillShopBean.OrderListBean item, Context context, int i) {
//                task_text_orderId.setText(item.getOrderId());
//                String times = new SimpleDateFormat("yyyy-MM-dd").format(
//                        new java.util.Date(item.getOrderTime()));
//                task_text_time.setText(times);
//                task_text_CompName.setText(item.getExpressCompName());
//                task_text_expressSn.setText(item.getExpressSn());
//                initRecy(context,task_recy,i,item,2);
//                task_button_affirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mClickNext!=null){
//                            mClickNext.next(item.getOrderId());
//
//                        }
//                    }
//                });
//            }
//        }
//        private ClickNext mClickNext;
//        public void setNext(ClickNext mClickNext){
//            this.mClickNext=mClickNext;
//        }
//        //确认收货的接口回调
//        public interface ClickNext{
//            void next(String orderId);
//        }
//
//        //待评价的viewholder
//        private class AppraiseViewHolder extends RecyclerView.ViewHolder{
//            private TextView appraise_text_orderId;
//            private RecyclerView appraise_recy;
//            private ImageView appraise_image_more;
//            public AppraiseViewHolder(@NonNull View itemView) {
//                super(itemView);
//                appraise_text_orderId=itemView.findViewById(R.id.xrecy_appraise_item_text_orderId);
//                appraise_recy=itemView.findViewById(R.id.xrecy_appraise_item_recy);
//                appraise_image_more=itemView.findViewById(R.id.xrecy_appraise_item_image_more);
//            }
//
//            public void getdata(BillShopBean.OrderListBean item, Context context, int i) {
//                appraise_text_orderId.setText(item.getOrderId());
//                initRecy(context,appraise_recy,i,item,3);
//            }
//
//
//        }
//        public ClickEvaluate mClickEvaluate;
//        public void setEva(ClickEvaluate mClickEvaluate){
//            this.mClickEvaluate=mClickEvaluate;
//        }
//        public interface ClickEvaluate{
//            void setEvaluat(List<OrderBeano.OrderListBean.DetailListBean> list, int position, OrderBeano.OrderListBean item, int i);
//        }
//
//        //已完成的viewholder
//        private class FinishViewHolder extends RecyclerView.ViewHolder{
//            private TextView finish_text_orderId;
//            private TextView finish_text_time;
//            private RecyclerView finish_recy;
//            private ImageView finish_image_more;
//            public FinishViewHolder(@NonNull View itemView) {
//                super(itemView);
//                finish_text_orderId=itemView.findViewById(R.id.xrecy_finish_item_text_orderId);
//                finish_text_time=itemView.findViewById(R.id.xrecy_finish_item_text_orderTime);
//                finish_recy=itemView.findViewById(R.id.xrecy_finish_item_recy);
//                finish_image_more=itemView.findViewById(R.id.xrecy_finish_item_image_more);
//
//
//            }
//
//            public void getdata(final OrderBeano.OrderListBean item, Context context, final int i) {
//                finish_text_orderId.setText(item.getOrderId());
//                String times = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
//                        new java.util.Date(item.getOrderTime()));
//                finish_text_time.setText(times);
//                initRecy(context,finish_recy,i,item,9);
//                finish_image_more.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mClickDelete!=null){
//                            mClickDelete.delete(item.getOrderId(),i);
//                        }
//
//                    }
//                });
//            }
//        }
//        //删除
//        public void deleteBill(int position){
//            list.remove(position);
//            notifyDataSetChanged();
//        }
//    }
//    private Context context;
//    private List<OrderBeano.OrderListBean> list;
//    private int status;
//
//    public StringAdapter(Context context, int status) {
//        this.context = context;
//        this.status = status;
//        list= new ArrayList<>();
//    }
//
//    public void setList(List<OrderBeano.OrderListBean> list) {
//        this.list.clear();
//        if(list!=null){
//            this.list.addAll(list);
//        }
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public StringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view=LayoutInflater.from(context).inflate(R.layout.item_text,viewGroup,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull StringAdapter.ViewHolder viewHolder, final int i) {
//        viewHolder.name.setText(list.get(i).getOrderId());
//        int count=0;
//        double price=0.0;
//        for (int j=0;j< list.get(i).getDetailList().size();j++){
//            count += Integer.valueOf(list.get(i).getDetailList().get(j).getCommodityCount());
//            price +=  Double.valueOf(Integer.valueOf(list.get(i).getDetailList().get(j).getCommodityCount()) *Double.valueOf( list.get(i).getDetailList().get(j).getCommodityPrice()));
//        }
//        viewHolder.allordersCount.setText(count+"");
//        viewHolder.allordersPrice.setText(price+"");
//        LinearLayoutManager manager=new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        viewHolder.recyclerView.setLayoutManager(manager);
//        AlldorInfoByStatusAdapter adapter=new AlldorInfoByStatusAdapter(context);
//        viewHolder.recyclerView.setAdapter(adapter);
//        adapter.setmList(list.get(i).getDetailList());
//        final double finalPrice = price;
//        viewHolder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (shopCarListener!=null){
//                    shopCarListener.callBack(list.get(i), finalPrice +"");
//                }
//            }
//        });
//        String orderStatus = list.get(i).getOrderStatus();
//        int i1 = Integer.parseInt(orderStatus);
//        switch (i1){
//            case 1:
//                viewHolder.button.setText("去支付");
//                break;
//            case 2:
//                viewHolder.button.setText("点击收货");
//                break;
//            case 3:
//                viewHolder.button.setText("去评价");
//                break;
//            case 9:
//                viewHolder.button.setText("完成");
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView name,allordersCount,allordersPrice;
//        RecyclerView recyclerView;
//        private Button button;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name=itemView.findViewById(R.id.orderId);
//            recyclerView=itemView.findViewById(R.id.recycle);
//            button=itemView.findViewById(R.id.pay);
//            allordersCount = itemView.findViewById(R.id.allordersCount);
//            allordersPrice = itemView.findViewById(R.id.allordersPrice);
//        }
//    }
//    private ShopCarListener shopCarListener;
//    public void setShopCarListener(ShopCarListener shopCarListener) {
//        this.shopCarListener = shopCarListener;
//    }
private List<OrderBeano.OrderListBean> list;
    private Context context;
    private final int ITEM_COUNT_PAY=1;
    private final int ITEM_COUNT_TASK=2;
    private final int ITEM_COUNT_APPRAISE=3;
    private final int ITEM_COUNT_FINISH=9;
    private final int ITEM_COUNT_TEN=0;
    public StringAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<OrderBeano.OrderListBean> mlist) {
        list.clear();
        if (mlist!=null){
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public void addList(List<OrderBeano.OrderListBean> mlist) {
        if (mlist!=null){
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public OrderBeano.OrderListBean getItem(int position){
        return list.get(position);
    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getOrderStatus().equals("1")){
            return ITEM_COUNT_PAY;
        }else if (list.get(position).getOrderStatus().equals("2")){
            return ITEM_COUNT_TASK;
        }else if (list.get(position).getOrderStatus().equals("3")){
            return ITEM_COUNT_APPRAISE;
        }else if (list.get(position).getOrderStatus().equals("9")){
            return ITEM_COUNT_FINISH;
        }else{
            return ITEM_COUNT_TEN;
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case ITEM_COUNT_PAY:
                View payView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_pay_item, viewGroup, false);
                return new PayViewHolder(payView);
            case ITEM_COUNT_TASK:
                View taskView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_task_item, viewGroup, false);
                return new TaskViewHolder(taskView);
            case ITEM_COUNT_APPRAISE:
                View appraiseView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_appraise_item, viewGroup, false);
                return new AppraiseViewHolder(appraiseView);
            case ITEM_COUNT_FINISH:
                View finishView = LayoutInflater.from(context).inflate(R.layout.xrecy_bill_finish_item, viewGroup, false);
                return new FinishViewHolder(finishView);
            default:
                return  null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType){
            case ITEM_COUNT_PAY:
                PayViewHolder payViewHolder= (PayViewHolder) viewHolder;
                payViewHolder.getdata(getItem(i),context,i);
                break;
            case ITEM_COUNT_TASK:
                TaskViewHolder taskViewHolder= (TaskViewHolder) viewHolder;
                taskViewHolder.getdata(getItem(i),context,i);
                break;
            case ITEM_COUNT_APPRAISE:
                AppraiseViewHolder appraiseViewHolder= (AppraiseViewHolder) viewHolder;
                appraiseViewHolder.getdata(getItem(i),context,i);
                break;
            case ITEM_COUNT_FINISH:
                FinishViewHolder finishViewHolder= (FinishViewHolder) viewHolder;
                finishViewHolder.getdata(getItem(i),context,i);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    //删除
    public void deleteBill(int position){
        list.remove(position);
        notifyDataSetChanged();
    }

    //待支付的viewholder
    private class PayViewHolder extends RecyclerView.ViewHolder{
        private TextView pay_text_orderId;
        private TextView pay_text_time;
        private RecyclerView pay_recy;
        private TextView pay_text_allprice;
        private TextView pay_text_allnum;
        private Button pay_button_go;
        private Button pay_button_cancel;
        public PayViewHolder(@NonNull View itemView) {
            super(itemView);
            pay_text_orderId=itemView.findViewById(R.id.xrecy_pay_item_text_orderId);
            pay_text_time=itemView.findViewById(R.id.xrecy_pay_item_text_orderTime);
            pay_text_allprice=itemView.findViewById(R.id.xrecy_pay_item_text_allprice);
            pay_text_allnum=itemView.findViewById(R.id.xrecy_pay_item_text_allnum);
            pay_button_go=itemView.findViewById(R.id.xrecy_pay_item_button_go);
            pay_button_cancel=itemView.findViewById(R.id.xrecy_pay_item_button_cancel);
            pay_recy=itemView.findViewById(R.id.xrecy_pay_item_recy);
        }

        public void getdata(final OrderBeano.OrderListBean item, Context context, final int i) {
            pay_text_orderId.setText(item.getOrderId());
            String times = new SimpleDateFormat("yyyy-MM-dd").format(
                    new java.util.Date(item.getOrderTime()));
            pay_text_time.setText(times);
            pay_text_allprice.setText(item.getPayAmount()+"");
            initRecy(context,pay_recy,i,item,1);
            //设置数量
            int num=0;
            for (int y=0;y<item.getDetailList().size();y++){
                num+=Integer.valueOf(item.getDetailList().get(y).getCommodityCount());
            }
            pay_text_allnum.setText(""+num);
            //删除订单
            pay_button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickDelete!=null){
                        mClickDelete.delete(item.getOrderId(),i);
                    }

                }
            });
            //支付
            pay_button_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickGo!=null){
                        mClickGo.go(item.getOrderId(),item.getPayAmount()+"");
                    }
                }
            });
        }
    }

    //支付
    private ClickGo mClickGo;
    public void setGo(ClickGo mClickGo){
        this.mClickGo=mClickGo;
    }
    public interface ClickGo{
        void go(String orderId,String all_price);
    }





    private ClickDelete mClickDelete;
    public void setDelete(ClickDelete mClickDelete){
        this.mClickDelete=mClickDelete;
    }
    //删除时候的接口回调
    public interface ClickDelete{
        void delete(String orderId,int position);
    }
    private void initRecy(Context context, RecyclerView recy, final int i, final OrderBeano.OrderListBean item, int status) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recy.setLayoutManager(linearLayoutManager);
        BillItemRecyAdapter billItemRecyAdapter=new BillItemRecyAdapter(context,status);
        billItemRecyAdapter.setList(item.getDetailList());
        recy.setAdapter(billItemRecyAdapter);
        billItemRecyAdapter.setEva(new BillItemRecyAdapter.ClickEvaluate() {
            @Override
            public void setEvaluat(List<OrderBeano.OrderListBean.DetailListBean> list, int position) {
                if (mClickEvaluate!=null){
                    mClickEvaluate.setEvaluat(list,position,item,i);
                }
            }
        });
    }
    //待收货的viewholder
    private class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView task_text_orderId;
        private TextView task_text_time;
        private RecyclerView task_recy;
        //快递公司
        private TextView task_text_CompName;
        //快递单号
        private TextView task_text_expressSn;
        //确认按钮
        private Button task_button_affirm;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_text_orderId=itemView.findViewById(R.id.xrecy_task_item_text_orderId);
            task_text_time=itemView.findViewById(R.id.xrecy_task_item_text_orderTime);
            task_text_CompName=itemView.findViewById(R.id.xrecy_task_item_text_CompName);
            task_text_expressSn=itemView.findViewById(R.id.xrecy_task_item_text_expressSn);
            task_button_affirm=itemView.findViewById(R.id.xrecy_task_item_button_affirm);
            task_recy=itemView.findViewById(R.id.xrecy_task_item_recy);
        }

        public void getdata(final OrderBeano.OrderListBean item, Context context, int i) {
            task_text_orderId.setText(item.getOrderId());
            String times = new SimpleDateFormat("yyyy-MM-dd").format(
                    new java.util.Date(item.getOrderTime()));
            task_text_time.setText(times);
            task_text_CompName.setText(item.getExpressCompName());
            task_text_expressSn.setText(item.getExpressSn());
            initRecy(context,task_recy,i,item,2);
            task_button_affirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickNext!=null){
                        mClickNext.next(item.getOrderId());

                    }
                }
            });
        }
    }
    private ClickNext mClickNext;
    public void setNext(ClickNext mClickNext){
        this.mClickNext=mClickNext;
    }
    //确认收货的接口回调
    public interface ClickNext{
        void next(String orderId);
    }

    //待评价的viewholder
    private class AppraiseViewHolder extends RecyclerView.ViewHolder{
        private TextView appraise_text_orderId;
        private RecyclerView appraise_recy;
        private ImageView appraise_image_more;
        public AppraiseViewHolder(@NonNull View itemView) {
            super(itemView);
            appraise_text_orderId=itemView.findViewById(R.id.xrecy_appraise_item_text_orderId);
            appraise_recy=itemView.findViewById(R.id.xrecy_appraise_item_recy);
            appraise_image_more=itemView.findViewById(R.id.xrecy_appraise_item_image_more);
        }

        public void getdata(OrderBeano.OrderListBean item, Context context, int i) {
            appraise_text_orderId.setText(item.getOrderId());
            initRecy(context,appraise_recy,i,item,3);
        }


    }
    public ClickEvaluate mClickEvaluate;
    public void setEva(ClickEvaluate mClickEvaluate){
        this.mClickEvaluate=mClickEvaluate;
    }
    public interface ClickEvaluate{
      //  void setEvaluat(List<BillShopBean.OrderListBean.DetailListBean> list, int position, BillShopBean.OrderListBean item, int i);

        void setEvaluat(List<OrderBeano.OrderListBean.DetailListBean> list, int position, OrderBeano.OrderListBean item, int i);
    }

    //已完成的viewholder
    private class FinishViewHolder extends RecyclerView.ViewHolder{
        private TextView finish_text_orderId;
        private TextView finish_text_time;
        private RecyclerView finish_recy;
        private ImageView finish_image_more;
        public FinishViewHolder(@NonNull View itemView) {
            super(itemView);
            finish_text_orderId=itemView.findViewById(R.id.xrecy_finish_item_text_orderId);
            finish_text_time=itemView.findViewById(R.id.xrecy_finish_item_text_orderTime);
            finish_recy=itemView.findViewById(R.id.xrecy_finish_item_recy);
            finish_image_more=itemView.findViewById(R.id.xrecy_finish_item_image_more);


        }

        public void getdata(final OrderBeano.OrderListBean item, Context context, final int i) {
            finish_text_orderId.setText(item.getOrderId());
            String times = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                    new java.util.Date(item.getOrderTime()));
            finish_text_time.setText(times);
            initRecy(context,finish_recy,i,item,9);
            finish_image_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickDelete!=null){
                        mClickDelete.delete(item.getOrderId(),i);
                    }

                }
            });
        }
    }
//public interface ShopCarListener {
//        void callBack(OrderBeano.OrderListBean list,String pr);
//    }

}
