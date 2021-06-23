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
  private UserRepo userRepo;

  @Override
  public User createUserService(final User userInstance) {
    return userRepo.save(userInstance);
  }

  @Override
  public void deleteUserService(final String userId) {
    userRepo.deleteById(userId);
  }

  @Override
  public Optional<User> getUserService(final String userId) {
    return userRepo.findById(userId);
  }

  @Override
  public List<User> getUserStreamService() {
    return userRepo.findAll();
  }

  @Override
  public org.arjun.cataloguemicroservice.User toProto(final User user) {
    return org.arjun.cataloguemicroservice.User.newBuilder()
            .setUserId(user.getUserId()).setUsername(user.getUserName()).build();
  }

  @Override
  public boolean checkUserExistenceById(final String userId) {
    return userRepo.findById(userId).isPresent();
  }

  @Override
  public boolean checkUserExistenceByUserName(final String userName) {
    return !userRepo.findByUserName(userName).isEmpty();
  }

}
