package com.gangtise.sdk.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
@Order(90)
public class RespAspect {

    //@Pointcut("execution(java.lang.Object  com.gangtise..*Provider..*(java.lang.Object))")
    @Pointcut("@annotation(com.gangtise.sdk.aspect.CallbackResp)")
    public void pointCutReturn() {
    }

    @Around("pointCutReturn()")
    public Object methodRHandler(ProceedingJoinPoint pjp) throws Throwable {
        return methodHandler(pjp);
    }

    private Object methodHandler(ProceedingJoinPoint pjp) throws Throwable {
        //TODO  调用回调方法获取返回值
        Object result = null;

        String[] declaringType = pjp.getSignature().getDeclaringTypeName().split("\\.");
        String providerName = declaringType[declaringType.length-1];
        String signatureName = pjp.getSignature().getName();
        printResultFilter(result,providerName,signatureName);

        pjp.proceed();
        return result;

    }

    /**
     * 过滤返回为ResultForm<PageableOut<T>> 的结果（下面第1种情况），减少日志文件的大小。
     * 1：{"code":"000000","msg":"操作成功！","result":{"currPage":16,"list":[{"jpush":0,"marketTrend":0}],"pageSize":20,"totalCount":841,"totalpage":43}}
     * 2：{"code": "000000","msg": "操作成功！","result": {"securitys": ["000001","600600"]},"status": true}
     * 3：{"code":"000000","msg":"操作成功！","result":true,"status":true}
     * @param result
     */
    private void printResultFilter(Object result,String providerName,String signatureName) {
        if(result instanceof java.util.List){
            List<Object> jsonArr = JSONArray.parseArray(JSONObject.toJSONString(result));
            for(Object o :jsonArr){
                assem(o, providerName, signatureName);
            }
        }else{
            assem(result, providerName, signatureName);
        }


    }

    private void assem(Object result, String providerName, String signatureName) {
        JSONObject jsonObject =  (JSONObject)JSONObject.toJSON(result);
        Object res = jsonObject.get("result");
        if(res instanceof Map){
            Map map = (Map) res;
            if(map.containsKey("list") && map.containsKey("pageSize")){
                List list = (List)map.get("list");
                log.info(" {}; {}; RESULT : 分页查询，查询结果条数为{}",providerName,signatureName,list.size());
            }else{
                log.info(" {}; {}; RESULT : {}",providerName,signatureName, JSON.toJSONString(result));
            }
        }else{
            log.info(" {}; {}; RESULT : {}",providerName,signatureName, JSON.toJSONString(result));
        }
    }
}
