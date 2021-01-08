package com.gangtise.sdk.demo;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

@RestController
public class ScriptEngineExecute {
    private String scripe_path = "/API";

    /**
     * 通过ClassPathResource 从resources目录下获取文件内容
     */
    @RequestMapping("/loadfile")
    public void loadfile(){
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

        //获取file文件
        Resource resource = new ClassPathResource("/API/test.js");
        InputStream is = null;
        File jsfile = null;
        try {
            is = resource.getInputStream();
            jsfile = new File("jsfile");
            inputStream2File(is,jsfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载到引擎中
        try {
            //engine.eval(new FileReader(scripe_path+"/GDataSet.js"));
            engine.eval(new FileReader(jsfile));
            if (engine instanceof Invocable){
                Invocable in = (Invocable) engine;
                //Double result = (double)in.invokeFunction("printname","");
                String result = (String)in.invokeFunction("printname");
                System.out.println("输出结果为"+result);
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }



    public void inputStream2File (InputStream is, File file) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[8192];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } finally {
            os.close();
            is.close();
        }
    }


    /**
     * FileReader 直接获取项目中文件内容
     */
    @RequestMapping("/loadfile2")
    public void loadfile2(){
        try {
            /**
             * To call a anonymous function from java script file
             */
            ScriptEngine engine = new ScriptEngineManager()
                    .getEngineByName("javascript");

            FileReader fr = new FileReader("src/main/js/GCore.min.js");

            engine.eval(fr);

            if (engine instanceof Invocable){
                Invocable in = (Invocable) engine;

                //Double result = (double)in.invokeFunction("printname","");
/*                String result = (String)in.invokeFunction("printname");
                System.out.println("输出结果为"+result);*/
            }

        } catch (ScriptException scrEx) {
            scrEx.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
    }
}
