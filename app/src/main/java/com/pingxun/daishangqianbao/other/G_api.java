package com.pingxun.daishangqianbao.other;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.pingxun.daishangqianbao.base.BaseActivity;
import com.pingxun.daishangqianbao.callback.StringDialogCallback;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by LH on 2017/8/1.
 * 网络请求统一管理类(单例模式)
 */

public class G_api {
    private static G_api mInstance;
    private G_api(){}
    private OnResultHandler handler;
    public static G_api getInstance(){
        if (mInstance==null){
            mInstance=new G_api();
        }
        return mInstance;
    }

    public interface OnResultHandler{
        void onResult(String jsonStr,int flag);
        void onError(int flag);
    }

    public G_api setHandleInterface(OnResultHandler handler){
        this.handler=handler;
        return mInstance;
    }


    /**
     * 通用get请求
     * @param urlStr URL
     * @param activity activity
     * @param map 参数
     * @param flag 标识
     */
    public void getRequest(final String urlStr, BaseActivity activity, Map<String,String> map, final int flag){
        OkGo.<String>get(urlStr)
                .tag(this)
                .params(map)
                .execute(new StringDialogCallback(activity) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (handler!=null){
                            handler.onResult(response.body(),flag);
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        if (handler!=null)
                            handler.onError(flag);
                    }
                });
    }




    /**
     * 通用的上传Json字符串post方法
     * @param urlStr URL
     * @param activity activity
     * @param jsonObject jsonObject
     * @param flag 标识
     */
    public void upJson(final String urlStr,BaseActivity activity,JSONObject jsonObject,final int flag){
        OkGo.<String>post(urlStr)
                .tag(this)
                .upJson(jsonObject)
                .execute(new StringDialogCallback(activity) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (handler!=null){
                            handler.onResult(response.body(),flag);
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        if (handler!=null)
                            handler.onError(flag);
                    }
                });


    }
}
