package com.example.dell.zxqy.notwork.model;

import com.example.dell.zxqy.notwork.utils.MCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface Model{
    //get请求
    void getRequest(String url,Class clazz,MCallBack callBack);
    //post请求
    void postRequest(String url, Class clazz, Map<String,String> map, MCallBack callBack);
    //delete请求
    void deleteRequest(String url,Class clazz,MCallBack callBack);
    //put请求
    void putRequest(String url,Class clazz,Map<String,String> map,MCallBack callBack);
   //上传头像
   void postFile(String url, Map<String, String> map,MCallBack callBack,Class clazz);
    //多图
    void postDuoConRequestModel(String url, Map<String,String> params, List<File> list , Class clazz , MCallBack callBack);
}
