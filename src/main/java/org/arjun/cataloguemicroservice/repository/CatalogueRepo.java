package org.arjun.cataloguemicroservice.repository;

import java.util.List;
import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CatalogueRepo extends JpaRepository<Catalogue, String> {

  List<Catalogue> findByUserId(final String userId);

  Catalogue findByUserIdAndCatalogueName(final String userId, final String catalogueName);

}
