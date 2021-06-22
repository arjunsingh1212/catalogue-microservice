package org.arjun.cataloguemicroservice.service.user;

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
}