package org.arjun.CatalogueMicroservice.repository;

import org.arjun.CatalogueMicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepo extends JpaRepository<User, Integer> {
}
