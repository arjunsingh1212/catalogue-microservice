package org.arjun.CatalogueMicroservice.service;

import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;
import org.arjun.CatalogueMicroservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService extends UserServiceGrpc.UserServiceImplBase {

  @Autowired
  UserRepo userRepo;

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void addUser(User request, StreamObserver<Status> responseObserver) {
    userRepo.save(new org.arjun.CatalogueMicroservice.entity.User(request.getUserId(),
            request.getUserName()));
    Status status = Status.newBuilder().setStatus("Success").setStatusCode(0).build();
    responseObserver.onNext(status);
    responseObserver.onCompleted();
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteUser(Id request, StreamObserver<Status> responseObserver) {
    super.deleteUser(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getUsersAll(Empty request, StreamObserver<User> responseObserver) {
    super.getUsersAll(request, responseObserver);
  }
}
