package org.arjun.cataloguemicroservice.service.user;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.transaction.Transactional;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;


@SpringBootTest
@Transactional
class UserServiceUtilTest {

  @Autowired
  UserServiceUtil userServiceUtil;

  @Autowired
  UserRepo userRepo;

  @BeforeEach
  void init() {
    for(int i=1;i<=6;i++) {
      if (userServiceUtil.checkUserExistenceByUserName("Partner ABC" + i)) {
        userRepo.deleteByUserName("Partner ABC" + i);
      }
    }
  }

  @AfterEach
  void tearDown() {
    for(int i=1;i<=6;i++) {
      if (userServiceUtil.checkUserExistenceByUserName("Partner ABC" + i)) {
        userRepo.deleteByUserName("Partner ABC" + i);
      }
    }
  }


  @Test
  void userCreateService() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC1");
    try {
      User createdUser = userServiceUtil.createUserService(user).get();
    } catch (InterruptedException |ExecutionException e) {
      e.printStackTrace();
    }
    assertNotNull(userRepo.findById(user.getUserId()));
    userRepo.deleteByUserName("Partner ABC1");
  }

  @Test
  void deleteUserService() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC2");
    userRepo.save(user);
    boolean before = userServiceUtil.checkUserExistenceByUserName("Partner ABC2");
    User queriedUser = userRepo.findByUserName("Partner ABC2").get(0);
    userServiceUtil.deleteUserService(queriedUser.getUserId());
    boolean after = userServiceUtil.checkUserExistenceByUserName("Partner ABC2");
    assertTrue(before && !after);
  }

  @Test
  void getUserService() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC3");
    userRepo.save(user);
    CompletableFuture<User> queriedUser = userServiceUtil.getUserService(user.getUserId());
    try {
      assertSame(queriedUser.get().getUserId(), user.getUserId());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    userRepo.deleteByUserName("Partner ABC3");
  }


  @Test
  void toProto() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC4");
    userRepo.save(user);
    String userId = userRepo.findByUserName("Partner ABC4").get(0).getUserId();
    org.arjun.cataloguemicroservice.User protoUser =
            org.arjun.cataloguemicroservice.User.newBuilder()
                    .setUserId(userId).setUsername("Partner ABC4").build();
    assertSame(userServiceUtil.toProto(user).getUserId(),protoUser.getUserId());
    userRepo.deleteByUserName("Partner ABC4");
  }

  @Test
  void checkUserExistenceById() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC5");
    userRepo.save(user);
    String userId = userRepo.findByUserName("Partner ABC5").get(0).getUserId();
    assertTrue(userServiceUtil.checkUserExistenceById(userId));
    userRepo.deleteByUserName("Partner ABC5");
    assertFalse(userServiceUtil.checkUserExistenceById("some-random-string"));
  }

  @Test
  void checkUserExistenceByUserName() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC6");
    userRepo.save(user);
    assertTrue(userServiceUtil.checkUserExistenceByUserName("Partner ABC6"));
    userRepo.deleteByUserName("Partner ABC6");
    assertFalse(userServiceUtil.checkUserExistenceByUserName("some-random-string"));
  }
}