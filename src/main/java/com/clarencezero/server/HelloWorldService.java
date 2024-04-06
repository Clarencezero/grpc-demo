package com.clarencezero.server;

import com.clarencezero.grpc.Request;
import com.clarencezero.grpc.Response;
import io.grpc.stub.StreamObserver;

public class HelloWorldService extends com.clarencezero.grpc.ExampleServiceGrpc.ExampleServiceImplBase {
    @Override
    public void sendMessage(Request request, StreamObserver<Response> responseObserver) {
        String reqMsg = request.getMessage();
        Response response = Response.newBuilder().setMessage("Hey, nice to meet you!").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
