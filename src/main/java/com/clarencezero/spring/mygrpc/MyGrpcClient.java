package com.clarencezero.spring.mygrpc;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyGrpcClient {

    /**
     * 客户端名称
     */
    String value();
}