package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arjun.cataloguemicroservice.CreateUserRequest;
import org.arjun.cataloguemicroservice.DeleteUserRequest;
import org.arjun.cataloguemicroservice.GetUserRequest;
import org.arjun.cataloguemicroservice.GetUserStreamRequest;
import org.arjun.cataloguemicroservice.User;
import org.arjun.cataloguemicroservice.UserServiceGrpc;
import org.arjun.cataloguemicroservice.UserStream;
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

  private final Logger logger =
          LogManager.getLogger(UserService.class);

  @Override
  public void createUser(final CreateUserRequest request,
                         final StreamObserver<User> responseObserver) {
    if (userServiceUtil.checkUserExistenceByUserName(request.getUser().getUsername())) {
      final Status status = Status.ALREADY_EXISTS
              .withDescription("User with the given User Name already present");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
    try {
      responseObserver.onNext(userServiceUtil.toProto(userServiceUtil.createUserService(
              converter.toUser(request)).get()));
    } catch (InterruptedException | ExecutionException e) {
      logger.info(e.getMessage());
    }
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
      final Status status = Status.NOT_FOUND
              .withDescription("User with the given User Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getUser(final GetUserRequest request, final StreamObserver<User> responseObserver) {
    if (userServiceUtil.checkUserExistenceById(request.getUserId())) {
      final CompletableFuture<org.arjun.cataloguemicroservice.entity.User> entry =
              userServiceUtil.getUserService(request.getUserId());
      try {
        responseObserver.onNext(userServiceUtil.toProto(entry.get()));
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
      responseObserver.onCompleted();
    } else {
      final Status status = Status.NOT_FOUND
              .withDescription("User with the given User Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getUserStream(final GetUserStreamRequest request, final StreamObserver<UserStream>
          responseObserver) {
    try {
      StreamSupport.stream(userServiceUtil.getUserStreamService().get()
              .spliterator(), false).forEach(e -> {
                responseObserver.onNext(UserStream.newBuilder()
                        .setUser(userServiceUtil.toProto(e)).build());
              });
    } catch (InterruptedException | ExecutionException e) {
      logger.info(e.getMessage());
    }
    responseObserver.onCompleted();
  }
}
