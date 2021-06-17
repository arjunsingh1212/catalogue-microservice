package org.arjun.CatalogueMicroservice.client;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.arjun.CatalogueMicroservice.CreateUserRequest;
import org.arjun.CatalogueMicroservice.User;
import org.arjun.CatalogueMicroservice.UserServiceGrpc;

import java.util.UUID;

public class GRPCClient {
  public static void main(String[] args) {
    ManagedChannel channel =
            ManagedChannelBuilder.forAddress(
                    "localhost", 6565).usePlaintext().build();

    UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
    System.out.println(UUID.randomUUID());
    User user = User.newBuilder().setUserId(UUID.randomUUID().toString()).setUsername("Partner A").build();
    CreateUserRequest request = CreateUserRequest.newBuilder().setUser(user).build();
    User response = stub.createUser(request);
    System.out.println("Response: " + response.toString());
  }
}
