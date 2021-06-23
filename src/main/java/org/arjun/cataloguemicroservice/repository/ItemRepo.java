package org.arjun.cataloguemicroservice.repository;

import java.util.List;
import org.arjun.cataloguemicroservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ItemRepo extends JpaRepository<Item, String> {

  List<Item> findByCatalogueId(final String catalogueId);

  Item findByItemIdAndCatalogueId(final String itemId, final String parentCatalogueId);

  Item findByCatalogueIdAndName(final String itemId, final String parentCatalogueId);

}
