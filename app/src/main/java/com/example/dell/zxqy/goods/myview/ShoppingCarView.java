package com.example.dell.zxqy.goods.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dell.zxqy.R;
import com.example.dell.zxqy.goods.adapter.ShoppingCarAdapter;
import com.example.dell.zxqy.goods.bean.GoodsShapping;

import java.util.List;

public class ShoppingCarView extends LinearLayout implements View.OnClickListener {
    private Context context;
    Button add, sub;
    EditText edit_num;
    private int num;
    private int position;
    private List<GoodsShapping.ResultBean> list;
    private ShoppingCarAdapter shoppingCarAdapter;

    public ShoppingCarView(Context context) {
        super(context);
        init(context);
    }

    public ShoppingCarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShoppingCarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.addlayout, null);
        add = view.findViewById(R.id.shopadd);
        sub = view.findViewById(R.id.shopj);
        edit_num = view.findViewById(R.id.shopnum);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        addView(view);
        edit_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String string = edit_num.getText().toString();
                    num = Integer.parseInt(String.valueOf(string));
                    list.get(position).setCount(num);
                } catch (Exception e) {
                    list.get(position).setCount(1);
                    if (monClick != null) {
                        monClick.OnClick(num);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setData(ShoppingCarAdapter shoppingCarAdapter, List<GoodsShapping.ResultBean> list, int i) {
        this.list = list;
        this.shoppingCarAdapter = shoppingCarAdapter;
        position = i;
        num = list.get(i).getCount();
        edit_num.setText(num + "");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.shopadd:
                num++;
                edit_num.setText(num+"");

                list.get(position).setCount(num);
                monClick.OnClick(num);
                shoppingCarAdapter.notifyItemChanged(position);
                break;
            case R.id.shopj:
                if(num>1){
                    num--;
                }else
                {
                    Toast.makeText(context,"不能再减了",Toast.LENGTH_SHORT).show();
                }
                edit_num.setText(num+"");
                list.get(position).setCount(num);
                monClick.OnClick(num);
                shoppingCarAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    //定义接口
    public interface OnClick{
        void OnClick(int num);
    }
    private OnClick monClick;
    public void setOnClick(OnClick onClick){
        monClick=onClick;
    }
}
