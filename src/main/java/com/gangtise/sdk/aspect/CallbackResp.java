package com.gangtise.sdk.aspect;

import java.lang.annotation.*;

/**
 * 有返回值的方法注解
 * 返回Object
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CallbackResp {
}
