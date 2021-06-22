package org.arjun.cataloguemicroservice.service.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.arjun.cataloguemicroservice.entity.Item;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ItemServiceUtil implements org.arjun.cataloguemicroservice.service.Item {

  @Autowired
  ItemRepo itemRepo;

  @Autowired
  CatalogueRepo catalogueRepo;

  public Item createItemService(Item item) {
    return itemRepo.save(item);
  }

  public void deleteItemService(String itemId) {
    itemRepo.deleteById(itemId);
  }

  public Optional<Item> getItemService(String itemId) {
    return itemRepo.findById(itemId);
  }

  public List<Item> getItemStreamByCatalogueId(String catalogueId) {
    return itemRepo.findByCatalogueId(catalogueId);
  }

  public List<Item> getItemStreamByUserId(String userId) {
    List<Item> itemList = new ArrayList<>();
    catalogueRepo.findByUserId(userId).forEach(e -> {
      itemRepo.findByCatalogueId(e.getCatalogueId()).forEach(ele -> {
        itemList.add(ele);
      });
    });
    return itemList;
  }

  public List<Item> getItemStreamAll() {
    return itemRepo.findAll();
  }
}
