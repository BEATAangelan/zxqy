package com.example.dell.zxqy.notwork.presenter;

import com.example.dell.zxqy.notwork.utils.MCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface Persenter {
    //get请求
    void getRequest(String url,Class clazz);
    //post请求
    void postRequest(String url, Class clazz, Map<String,String> map);
    //delete请求
    void deleteRequest(String url,Class clazz);
    //put请求
    void putRequest(String url,Class clazz,Map<String,String> map);
    //上传头像
    void postFile(String url, Map<String, String> map,Class clazz);
    //多张图片
    void postDuoConRequestIpresenter(String url, Map<String,String> params, List<File> list , Class clazz);
}
