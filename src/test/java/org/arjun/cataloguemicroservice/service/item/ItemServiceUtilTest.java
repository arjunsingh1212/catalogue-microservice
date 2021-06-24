package org.arjun.cataloguemicroservice.service.item;

import org.arjun.assignment1.Type;
import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.entity.Item;
import org.arjun.cataloguemicroservice.entity.User;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.ItemRepo;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.arjun.cataloguemicroservice.service.catalogue.CatalogueServiceUtil;
import org.arjun.cataloguemicroservice.service.user.UserServiceUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceUtilTest {

  @Autowired
  ItemServiceUtil itemServiceUtil;

  @Autowired
  CatalogueServiceUtil catalogueServiceUtil;

  @Autowired
  UserServiceUtil userServiceUtil;

  @Autowired
  ItemRepo itemRepo;

  @Autowired
  CatalogueRepo catalogueRepo;

  @Autowired
  UserRepo userRepo;

  User user;
  String userId;
  Catalogue catalogue;
  String catalogueId;

  @BeforeEach
  void init() {
    user = new User(UUID.randomUUID().toString(),"Partner ABC");
    userRepo.save(user);
    userId = user.getUserId();
    catalogue = new Catalogue(
            UUID.randomUUID().toString(),userId,"Catalogue ABC","Test Catalogue");
    catalogueRepo.save(catalogue);
    catalogueId = catalogue.getCatalogueId();
  }

  @AfterEach
  void clear() {
    userServiceUtil.deleteUserService(userId);
    catalogueServiceUtil.deleteCatalogueService(catalogueId);
  }

  @Test
  void createItemService() {
    Item item = new Item("Test Item", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemServiceUtil.createItemService(item);
    assertNotNull(itemRepo.findById(item.getItemId()));
    itemRepo.deleteById(item.getItemId());
  }

  @Test
  void deleteItemService() {
    Item item = new Item("Test Item", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item);
    boolean before = itemServiceUtil.checkItemExistenceById(item.getItemId());
    Item queriedItem = itemRepo.getById(item.getItemId());
    itemServiceUtil.deleteItemService(queriedItem.getItemId());
    boolean after = itemServiceUtil.checkItemExistenceById(item.getItemId());
    assertTrue(before && !after);
  }

  @Test
  void getItemService() {
    Item item = new Item("Test Item", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item);
    Item queriedItem = itemRepo.getById(item.getItemId());
    assertSame(queriedItem.getItemId(), item.getItemId());
    itemServiceUtil.deleteItemService(queriedItem.getItemId());
  }

  @Test
  void getItemStreamByCatalogueId() {
    Item item1 = new Item("Test Item1", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item1);
    Item item2 = new Item("Test Item2", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item2);
    List<Item> itemList = itemServiceUtil.getItemStreamByCatalogueId(catalogueId);
    assertTrue((item1.getItemId().equals(itemList.get(0).getItemId()) && item2.getItemId().equals(itemList.get(1).getItemId()))
            || (item1.getItemId().equals(itemList.get(1).getItemId()) && item2.getItemId().equals(itemList.get(0).getItemId())));
    itemRepo.deleteById(item1.getItemId());
    itemRepo.deleteById(item2.getItemId());
  }

  @Test
  void getItemStreamByUserId() {
    Item item1 = new Item("Test Item1", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item1);
    Item item2 = new Item("Test Item2", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item2);
    List<Item> itemList = itemServiceUtil.getItemStreamByUserId(userId);
    assertTrue((item1.getItemId().equals(itemList.get(0).getItemId()) && item2.getItemId().equals(itemList.get(1).getItemId()))
            || (item1.getItemId().equals(itemList.get(1).getItemId()) && item2.getItemId().equals(itemList.get(0).getItemId())));
    itemRepo.deleteById(item1.getItemId());
    itemRepo.deleteById(item2.getItemId());
  }

  @Test
  void toProto() {
    Item item = new Item("Test Item", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item);
    org.arjun.cataloguemicroservice.Item protoItem =
            org.arjun.cataloguemicroservice.Item.newBuilder()
                    .setItemId(item.getItemId()).setCatalogueId(item.getCatalogueId())
                    .setName("Test Item").setPrice(50.4).setQuantity(3)
                    .setType(org.arjun.cataloguemicroservice.Item.Type.RAW).build();
    assertSame(protoItem.getItemId(),itemServiceUtil.toProto(item).getItemId());
    itemRepo.deleteById(item.getItemId());
  }

  @Test
  void checkItemExistenceByCatalogueIdAndItemName() {
    Item item = new Item("Test Item", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item);
    assertTrue(itemServiceUtil.checkItemExistenceByCatalogueIdAndItemName(catalogueId,"Test Item"));
    itemRepo.deleteById(item.getItemId());
    assertFalse(itemServiceUtil.checkItemExistenceByCatalogueIdAndItemName(catalogueId,"Test Item"));
  }

  @Test
  void checkItemExistenceById() {
    Item item = new Item("Test Item", BigDecimal.valueOf(50.4),
            3,Type.RAW.toString(),UUID.randomUUID().toString(),catalogueId);
    itemRepo.save(item);
    assertTrue(itemServiceUtil.checkItemExistenceById(item.getItemId()));
    itemRepo.deleteById(item.getItemId());
    assertFalse(itemServiceUtil.checkItemExistenceById(item.getItemId()));
  }
}