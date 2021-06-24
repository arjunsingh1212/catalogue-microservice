package org.arjun.cataloguemicroservice.restcontroller;

import java.util.List;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.service.user.UserServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserServiceUtil userServiceUtil;

  @PostMapping("/api/users")
  public User createUser(final User user) {
    return userServiceUtil.createUserService(user);
  }

  @DeleteMapping("/api/users/{userId}")
  public void deleteUser(@PathVariable("userId") final String userId) {
    userServiceUtil.deleteUserService(userId);
  }

  @GetMapping("/api/users/{userId}")
  public User getUser(@PathVariable("userId") final String userId) {
    return userServiceUtil.getUserService(userId).get();
  }

  @GetMapping("/api/users")
  public List<User> getUserStream() {
    return userServiceUtil.getUserStreamService();
  }

}
