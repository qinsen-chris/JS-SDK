package com.gangtise.sdk.callback;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * sdk_java_log
 * 日志回调方法
 */
public class SdkJavaLog implements JavaVoidCallback {
    @Override
    public void invoke(V8Object receiver, V8Array parameters) {

        System.out.println("SdkJavaLog -> invoke -> parameters.length():" + parameters.length());

        for(String key : receiver.getKeys()){
            System.out.println("SdkJavaLog -> invoke receiver key: " + key);
        }

        for(String key : parameters.getKeys()){
            System.out.println("SdkJavaLog -> invoke parameters key: " + key);
        }
        V8Object value = (V8Object)parameters.get(0);

        System.out.println("SdkJavaLog -> invoke get0: " + value.get("level") + " msg:" + value.get("msg") + " time" + value.get("time"));
    }
}
