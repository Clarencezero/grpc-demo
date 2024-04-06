package com.clarencezero.client;

import com.clarencezero.grpc.ExampleServiceGrpc;
import com.clarencezero.grpc.Request;
import com.clarencezero.grpc.Response;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
        //
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();

        //
        ExampleServiceGrpc.ExampleServiceBlockingStub stud = ExampleServiceGrpc.newBlockingStub(channel);

        Request request = Request.newBuilder().setMessage("hello world").build();
        Response response = stud.sendMessage(request);

        System.out.println("Response from server: " + response.getMessage());

        channel.shutdown();
    }
}
