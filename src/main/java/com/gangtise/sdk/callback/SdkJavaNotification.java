package com.gangtise.sdk.callback;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * sdk_java_notification
 * 通知回调方法
 */
public class SdkJavaNotification implements JavaVoidCallback {
    @Override
    public void invoke(V8Object receiver, V8Array parameters) {

        System.out.println("SdkJavaNotification -> invoke -> parameters.length:" + parameters.length());
        for(String key : parameters.getKeys()){
            System.out.println("SdkJavaNotification -> invoke -> parameters key: " + key);
        }

        for(String key : parameters.getKeys()){
            System.out.println("SdkJavaNotification -> invoke parameters key: " + key);
        }
        V8Object value = (V8Object)parameters.get(0);

        System.out.println("SdkJavaNotification -> invoke get0: " + value.get("level") + " msg:" + value.get("msg") + " time" + value.get("time"));
    }
}
