package com.gangtise.sdk.japi;

import com.eclipsesource.v8.V8;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class V8Engine {

    private static volatile V8 singleton;
    private V8Engine() {}

    public static V8 getInstance() {
        if (singleton == null) {
            synchronized (V8.class) {
                if (singleton == null) {
                    singleton = V8.createV8Runtime();
                }
            }
        }
        return singleton;
    }

    public static void release(){
        singleton.release();
    }

    public static V8 loadJSFile(){
        V8 runtime = getInstance();

        //获取file文件
        Resource resource = new ClassPathResource("/API/min/GCore.min.js");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();

            byte[] bytes = new byte[0];
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String jsStr = new String(bytes);
            //获取JS文件 内容
            runtime.executeVoidScript(jsStr);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return runtime;
    }

}
