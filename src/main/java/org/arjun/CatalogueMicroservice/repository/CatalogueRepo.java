package org.arjun.CatalogueMicroservice.repository;

import org.arjun.CatalogueMicroservice.entity.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CatalogueRepo extends JpaRepository<Catalogue, Integer> {
}
