package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.Optional;

public interface User {
  org.arjun.cataloguemicroservice.entity.User
      createUserService(final org.arjun.cataloguemicroservice.entity.User userInstance);

  void deleteUserService(final String userId);

  Optional<org.arjun.cataloguemicroservice.entity.User>
      getUserService(final String userId);

  List<org.arjun.cataloguemicroservice.entity.User>
      getUserStreamService();

  org.arjun.cataloguemicroservice.User toProto(
          final org.arjun.cataloguemicroservice.entity.User user);
}
