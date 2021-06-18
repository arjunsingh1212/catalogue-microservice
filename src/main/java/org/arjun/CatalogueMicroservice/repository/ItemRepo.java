package org.arjun.CatalogueMicroservice.repository;

import org.arjun.CatalogueMicroservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemRepo extends JpaRepository<Item, String> {

  List<Item> findByCatalogueId(String catalogueId);
}
