package org.arjun.CatalogueMicroservice.client;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.arjun.CatalogueMicroservice.*;

import java.util.UUID;

public class GRPCClient {

  ManagedChannel channel =
          ManagedChannelBuilder.forAddress(
                  "localhost", 6565).usePlaintext().build();
  UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

  private void createUser() {
    User user = User.newBuilder().setUserId(UUID.randomUUID().toString()).setUsername("Partner B").build();
    CreateUserRequest request = CreateUserRequest.newBuilder().setUser(user).build();
    User response = stub.createUser(request);
    System.out.println("Response: " + response.toString());
  }

  private void deleteUser() {
    String userId = "ae38f97c-5532-48e7-89ee-d6f70aad752a";
    DeleteUserRequest request = DeleteUserRequest.newBuilder().setUserId(userId).build();
    Empty response = stub.deleteUser(request);
    System.out.println("Response: " + response.toString());
  }

  private void getUser() {
    String userId = "00ed1cba-b9f2-46fc-b662-4ff30955f470";
    GetUserRequest request = GetUserRequest.newBuilder().setUserId(userId).build();
    User response = stub.getUser(request);
    System.out.println("Response: " + response.toString());
  }

  private void getUserStream() {
    GetUserStreamRequest request = GetUserStreamRequest.newBuilder().setAll(true).build();
    stub.getUserStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }

  public static void main(String[] args) {
    GRPCClient client = new GRPCClient();
//    client.createUser();
//    client.deleteUser();
//    client.getUser();
    client.getUserStream();
  }
}
