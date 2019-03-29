package com.example.dell.zxqy.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.zxqy.Base.BaseFragment;
import com.example.dell.zxqy.R;
import com.example.dell.zxqy.app.MyApp;
import com.example.dell.zxqy.my.activity.MyActivity;
import com.example.dell.zxqy.my.activity.MyAddressActivity;
import com.example.dell.zxqy.my.activity.MyCrileActivity;
import com.example.dell.zxqy.my.activity.MyFootActivity;
import com.example.dell.zxqy.my.activity.MyMoneyActivity;
import com.example.dell.zxqy.my.bean.MyAvaterBean;
import com.example.dell.zxqy.my.bean.MyBean;
import com.example.dell.zxqy.notwork.presenter.IPersentermpl;
import com.example.dell.zxqy.notwork.utils.Api;
import com.example.dell.zxqy.notwork.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class FragmentMy extends BaseFragment implements IView {

    @BindView(R.id.image_myhead)
    SimpleDraweeView image_myhead;
    @BindView(R.id.text_screenname)
    TextView textV_screenname;
    @BindView(R.id.grzl)
    TextView grzl;
    @BindView(R.id.myCrile)
    TextView myCrile;
    @BindView(R.id.myfoot)
    TextView myfoot;
    @BindView(R.id.mymoney)
    TextView mymoney;
    @BindView(R.id.myaddress)
    TextView myaddress;
    String name;
    IPersentermpl iPersentermpl;

    Unbinder unbinder;
    private String password;
    private final int REQUEST_PICK = 200;
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICTRUE = 300;
    private PopupWindow popupWindow;
    private TextView photo, album, dismiss;
    private final String PATH_FILE = Environment.getExternalStorageDirectory() + "/file.png";
    private final String path = Environment.getExternalStorageDirectory() + "/image.png";

    @Override
    protected void intiData() {
        init();
        image_myhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuhead();
            }
        });
    }

    public void init() {
        iPersentermpl = new IPersentermpl(this);
        iPersentermpl.getRequest(Api.SearchMyPath, MyBean.class);
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.fragmentmy;
    }

    //上传头像
    public void popuhead() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popu_head, null, false);
        photo = contentView.findViewById(R.id.photo);
        album = contentView.findViewById(R.id.album);
        dismiss = contentView.findViewById(R.id.dismiss);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色
        ColorDrawable dw = new ColorDrawable(getActivity().getResources().getColor(R.color.coloroo));
        popupWindow.setBackgroundDrawable(dw);
        image_myhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v, 80, 100);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    String[] mStatenetwork = new String[]{
                            //写的权限
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            //读的权限
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            //入网权限
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            //WIFI权限
                            Manifest.permission.ACCESS_WIFI_STATE,
                            //读手机权限
                            Manifest.permission.READ_PHONE_STATE,
                            //网络权限
                            Manifest.permission.INTERNET,
                            //相机
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_APN_SETTINGS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                    };
                    ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
                }

                /*//第一步，判断系统版本是否Wie6.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //第二步：checkSelfPermission判断有没有此权限
                    //第一个参数：上下文
                    //第二个参数：我们想要判断的权限
                    //PackageManager.PERMISSION_GRANTED 条件，权限有没有被授予
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                        startActivityForResult(intent, REQUEST_CAMEAR);
                    }*/
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, REQUEST_CAMEAR);



            }
        });
        album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //如果requestCode匹配，切权限申请通过
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
            startActivityForResult(intent, REQUEST_CAMEAR);
        }
    }

    //个人资料
    @OnClick(R.id.grzl)
    public void setmy() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        startActivity(intent);
        EventBus.getDefault().postSticky(password);
    }

    //我的圈子
    @OnClick(R.id.myCrile)
    public void setCrile() {
        Intent intent = new Intent(getActivity(), MyCrileActivity.class);
        startActivity(intent);
        EventBus.getDefault().postSticky(password);
    }

    //我的足迹
    @OnClick(R.id.myfoot)
    public void setFoot() {
        Intent intent = new Intent(getActivity(), MyFootActivity.class);
        startActivity(intent);
        EventBus.getDefault().postSticky(password);
    }

    //我的钱包
    @OnClick(R.id.mymoney)
    public void setMoney(){
        Intent intent = new Intent(getActivity(), MyMoneyActivity.class);
        startActivity(intent);
    }
    //我的收货地址
    @OnClick(R.id.myaddress)
    public void setAddress() {
        Intent intent = new Intent(getActivity(), MyAddressActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersentermpl.onDestory();
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof MyBean) {
            MyBean bean = (MyBean) data;
            MyBean.ResultBean result = bean.getResult();
            textV_screenname.setText(result.getNickName());
            password = result.getPassword();
        }
        if (data instanceof MyAvaterBean) {
            MyAvaterBean avaterBean = (MyAvaterBean) data;
            if (avaterBean.getMessage().equals("上传成功")) {
                Uri uri = Uri.parse(avaterBean.getHeadPath());
                image_myhead.setImageURI(uri);
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "上传失败", Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof MyBean) {
            MyBean bean = (MyBean) data;
            if (bean.getStatus().equals("0000")) {
                // nickName = bean.getResult().getNickName();
                image_myhead.setImageURI(Uri.parse(bean.getResult().getHeadPic()));
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onFiled(String e) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //判断SD卡是否挂载
    public boolean hasSdcard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
    //上传头像
    private void getUserAvatar(Map<String,String> map){
        iPersentermpl.postFile(Api.Head,map,MyAvaterBean.class);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMEAR && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, REQUEST_PICTRUE);

        }
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_PICTRUE);
        }
        //获取剪切完的图片数据 bitmap
        if (requestCode == REQUEST_PICTRUE && resultCode == RESULT_OK) {
            Bitmap bitmap =data.getParcelableExtra("data");
            try {
                MyApp.saveBitmap(bitmap,PATH_FILE,50);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TAG",e.getMessage());
            }
            Map<String,String> map=new HashMap<>();
            map.put("image",PATH_FILE);
            getUserAvatar(map);
        }
    }
}
