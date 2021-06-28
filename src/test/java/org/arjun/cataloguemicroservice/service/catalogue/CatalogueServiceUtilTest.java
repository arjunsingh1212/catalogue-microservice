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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    user = new User(UUID.randomUUID().toString(), "Partner ABC");
    userRepo.save(user);
    userId = userRepo.findByUserName("Partner ABC").get(0).getUserId();
  }

  @AfterEach
  void destroyUser() {
    userServiceUtil.deleteUserService(userId);
  }

  @Test
  void createCatalogueService() {
    Catalogue catalogue = new Catalogue(
            UUID.randomUUID().toString(), userId,
            "Catalogue ABCDEFGHIJKL", "Test Catalogue");
    try {
      Catalogue createdCatalogue = catalogueServiceUtil.createCatalogueService(catalogue).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    String catalogueId = catalogue.getCatalogueId();
    assertNotNull(catalogueRepo.findById(catalogueId));
    if (catalogueRepo.findById(catalogueId).isPresent()) {
      catalogueRepo.deleteById(catalogueId);
    }
  }

  @Test
  void deleteCatalogueService() {
    Catalogue catalogue = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABC", "Test Catalogue");
    catalogueRepo.save(catalogue);
    boolean before = catalogueServiceUtil.checkCatalogueExistenceById(catalogue.getCatalogueId());
    Catalogue queriedCatalogue = catalogueRepo.findByUserIdAndCatalogueName(userId, "Catalogue ABC");
    catalogueServiceUtil.deleteCatalogueService(queriedCatalogue.getCatalogueId());
    boolean after = catalogueServiceUtil.checkCatalogueExistenceById(catalogue.getCatalogueId());
    assertTrue(before && !after);
  }

  @Test
  void getCatalogueService() {
    Catalogue catalogue = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABC", "Test Catalogue");
    catalogueRepo.save(catalogue);
    Catalogue queriedCatalogue = catalogueRepo.findByUserIdAndCatalogueName(userId, "Catalogue ABC");
    assertSame(queriedCatalogue.getCatalogueId(), catalogue.getCatalogueId());
    catalogueRepo.deleteById(catalogue.getCatalogueId());
  }

  @Test
  void getCatalogueStreamByUserId() {
    Catalogue catalogue1 = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABCD", "Test Catalogue");
    catalogueRepo.save(catalogue1);
    Catalogue catalogue2 = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABCE", "Test Catalogue");
    catalogueRepo.save(catalogue2);
    List<Catalogue> catalogueList = null;
    try {
      catalogueList = catalogueServiceUtil.getCatalogueStreamByUserId(userId).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    try {
      assertEquals(catalogue1.getCatalogueId(), catalogueList.get(0).getCatalogueId());
      Assertions.assertEquals(catalogue2.getCatalogueId(), catalogueList.get(1).getCatalogueId());
      assertTrue((catalogue1.getCatalogueId().equals(catalogueList.get(0).getCatalogueId()) && catalogue2.getCatalogueId().equals(catalogueList.get(1).getCatalogueId()))
              || (catalogue1.getCatalogueId().equals(catalogueList.get(1).getCatalogueId()) && catalogue2.getCatalogueId().equals(catalogueList.get(0).getCatalogueId())));
      catalogueRepo.deleteById(catalogue1.getCatalogueId());
      catalogueRepo.deleteById(catalogue2.getCatalogueId());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void toProto() {
    Catalogue catalogue = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABC", "Test Catalogue");
    catalogueRepo.save(catalogue);
    org.arjun.cataloguemicroservice.Catalogue protoCatalogue =
            org.arjun.cataloguemicroservice.Catalogue.newBuilder()
                    .setUserId(userId).setCatalogueName("Catalogue ABC")
                    .setCatalogueId(catalogue.getCatalogueId()).build();
    assertSame(protoCatalogue.getCatalogueId(), catalogueServiceUtil.toProto(catalogue).getCatalogueId());
    catalogueRepo.deleteById(catalogue.getCatalogueId());
  }

  @Test
  void checkCatalogueExistenceByUserIdAndCatalogueName() {
    Catalogue catalogue = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABC", "Test Catalogue");
    catalogueRepo.save(catalogue);
    assertTrue(catalogueServiceUtil.checkCatalogueExistenceByUserIdAndCatalogueName(userId, "Catalogue ABC"));
    catalogueRepo.deleteById(catalogue.getCatalogueId());
    assertFalse(catalogueServiceUtil.checkCatalogueExistenceByUserIdAndCatalogueName(userId, "Catalogue ABC"));
  }

  @Test
  void checkCatalogueExistenceById() {
    Catalogue catalogue = new Catalogue(
            UUID.randomUUID().toString(), userId, "Catalogue ABC", "Test Catalogue");
    catalogueRepo.save(catalogue);
    assertTrue(catalogueServiceUtil.checkCatalogueExistenceById(catalogue.getCatalogueId()));
    catalogueRepo.deleteById(catalogue.getCatalogueId());
    assertFalse(catalogueServiceUtil.checkCatalogueExistenceById(catalogue.getCatalogueId()));
  }
}