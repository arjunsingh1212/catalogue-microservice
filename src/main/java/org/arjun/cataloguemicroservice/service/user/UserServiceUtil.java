package org.arjun.cataloguemicroservice.service.user;

import java.util.List;
import java.util.Optional;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserServiceUtil implements
        org.arjun.cataloguemicroservice.service.User {

  @Autowired
  UserRepo userRepo;

  public User createUserService(User userInstance) {
    return userRepo.save(userInstance);
  }

  public void deleteUserService(String userId) {
    userRepo.deleteById(userId);
  }

  public Optional<User> getUserService(String userId) {
    return userRepo.findById(userId);
  }

  public List<User> getUserStreamService() {
    return userRepo.findAll();
  }
}
