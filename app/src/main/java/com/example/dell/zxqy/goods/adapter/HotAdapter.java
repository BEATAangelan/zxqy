package com.example.dell.zxqy.goods.adapter;

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
import com.example.dell.zxqy.goods.bean.Shop;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {
    private Context context;
    private List<Shop.ResultBean.RxxpBean.CommodityListBean> list;

    public HotAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<Shop.ResultBean.RxxpBean.CommodityListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title_hot.setText(list.get(i).getCommodityName());
        viewHolder.price_hot.setText(list.get(i).getPrice()+"");
        viewHolder.image_hot.setAspectRatio(0.8f);
        Uri parse = Uri.parse(list.get(i).getMasterPic());
        ImageRequest request=ImageRequestBuilder.newBuilderWithSource(parse)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(viewHolder.image_hot.getController())
                        .build();
        viewHolder.image_hot.setController(controller);
        viewHolder.image_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick!=null){
                    onClick.Click(list.get(i).getCommodityId()+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image_hot;
        TextView title_hot,price_hot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_hot = itemView.findViewById(R.id.image_hot);
            title_hot = itemView.findViewById(R.id.title_hot);
            price_hot = itemView.findViewById(R.id.price_hot);
        }
    }
    OnClick onClick;
    public void setOnClick(OnClick onClick){
        this.onClick=onClick;
    }
    public interface OnClick{
        void Click(String id);
    }
}
