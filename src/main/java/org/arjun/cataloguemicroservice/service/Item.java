package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Item {

  CompletableFuture<org.arjun.cataloguemicroservice.entity.Item>
      createItemService(final org.arjun.cataloguemicroservice.entity.Item item);

  void deleteItemService(final String itemId);

  CompletableFuture<org.arjun.cataloguemicroservice.entity.Item>
      getItemService(final String itemId,final String parentCatalogueId);

  CompletableFuture<List<org.arjun.cataloguemicroservice.entity.Item>>
      getItemStreamByCatalogueId(final String catalogueId);

  CompletableFuture<List<org.arjun.cataloguemicroservice.entity.Item>>
      getItemStreamByUserId(final String userId);

  CompletableFuture<List<org.arjun.cataloguemicroservice.entity.Item>>
      getItemStreamAll();

  org.arjun.cataloguemicroservice.Item toProto(
          final org.arjun.cataloguemicroservice.entity.Item item);

  boolean checkItemExistenceByCatalogueIdAndItemName(
          final String catalogueId, final String itemName);

  boolean checkItemExistenceById(final String itemId);
}
