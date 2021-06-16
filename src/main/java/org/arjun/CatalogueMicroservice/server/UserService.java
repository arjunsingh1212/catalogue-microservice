package org.arjun.CatalogueMicroservice.server;

import org.arjun.CatalogueMicroservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.arjun.CatalogueMicroservice.UserServiceGrpc;

public class UserService extends UserServiceGrpc.UserServiceImplBase {

  @Autowired
  UserRepo userRepo;

}
