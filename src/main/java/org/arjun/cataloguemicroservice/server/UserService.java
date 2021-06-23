package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.arjun.cataloguemicroservice.*;
import org.arjun.cataloguemicroservice.service.converter.Converter;
import org.arjun.cataloguemicroservice.service.user.UserServiceUtil;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

  @Autowired
  private UserServiceUtil userServiceUtil;

  @Autowired
  private Converter converter;

  @Override
  public void createUser(final CreateUserRequest request,
                         final StreamObserver<User> responseObserver) {
    if (userServiceUtil.checkUserExistenceByUserName(request.getUser().getUsername())) {
      Status status = Status.ALREADY_EXISTS
              .withDescription("User with the given User Name already present");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
    responseObserver.onNext(userServiceUtil.toProto(userServiceUtil.createUserService(
            converter.toUser(request))));
    responseObserver.onCompleted();
  }

  @Override
  public void deleteUser(final DeleteUserRequest request, final StreamObserver<Empty>
          responseObserver) {
    if (userServiceUtil.checkUserExistenceById(request.getUserId())) {
      userServiceUtil.deleteUserService(request.getUserId());
      responseObserver.onNext(Empty.getDefaultInstance());
      responseObserver.onCompleted();
    } else {
      Status status = Status.NOT_FOUND
              .withDescription("User with the given User Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getUser(final GetUserRequest request, final StreamObserver<User> responseObserver) {
    if (userServiceUtil.checkUserExistenceById(request.getUserId())) {
      final Optional<org.arjun.cataloguemicroservice.entity.User> entry =
              userServiceUtil.getUserService(request.getUserId());
      entry.map(e -> userServiceUtil.toProto(e))
              .ifPresent(responseObserver::onNext);
      responseObserver.onCompleted();
    } else {
      Status status = Status.NOT_FOUND
              .withDescription("User with the given User Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getUserStream(final GetUserStreamRequest request, final StreamObserver<UserStream>
          responseObserver) {
    userServiceUtil.getUserStreamService().forEach(e -> {
      responseObserver.onNext(UserStream.newBuilder().setUser(userServiceUtil.toProto(e)).build());
    });
    responseObserver.onCompleted();
  }
}
