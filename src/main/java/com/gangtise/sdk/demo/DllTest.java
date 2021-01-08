package com.gangtise.sdk.demo;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DllTest {

    private String filepath = "C:\\Windows\\System32\\libDBSApi4Java.dll";

    private static DllTest instance;

    public static DllTest getInstance(){
        if(instance == null){
            instance = new DllTest();
        }
        return instance;
    }


    public DllTest(){
        java.util.Properties prop = System.getProperties();
        String osName = prop.getProperty("os.name").toLowerCase();
        log.info("操作系统：{}",osName);
        log.info("java.library.path：{}",System.getProperty("java.library.path"));
        if (osName.indexOf("linux")>-1) {
            System.load(filepath);
        }else{
            System.load(filepath);
        }
    }


    public static void main(String[] args) {
        DllTest dllTest = DllTest.getInstance();
        System.out.println(dllTest);
    }
}
