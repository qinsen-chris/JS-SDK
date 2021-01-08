package com.gangtise.sdk.demo;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.gangtise.sdk.callback.SdkJavaLog;
import com.gangtise.sdk.callback.SdkJavaNotification;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
@RestController
public class SocketTest {

    @RequestMapping("/v8testsocket")
    public void v8testsocket(){
        V8 runtime = V8.createV8Runtime();
        V8Object gsdk = null;
        V8Object gsdkinfo = null;
        try{
            //1、加载js文件
            v8LoadFile(runtime);

            //获取js中对象
            gsdk = runtime.getObject("webSocket");
            Object oo = runtime.executeJSFunction("webSocket");
            gsdkinfo = (V8Object)gsdk.executeJSFunction("webSocket");
            for(String key : gsdkinfo.getKeys()){
                System.out.println(key);
            }

            gsdkinfo.release();
            gsdk.release();
            runtime.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void v8LoadFile(V8 runtime) throws Exception{
        //获取file文件
        Resource resource = new ClassPathResource("/API/websocket.js");
        InputStream inputStream = resource.getInputStream();

        byte[] bytes = new byte[0];
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String jsStr = new String(bytes);

        runtime.executeVoidScript(jsStr);
    }
}
