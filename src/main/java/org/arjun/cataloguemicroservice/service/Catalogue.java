package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.Optional;

public interface Catalogue {
  org.arjun.cataloguemicroservice.entity.Catalogue
      createCatalogueService(org.arjun.cataloguemicroservice.entity.Catalogue catalogue);

  void deleteCatalogueService(String catalogueId);

  Optional<org.arjun.cataloguemicroservice.entity.Catalogue>
      getCatalogueService(String catalogueId);

  List<org.arjun.cataloguemicroservice.entity.Catalogue>
      getCatalogueStreamByUserId(String userId);

  List<org.arjun.cataloguemicroservice.entity.Catalogue>
      getCatalogueStreamAll();

}
