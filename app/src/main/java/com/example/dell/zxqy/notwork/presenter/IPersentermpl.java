package com.example.dell.zxqy.notwork.presenter;

import com.example.dell.zxqy.notwork.model.IModelmpl;
import com.example.dell.zxqy.notwork.utils.MCallBack;
import com.example.dell.zxqy.notwork.view.IView;

import java.util.Map;

public class IPersentermpl implements Persenter {
    private IModelmpl modelmpl;
    private IView mIView;
    public IPersentermpl(IView iView){
        modelmpl = new IModelmpl();
        mIView=iView;
    }
    @Override
    public void getRequest(String url, Class clazz) {
        modelmpl.getRequest(url, clazz, new MCallBack() {
            @Override
            public void setData(Object data) {
                mIView.onSuccess(data);
            }

            @Override
            public void failed(String error) {
                mIView.onFiled(error);
            }
        });
    }

    @Override
    public void postRequest(String url, Class clazz, Map<String, String> map) {
        modelmpl.postRequest(url, clazz,map,new MCallBack() {
            @Override
            public void setData(Object data) {
                mIView.onSuccess(data);
            }

            @Override
            public void failed(String error) {

            }
        });
    }

    @Override
    public void deleteRequest(String url, Class clazz) {
        modelmpl.deleteRequest(url, clazz, new MCallBack() {
            @Override
            public void setData(Object data) {
                mIView.onSuccess(data);
            }

            @Override
            public void failed(String error) {

            }
        });
    }

    @Override
    public void putRequest(String url, Class clazz, Map<String, String> map) {
        modelmpl.putRequest(url, clazz,map,new MCallBack() {
            @Override
            public void setData(Object data) {
                mIView.onSuccess(data);
            }

            @Override
            public void failed(String error) {

            }
        });
    }

    @Override
    public void postFile(String url, Map<String, String> map, final Class clazz) {
     modelmpl.postFile(url, map, new MCallBack() {
         @Override
         public void setData(Object data) {
             mIView.onSuccess(data);
         }
         @Override
         public void failed(String error) {
         }
     },clazz);
    }

    public void onDestory(){
        if(modelmpl!=null){
            modelmpl=null;
        }
        if(mIView!=null){
            mIView=null;
        }
    }
}
