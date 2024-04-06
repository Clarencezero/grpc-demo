package com.clarencezero.spring.mygrpc;

import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.inject.StubTransformer;
import net.devh.boot.grpc.client.stubfactory.StubFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.List;

public class MyGrpcClientBeanPostProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;
    private GrpcChannelFactory channelFactory = null;
    private List<StubTransformer> stubTransformers = null;
    private List<StubFactory> stubFactories = null;
    private ConfigurableListableBeanFactory configurableBeanFactory;

    public MyGrpcClientBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        do {
            processFields(clazz, bean);
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return bean;
    }

    private void processFields(final Class<?> clazz, final Object bean) {
        for (final Field field : clazz.getDeclaredFields()) {
            final MyGrpcClient annotation = AnnotationUtils.findAnnotation(field, MyGrpcClient.class);
            if (annotation != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, processInjectionPoint(field, field.getType(), annotation));
            }
        }
    }

    protected <T> T processInjectionPoint(final Member injectionTarget, final Class<T> injectionType,
                                          final MyGrpcClient annotation) {
        final String name = annotation.value();
        final MyGrpcChannelFactory factory = this.applicationContext.getBean(MyGrpcChannelFactory.class);
        Channel channel = factory
                .newManagedChannel("grpc");
        if (channel == null) {
            throw new IllegalStateException("Unable to create channel object!!!");
        }

        final T finalInjectObject = makeInjectObject(name, injectionTarget, injectionType, channel);
        if (finalInjectObject == null) {
            throw new IllegalStateException("Unable to create inject object for " + name);
        }

        return finalInjectObject;
    }

    private <T> T makeInjectObject(String name, Member injectionTarget, Class<T> injectionType, Channel channel) {
        if (Channel.class.equals(injectionType)) {
            return injectionType.cast(channel);
        } else if (AbstractStub.class.isAssignableFrom(injectionType)) {

            @SuppressWarnings("unchecked")
            AbstractStub<?> stub = BlockingStubFactory.createStub(
                    (Class<? extends AbstractStub<?>>) injectionType.asSubclass(AbstractStub.class), channel);
            return injectionType.cast(stub);
        } else {
            if (injectionTarget != null) {
                throw new InvalidPropertyException(injectionTarget.getDeclaringClass(), injectionTarget.getName(),
                        "Unsupported type " + injectionType.getName());
            } else {
                throw new BeanInstantiationException(injectionType, "Unsupported grpc stub or channel type");
            }
        }
    }
}
