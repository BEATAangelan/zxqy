package com.example.dell.zxqy.notwork.model;

import android.content.Context;
import android.widget.Toast;

import com.example.dell.zxqy.notwork.utils.MCallBack;
import com.example.dell.zxqy.notwork.utils.NetWorkUtils;
import com.example.dell.zxqy.notwork.utils.RetorfitManager;
import com.example.dell.zxqy.user.activity.MainActivity;
import com.google.gson.Gson;

import java.util.Map;

public class IModelmpl implements Model {
    Context context;
    @Override
    public void getRequest(String url, final Class clazz, final MCallBack callBack) {
            RetorfitManager.getInstance().get(url, new RetorfitManager.HttListener() {
                @Override
                public void onSuccess(String data) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(data, clazz);
                    callBack.setData(o);
                }
                @Override
                public void onFail(String error) {
                    callBack.failed(error);
                }
            });

    }

    @Override
    public void postRequest(String url, final Class clazz, Map<String, String> map, final MCallBack callBack) {
        RetorfitManager.getInstance().post(url, map, new RetorfitManager.HttListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }
            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }

    @Override
    public void deleteRequest(String url, final Class clazz, final MCallBack callBack) {
        RetorfitManager.getInstance().delete(url, new RetorfitManager.HttListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }
            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }

    @Override
    public void putRequest(String url, final Class clazz, Map<String, String> map, final MCallBack callBack) {
        RetorfitManager.getInstance().put(url, map, new RetorfitManager.HttListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }
            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }

    @Override
    public void postFile(String url, Map<String, String> map, final MCallBack callBack, final Class clazz) {
        RetorfitManager.getInstance().postFile(url, map, new RetorfitManager.HttListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}
