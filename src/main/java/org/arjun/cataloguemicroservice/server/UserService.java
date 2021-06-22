package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.arjun.cataloguemicroservice.*;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.arjun.cataloguemicroservice.service.converter.Converter;
import org.arjun.cataloguemicroservice.service.user.UserServiceUtil;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

  @Autowired
  UserRepo userRepo;

  @Autowired
  UserServiceUtil userServiceUtil;

  @Autowired
  Converter converter;

  @Override
  public void createUser(CreateUserRequest request, StreamObserver<User> responseObserver) {
    responseObserver.onNext(userServiceUtil.toProto(userServiceUtil.createUserService(
            converter.toUser(request))));
    responseObserver.onCompleted();
  }

  @Override
  public void deleteUser(DeleteUserRequest request, StreamObserver<Empty>
          responseObserver) {
    userServiceUtil.deleteUserService(request.getUserId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public void getUser(GetUserRequest request, StreamObserver<User> responseObserver) {
    Optional<org.arjun.cataloguemicroservice.entity.User> entry =
            userServiceUtil.getUserService(request.getUserId());
    entry.map(e -> userServiceUtil.toProto(e))
            .ifPresent(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  @Override
  public void getUserStream(GetUserStreamRequest request, StreamObserver<UserStream>
          responseObserver) {
    userServiceUtil.getUserStreamService().forEach(e -> {
      responseObserver.onNext(UserStream.newBuilder().setUser(userServiceUtil.toProto(e)).build());
    });
    responseObserver.onCompleted();
  }
}
