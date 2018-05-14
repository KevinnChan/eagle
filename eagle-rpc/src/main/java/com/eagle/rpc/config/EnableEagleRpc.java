package com.eagle.rpc.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcBeanConfiguration.class)
public @interface EnableEagleRpc {
}
