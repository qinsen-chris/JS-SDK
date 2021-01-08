package com.gangtise.sdk.demo;

import com.alibaba.fastjson.JSONObject;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;
import com.gangtise.sdk.japi.GSDKInfo;
import com.gangtise.sdk.japi.V8Engine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class V8Execute {

    public static class Printer {
        public void print(String string) {
            System.out.println(string);
        }
    }

    @RequestMapping("/registerJavaMethod")
    public void registerJavaMethod(){
        V8 runtime = V8.createV8Runtime();
        runtime.registerJavaMethod(new Printer(), "print", "print2", new Class<?>[]{String.class});
        runtime.executeVoidScript( "print2('Hello, World!');" );
        runtime.release();
    }


    @RequestMapping("/executeVoidScript")
    public void executeVoidScript(){
        long starttime = System.currentTimeMillis();
        V8 runtime = V8.createV8Runtime();
        System.out.println("createV8Runtime : " + (System.currentTimeMillis() - starttime));

        //使用内存管理对象
        MemoryManager mm = new MemoryManager(runtime);

        //获取JS文件 内容
        runtime.executeVoidScript(""
                + "var person = {};\n"
                + "var hockeyTeam = {name : 'WolfPack'};\n"
                + "person.first = 'Ian';\n"
                + "person['last'] = 'Bull';\n"
                + "person.hockeyTeam = hockeyTeam;\n");

        V8Object person = runtime.getObject("person");
        V8Object hockeyTeam = person.getObject("hockeyTeam");

        System.out.println("person:" + person.getString("first"));
        System.out.println("hockeyTeam:" + hockeyTeam.getString("name"));

        mm.release();

/*        person.release();
        hockeyTeam.release();
        runtime.release();*/
    }

    /**
     * 加载js文件
     * @throws Exception
     */
    @RequestMapping("/v8LoadFile")
    public void v8LoadFile() throws Exception{
        //获取file文件
        Resource resource = new ClassPathResource("/API/min/GCore.min.js");
        InputStream inputStream = resource.getInputStream();

        byte[] bytes = new byte[0];
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String jsStr = new String(bytes);
        v8LoadFile(jsStr);
    }

    private void v8LoadFile(String jsStr){
        long starttime = System.currentTimeMillis();
        V8 runtime = V8.createV8Runtime();
        System.out.println("createV8Runtime : " + (System.currentTimeMillis() - starttime));

        //获取JS文件 内容
        runtime.executeVoidScript(jsStr);

        V8Object person = runtime.getObject("person");
        V8Object hockeyTeam = person.getObject("hockeyTeam");

        System.out.println("person" + person.getString("first"));
        System.out.println("hockeyTeam" + hockeyTeam.getString("name"));
        person.release();
        hockeyTeam.release();

        runtime.release();
    }



}
