package com.example.dell.zxqy.user.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dell.zxqy.Base.BaseActivity;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.fragment.FragmentCar;
import com.example.dell.zxqy.fragment.FragmentCircle;
import com.example.dell.zxqy.fragment.FragmentFootprint;
import com.example.dell.zxqy.fragment.FragmentHome;
import com.example.dell.zxqy.fragment.FragmentMy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowActivity extends BaseActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.group)
    RadioGroup group;
    private Unbinder bind;


    @Override
    protected void initView() {

        bind = ButterKnife.bind(this);
    }
    @Override
    protected int getView() {

        return R.layout.activity_show;
    }

    @Override
    protected void initData() {
        final List<Fragment> list=new ArrayList<>();
        list.add(new FragmentHome());
        list.add(new FragmentCircle());
        list.add(new FragmentCar());
        list.add(new FragmentFootprint());
        list.add(new FragmentMy());
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                return list.get(i);
            }

            @Override
            public int getCount() {

                return list.size();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.button_home:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.button_circle:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.button_car:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.button_footprint:
                        pager.setCurrentItem(3);
                        break;
                    case R.id.button_my:
                        pager.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
