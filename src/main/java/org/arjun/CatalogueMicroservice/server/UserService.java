package org.arjun.CatalogueMicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;
import org.arjun.CatalogueMicroservice.repository.UserRepo;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
    userRepo.save(new org.arjun.CatalogueMicroservice.entity.User(
            request.getUser().getUserId(),
            request.getUser().getUsername()));
    User user = User.newBuilder().setUserId(request.getUser().getUserId()).
            setUsername(request.getUser().getUsername()).build();
    responseObserver.onNext(user);
    responseObserver.onCompleted();
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
    userRepo.deleteById(request.getUserId());
    responseObserver.onNext(Empty.newBuilder().build());
    responseObserver.onCompleted();
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
    Optional<org.arjun.CatalogueMicroservice.entity.User> entry = userRepo.findById(request.getUserId());
    entry.map(e -> e.toProto())
            .ifPresent(responseObserver::onNext);
    responseObserver.onCompleted();
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
    userRepo.findAll().forEach(e -> {
      responseObserver.onNext(UserStream.newBuilder().setUser(e.toProto()).build());
    });
    responseObserver.onCompleted();
  }
}
