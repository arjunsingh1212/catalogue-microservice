package org.arjun.cataloguemicroservice.repository;

import java.util.List;
import org.arjun.cataloguemicroservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface ItemRepo extends JpaRepository<Item, String> {

  List<Item> findByCatalogueId(String catalogueId);

  Item findByItemIdAndCatalogueId(String itemId, String parentCatalogueId);
}
