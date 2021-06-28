package org.arjun.cataloguemicroservice.service.user;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class UserServiceUtil implements
        org.arjun.cataloguemicroservice.service.User {

  @Autowired
  private UserRepo userRepo;

  private final ReadWriteLock lock = new ReentrantReadWriteLock();

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<User> createUserService(final User userInstance) {
    lock.writeLock().lock();
    try {
      return CompletableFuture.completedFuture(userRepo.save(userInstance));
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Override
  public void deleteUserService(final String userId) {
    userRepo.deleteById(userId);
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<User> getUserService(final String userId) {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(userRepo.findById(userId).get());
    } finally {
      lock.readLock().unlock();
    }
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<List<User>> getUserStreamService() {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(userRepo.findAll());
    } finally {
      lock.readLock().unlock();
    }
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
