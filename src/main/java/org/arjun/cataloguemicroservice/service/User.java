package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.Optional;

public interface User {
  org.arjun.cataloguemicroservice.entity.User
      createUserService(org.arjun.cataloguemicroservice.entity.User userInstance);

  void deleteUserService(String userId);

  Optional<org.arjun.cataloguemicroservice.entity.User>
      getUserService(String userId);

  List<org.arjun.cataloguemicroservice.entity.User>
      getUserStreamService();
}
