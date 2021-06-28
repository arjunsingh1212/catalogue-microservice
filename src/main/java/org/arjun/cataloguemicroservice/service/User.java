package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface User {
  CompletableFuture<org.arjun.cataloguemicroservice.entity.User>
      createUserService(final org.arjun.cataloguemicroservice.entity.User userInstance);

  void deleteUserService(final String userId);

  CompletableFuture<org.arjun.cataloguemicroservice.entity.User>
      getUserService(final String userId);

  CompletableFuture<List<org.arjun.cataloguemicroservice.entity.User>>
      getUserStreamService();

  org.arjun.cataloguemicroservice.User toProto(
          final org.arjun.cataloguemicroservice.entity.User user);

  boolean checkUserExistenceById(
          final String userId);

  boolean checkUserExistenceByUserName(final String userName);
}
