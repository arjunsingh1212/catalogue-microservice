package org.arjun.cataloguemicroservice.service.catalogue;

import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.arjun.cataloguemicroservice.service.user.UserServiceUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.UUID;

@SpringBootTest
@Transactional
class CatalogueServiceUtilTest {

  @Autowired
  UserServiceUtil userServiceUtil;

  @Autowired
  CatalogueServiceUtil catalogueServiceUtil;

  @Autowired
  UserRepo userRepo;

  @Autowired
  CatalogueRepo catalogueRepo;

  User user;
  String userId;

  @BeforeEach
  void createUser() {
    user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userServiceUtil.createUserService(user);
    userId = userRepo.findByUserName("Partner ABC").get(0).getUserId();
  }

  @AfterEach
  void destroyUser() {
    userServiceUtil.deleteUserService(userId);
  }

  @Test
  void createCatalogueService() {
    Catalogue catalogue = new Catalogue();
    catalogue.setCatalogueId(UUID.randomUUID().toString());
    catalogue.setUserId(userId);
    catalogue.setCatalogueName("Catalogue ABC");
    catalogue.setDescription("Test Catalogue");
    catalogueServiceUtil.createCatalogueService(catalogue);
    Assertions.assertNotNull(catalogueRepo.findByUserIdAndCatalogueName(
            userId,"Catalogue ABC"));
    String catalogueId = catalogue.getCatalogueId();
    catalogueRepo.deleteById(catalogueId);
  }

  @Test
  void deleteCatalogueService() {
  }

  @Test
  void getCatalogueService() {
  }

  @Test
  void getCatalogueStreamByUserId() {
  }

  @Test
  void getCatalogueStreamAll() {
  }

  @Test
  void toProto() {
  }

  @Test
  void checkCatalogueExistenceByUserIdAndCatalogueName() {
  }

  @Test
  void checkCatalogueExistenceById() {
  }
}