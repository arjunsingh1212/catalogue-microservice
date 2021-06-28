package org.arjun.cataloguemicroservice.service.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.arjun.cataloguemicroservice.entity.Item;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class ItemServiceUtil implements org.arjun.cataloguemicroservice.service.Item {

  @Autowired
  private ItemRepo itemRepo;

  @Autowired
  private CatalogueRepo catalogueRepo;

  private final ReadWriteLock lock = new ReentrantReadWriteLock();


  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<Item> createItemService(final Item item) {
    lock.writeLock().lock();
    try {
      return CompletableFuture.completedFuture(itemRepo.save(item));
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Override
  public void deleteItemService(final String itemId) {
    itemRepo.deleteById(itemId);
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<Item> getItemService(final String itemId,
                                                final String parentCatalogueId) {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(
              itemRepo.findByItemIdAndCatalogueId(itemId, parentCatalogueId));
    } finally {
      lock.readLock().unlock();
    }
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<List<Item>> getItemStreamByCatalogueId(final String catalogueId) {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(itemRepo.findByCatalogueId(catalogueId));
    } finally {
      lock.readLock().unlock();
    }
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<List<Item>> getItemStreamByUserId(final String userId) {
    final List<Item> itemList = new ArrayList<>();
    catalogueRepo.findByUserId(userId).forEach(e -> {
      itemRepo.findByCatalogueId(e.getCatalogueId()).forEach(ele -> {
        itemList.add(ele);
      });
    });
    return CompletableFuture.completedFuture(itemList);
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<List<Item>> getItemStreamAll() {
    return CompletableFuture.completedFuture(itemRepo.findAll());
  }

  @Override
  public org.arjun.cataloguemicroservice.Item toProto(final Item item) {
    org.arjun.cataloguemicroservice.Item.Type type;
    switch (item.getType()) {
      case "RAW":
        type = org.arjun.cataloguemicroservice.Item.Type.RAW;
        break;

      case "MANUFACTURED":
        type = org.arjun.cataloguemicroservice.Item.Type.MANUFACTURED;
        break;

      case "IMPORTED":
        type = org.arjun.cataloguemicroservice.Item.Type.IMPORTED;
        break;

      default:
        type = org.arjun.cataloguemicroservice.Item.Type.TYPE_UNSPECIFIED;
        break;
    }
    return org.arjun.cataloguemicroservice.Item.newBuilder()
            .setItemId(item.getItemId()).setCatalogueId(
                    item.getCatalogueId()).setName(item.getName())
            .setPrice(Double.parseDouble(
                    item.getPrice().toString())).setQuantity(item.getQuantity())
            .setType(type).build();
  }

  @Override
  public boolean checkItemExistenceByCatalogueIdAndItemName(
          final String catalogueId, final String itemName) {
    return itemRepo.findByCatalogueIdAndName(catalogueId,itemName) != null;
  }

  @Override
  public boolean checkItemExistenceById(final String itemId) {
    return itemRepo.findById(itemId).isPresent();
  }
}
