package com.example.dell.zxqy.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.my.bean.PasswordBean;
import com.example.dell.zxqy.my.bean.shopAddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private  Context context;
    private List<shopAddressBean.ResultBean> list;

    public AddressAdapter(Context context) {
        this.context = context;
        list =  new ArrayList<>();
    }

    public void setDatas(List<shopAddressBean.ResultBean> result) {
        if (result!=null){
            list.addAll(result);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.addressactivity_new_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
     viewHolder.addressItenName.setText(list.get(i).getRealName());
     viewHolder.addressItenAddress.setText(list.get(i).getAddress());
     viewHolder.addressItenPhone.setText(list.get(i).getPhone());
        if(list.get(i).getWhetherDefault()==1){
            viewHolder.addressItenCk.setChecked(true);
        }else
        {
            viewHolder.addressItenCk.setChecked(false);
        }
     viewHolder.addressItenCk.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(list.get(i).getWhetherDefault()==1){
                 viewHolder.addressItenCk.setChecked(true);
             }else
             {
                 viewHolder.addressItenCk.setChecked(false);
             }
             if(msetOnItemClick!=null){
                 msetOnItemClick.onClick(list.get(i).getId()+"");
             }
         }
     });
        viewHolder.addressItenUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nsetOnItemClick!=null){
                    nsetOnItemClick.onClick(list.get(i).getId()+"",list.get(i).getRealName(),list.get(i).getPhone(),list.get(i).getAddress(),list.get(i).getZipCode());
                  //  nsetOnItemClick.onClick(list.get(i).getId()+list.get(i).getRealName()+list.get(i).getPhone()+list.get(i).getAddress()+list.get(i).getZipCode());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_iten_name)
        TextView addressItenName;
        @BindView(R.id.address_iten_phone)
        TextView addressItenPhone;
        @BindView(R.id.address_iten_address)
        TextView addressItenAddress;
        @BindView(R.id.address_iten_ck)
        RadioButton addressItenCk;
        @BindView(R.id.address_iten_update)
        TextView addressItenUpdate;
        @BindView(R.id.address_iten_del)
        TextView addressItenDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    setOnItemClick msetOnItemClick;
    public interface setOnItemClick{
        void onClick(String id);
    }
    public void setData(setOnItemClick setOnItemClick){
        this.msetOnItemClick=setOnItemClick;
    }
    NsetOnItemClick nsetOnItemClick;
    public interface NsetOnItemClick{
        void onClick(String id,String name,String phone,String address,String code);
    }
    public void nsetData(NsetOnItemClick nsetOnItemClick){
        this.nsetOnItemClick=nsetOnItemClick;
    }
}
