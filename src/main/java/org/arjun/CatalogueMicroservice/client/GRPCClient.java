package org.arjun.CatalogueMicroservice.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.arjun.CatalogueMicroservice.Status;
import org.arjun.CatalogueMicroservice.User;
import org.arjun.CatalogueMicroservice.UserServiceGrpc;

public class GRPCClient {
  public static void main(String[] args) {
    ManagedChannel channel =
            ManagedChannelBuilder.forAddress(
                    "localhost", 9876).usePlaintext().build();

    UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
    User user = User.newBuilder().setUserId(1).setUserName("Partner A").build();
    Status status = stub.addUser(user);
    System.out.println("Response: " + status.getStatus());
  }
}
