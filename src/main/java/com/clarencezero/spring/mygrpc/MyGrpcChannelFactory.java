package com.clarencezero.spring.mygrpc;

import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyGrpcChannelFactory {
    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    private MyGrpcProperties properties;

    public MyGrpcChannelFactory(MyGrpcProperties properties) {
        this.properties = properties;
    }

    public Channel newManagedChannel(final String name) {
        NettyChannelBuilder builder = newChannelBuilder(name);
        return builder.build();
    }

    private NettyChannelBuilder newChannelBuilder(String name) {
        MyGrpcProperties props = new MyGrpcProperties();
        // URI address = props.getAddress();
        URI address = null;
        return NettyChannelBuilder.forTarget(address.toString())
                // .defaultLoadBalancingPolicy(props.getLoadBalancingPolicy())
                .usePlaintext();
    }
}
