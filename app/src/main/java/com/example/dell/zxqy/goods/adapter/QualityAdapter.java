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

import java.util.List;

public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.ViewHolder> {
    private Context context;
    private List<Shop.ResultBean.PzshBean.CommodityListBeanX> list;

    public QualityAdapter(Context context, List<Shop.ResultBean.PzshBean.CommodityListBeanX> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.quality_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.quality_title.setText(list.get(i).getCommodityName());
        viewHolder.quality_price.setText(list.get(i).getPrice()+"");
        viewHolder.quality_image.setAspectRatio(0.8f);
        Uri parse = Uri.parse(list.get(i).getMasterPic());
        ImageRequest request=ImageRequestBuilder.newBuilderWithSource(parse)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(viewHolder.quality_image.getController())
                        .build();
        viewHolder.quality_image.setController(controller);
        viewHolder.quality_image.setOnClickListener(new View.OnClickListener() {
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
        SimpleDraweeView quality_image;
        TextView quality_title,quality_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quality_image = itemView.findViewById(R.id.quality_image);
            quality_title = itemView.findViewById(R.id.quality_title);
            quality_price = itemView.findViewById(R.id.quality_price);
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
