package org.arjun.cataloguemicroservice.repository;

import java.util.List;
import org.arjun.cataloguemicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepo extends JpaRepository<User, String> {

  List<User> findByUserName(final String userName);

  void deleteByUserName(final String userName);
}
