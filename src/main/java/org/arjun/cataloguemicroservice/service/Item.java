package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.Optional;

public interface Item {

  org.arjun.cataloguemicroservice.entity.Item
      createItemService(org.arjun.cataloguemicroservice.entity.Item item);

  void deleteItemService(String itemId);

  org.arjun.cataloguemicroservice.entity.Item
      getItemService(String itemId,String parentCatalogueId);

  List<org.arjun.cataloguemicroservice.entity.Item>
      getItemStreamByCatalogueId(String catalogueId);

  List<org.arjun.cataloguemicroservice.entity.Item>
      getItemStreamByUserId(String userId);

  List<org.arjun.cataloguemicroservice.entity.Item>
      getItemStreamAll();
}
