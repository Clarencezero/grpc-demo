package com.clarencezero.spring.mygrpc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@EqualsAndHashCode
@ConfigurationProperties("grpc.client")
public class MyGrpcProperties {
    private Map<String, ChannelProperties> propertiesMap = new HashMap<>();
    private URI address = URI.create("static://localhost:9091");
    private String address2;

    @Data
    private class ChannelProperties {
        private String host;

        private int port;
        private static final String DEFAULT_LOAD_xBALANCING_POLICY = "round_robin";

        private URI address;

        private String loadBalancingPolicy = DEFAULT_LOAD_xBALANCING_POLICY;
    }
}
