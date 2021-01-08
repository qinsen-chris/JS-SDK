package com.gangtise.sdk.japi;

import com.eclipsesource.v8.V8;

public class GSDK {

    /**
     *
     * @param url 租户域名
     * @param userid 用户id
     * @param token 用户token
     * @param authServer 第三方认证环境码
     * @param other 其他可扩展参数
     */
    public static void connect(String url,String userid,String token,String authServer,Object other){
        V8 runtime = V8Engine.loadJSFile();

        //runtime.e

        V8Engine.release();
    }
}
