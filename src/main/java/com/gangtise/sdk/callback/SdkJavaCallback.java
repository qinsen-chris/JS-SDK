package com.gangtise.sdk.callback;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * 请求回调地址，返回数据
 */
public class SdkJavaCallback implements JavaVoidCallback {

    @Override
    public void invoke(V8Object v8Object, V8Array v8Array) {
        System.out.println("SdkJavaNotification -> invoke -> v8Array.length:" + v8Array.length());
        for(String key : v8Array.getKeys()){
            System.out.println("SdkJavaNotification -> invoke -> key: " + key);
        }
    }
}
