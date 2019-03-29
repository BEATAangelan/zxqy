package com.example.dell.zxqy.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.form.fragment.FragmentAll;
import com.example.dell.zxqy.form.fragment.FragmentFinish;
import com.example.dell.zxqy.form.fragment.FragmentPay;
import com.example.dell.zxqy.form.fragment.FragmentTake;
import com.example.dell.zxqy.form.fragment.FragmentTakes;
import com.example.dell.zxqy.form.fragment.FragmentWait;

import java.util.ArrayList;
import java.util.List;

public class FragmentFootprint extends BaseFragment implements View.OnClickListener{
    private LinearLayout indent_sunindent,indent_payment,indent_waitcargo,indent_evaluate,indent_sucess,lin;
    private ViewPager fragpage;
    private List<Fragment> list;

    @Override
    protected void intiData() {

        list = new ArrayList<>();
        list.add(new FragmentAll());
        list.add(new FragmentPay());
        list.add(new FragmentWait());
        list.add(new FragmentTake());
        list.add(new FragmentFinish());




        fragpage.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        //点击

    }

    @Override
    protected void initView(View view) {
        indent_sunindent = view.findViewById(R.id.indent_sunindent);
        indent_waitcargo = view.findViewById(R.id.indent_waitcargo);
        indent_payment = view.findViewById(R.id.indent_payment);
        indent_evaluate = view.findViewById(R.id.indent_evaluate);
        indent_sucess = view.findViewById(R.id.indent_sucess);
        lin = view.findViewById(R.id.lin);
        fragpage = view.findViewById(R.id.fragpage);


        indent_sunindent.setOnClickListener(this);
        indent_waitcargo.setOnClickListener(this);
        indent_payment.setOnClickListener(this);
        indent_evaluate.setOnClickListener(this);
        indent_sucess.setOnClickListener(this);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragmentfootprint;
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.indent_sunindent:
                fragpage.setCurrentItem(0);
                break;
            case R.id.indent_payment:
                fragpage.setCurrentItem(1);
                break;
            case R.id.indent_waitcargo:
                fragpage.setCurrentItem(2);
                break;
            case R.id.indent_evaluate:
                fragpage.setCurrentItem(3);
                break;
            case R.id.indent_sucess:
                fragpage.setCurrentItem(4);
                break;
            default:
                break;
        }
    }
}
