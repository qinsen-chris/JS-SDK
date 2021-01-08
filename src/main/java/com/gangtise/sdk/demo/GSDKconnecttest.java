package com.gangtise.sdk.demo;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.gangtise.sdk.callback.SdkJavaLog;
import com.gangtise.sdk.callback.SdkJavaNotification;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.InputStream;

@RestController
public class GSDKconnecttest {

    @RequestMapping("/v8testfunc")
    public void v8testfunc(){
        V8 runtime = V8.createV8Runtime();

        MemoryManager mm = new MemoryManager(runtime);

        V8Object gsdk = null;
        V8Array parameters = null;
        V8Object  gsdkinfo = null;
        V8Object gsdk_hook = null;
        try{
            //1、加载js文件
            v8LoadFile(runtime);

            //2、注册回调函数
            SdkJavaNotification sdkJavaNotification = new SdkJavaNotification();
            runtime.registerJavaMethod(sdkJavaNotification,"sdk_java_notification");

            SdkJavaLog sdkJavaLog = new SdkJavaLog();
            runtime.registerJavaMethod(sdkJavaLog,"sdk_java_log");


            //3、执行GSDK_HOOK.start();
            gsdk_hook = runtime.getObject("GSDK_HOOK");
            for(String key : gsdk_hook.getKeys()){
                System.out.println(key);
            }
            gsdk_hook.executeJSFunction("start");

            //获取js中对象
            gsdk = runtime.getObject("GSDK");

            parameters = new V8Array(runtime);
            parameters.push("ztzq.gangtise.com.cn");
            parameters.push("aaaaaa");
            parameters.push("token10000000000");

            gsdkinfo = (V8Object)gsdk.executeJSFunction("getInfo");
            for(String key : gsdkinfo.getKeys()){
                System.out.println(key);
            }
            //JSONObject gsdkIson = JSONObject.parseObject(gsdkinfo.toString());

            /*GSDKInfo info = (GSDKInfo)gsdkinfo;
            System.out.println(info.version);*/
            String version = (String)gsdkinfo.get("version");
            System.out.println(version);

            Object connectObj = gsdk.executeFunction("connect",parameters);

            /*V8Object request = runtime.getObject("XMLHttpRequest");
            System.out.println("XMLHttpRequest : " + request);*/

           /* gsdk_hook.release();
            gsdkinfo.release();
            parameters.release();
            gsdk.release();
            runtime.release();*/

            //mm.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void v8LoadFile(V8 runtime) throws Exception{
        //获取file文件
        Resource resource = new ClassPathResource("/API/min/GCore.min.js");
        InputStream inputStream = resource.getInputStream();

        byte[] bytes = new byte[0];
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String jsStr = new String(bytes);

        runtime.executeVoidScript(jsStr);
    }
}
