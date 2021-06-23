package org.arjun.cataloguemicroservice.service.user;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
class UserServiceUtilTest {

  @Autowired
  UserServiceUtil userServiceUtil;

  @Autowired
  UserRepo userRepo;

  @Test
  void userCreateService() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userServiceUtil.createUserService(user);
    Assertions.assertNotNull(userRepo.findByUserName("Partner ABC"));
    userRepo.deleteByUserName("Partner ABC");
  }

  @Test
  void deleteUserService() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userRepo.save(user);
    boolean before = userServiceUtil.checkUserExistenceByUserName("Partner ABC");
    User queriedUser = userRepo.findByUserName("Partner ABC").get(0);
    userServiceUtil.deleteUserService(queriedUser.getUserId());
    boolean after = userServiceUtil.checkUserExistenceByUserName("Partner ABC");
    Assertions.assertTrue(before && !after);
  }

  @Test
  void getUserService() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userRepo.save(user);
    String userId = userRepo.findByUserName("Partner ABC").get(0).getUserId();
    Optional<User> queriedUser = userServiceUtil.getUserService(userId);
    Assertions.assertSame(queriedUser.get().getUserId(), user.getUserId());
    userRepo.deleteByUserName("Partner ABC");
  }


  @Test
  void toProto() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userRepo.save(user);
    String userId = userRepo.findByUserName("Partner ABC").get(0).getUserId();
    org.arjun.cataloguemicroservice.User protoUser =
            org.arjun.cataloguemicroservice.User.newBuilder()
                    .setUserId(userId).setUsername("Partner ABC").build();
    Assertions.assertSame(userServiceUtil.toProto(user).getUserId(),protoUser.getUserId());
    userRepo.deleteByUserName("Partner ABC");
  }

  @Test
  void checkUserExistenceById() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userRepo.save(user);
    String userId = userRepo.findByUserName("Partner ABC").get(0).getUserId();
    Assertions.assertTrue(userServiceUtil.checkUserExistenceById(userId));
    userRepo.deleteByUserName("Partner ABC");
    Assertions.assertFalse(userServiceUtil.checkUserExistenceById("some-random-string"));
  }

  @Test
  void checkUserExistenceByUserName() {
    User user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userRepo.save(user);
    Assertions.assertTrue(userServiceUtil.checkUserExistenceByUserName("Partner ABC"));
    userRepo.deleteByUserName("Partner ABC");
    Assertions.assertFalse(userServiceUtil.checkUserExistenceByUserName("some-random-string"));
  }
}