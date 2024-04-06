package com.clarencezero.spring.mygrpc;

import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import org.springframework.beans.BeanInstantiationException;

import java.lang.reflect.Method;

public class BlockingStubFactory {
    public static AbstractStub<?> createStub(final Class<? extends AbstractStub<?>> stubType, final Channel channel) {
        try {
            final String methodName = "newBlockingStub";
            final Class<?> enclosingClass = stubType.getEnclosingClass();
            final Method factoryMethod = enclosingClass.getMethod(methodName, Channel.class);
            return stubType.cast(factoryMethod.invoke(null, channel));
        } catch (final Exception e) {
            throw new BeanInstantiationException(stubType, "Failed to create gRPC client", e);
        }
    }
}
