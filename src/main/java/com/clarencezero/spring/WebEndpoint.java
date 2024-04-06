package com.clarencezero.spring;

import com.clarencezero.grpc.ExampleServiceGrpc;
import com.clarencezero.grpc.Request;
import com.clarencezero.grpc.Response;
import com.clarencezero.spring.mygrpc.MyGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebEndpoint {
    @MyGrpcClient("hello world")
    @GrpcClient("hello")
    private ExampleServiceGrpc.ExampleServiceBlockingStub stub2;

    @GetMapping("/hello")
    public String hello() {
        Request request = Request.newBuilder().setMessage("hello world").build();
        Response response = stub2.sendMessage(request);
        return response.getMessage();
    }
}
