package com.clarencezero.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static void main(String[] args) throws Exception {
        Server server;
        int grpcServerPort = 9091;
        ServerBuilder serverBuilder = ServerBuilder.forPort(grpcServerPort);
        serverBuilder.addService(new HelloWorldService());
        server = serverBuilder.build().start();
        Thread.currentThread().join();
    }
}
