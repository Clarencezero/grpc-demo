package com.clarencezero.spring.mygrpc;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@EnableConfigurationProperties
@Configuration(proxyBeanMethods = false)
public class MyGrpcClientAutoConfiguration {
    @Bean
    public MyGrpcClientBeanPostProcessor myGrpcClientBeanPostProcessor(final ApplicationContext applicationContext) {
        return new MyGrpcClientBeanPostProcessor(applicationContext);
    }

    @Bean
    public MyGrpcProperties myGrpcProperties() {
        return new MyGrpcProperties();
    }

    @Bean
    @Lazy
    public MyGrpcChannelFactory myGrpcChannelFactory(MyGrpcProperties myGrpcProperties) {
        return new MyGrpcChannelFactory(myGrpcProperties);
    }
}