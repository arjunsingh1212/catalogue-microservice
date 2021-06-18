package org.arjun.CatalogueMicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;
import org.arjun.CatalogueMicroservice.repository.UserRepo;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GRpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

  @Autowired
  UserRepo userRepo;

  /**
   * <pre>
   * Add an user to DB
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void createUser(CreateUserRequest request, StreamObserver<User> responseObserver) {
    super.createUser(request, responseObserver);
  }

  /**
   * <pre>
   * Delete a specific user
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteUser(DeleteUserRequest request, StreamObserver<Empty> responseObserver) {
    super.deleteUser(request, responseObserver);
  }

  /**
   * <pre>
   * Get a specific user
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getUser(GetUserRequest request, StreamObserver<User> responseObserver) {
    super.getUser(request, responseObserver);
  }

  /**
   * <pre>
   * Get all Users
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getUserStream(GetUserStreamRequest request, StreamObserver<UserStream> responseObserver) {
    super.getUserStream(request, responseObserver);
  }
}
