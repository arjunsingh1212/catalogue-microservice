package org.arjun.cataloguemicroservice.service.catalogue;

import java.util.List;
import java.util.Optional;
import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CatalogueServiceUtil
        implements org.arjun.cataloguemicroservice.service.Catalogue {

  @Autowired
  CatalogueRepo catalogueRepo;

  public Catalogue createCatalogueService(Catalogue catalogue) {
    return catalogueRepo.save(catalogue);
  }

  public void deleteCatalogueService(String catalogueId) {
    catalogueRepo.deleteById(catalogueId);
  }

  public Optional<Catalogue> getCatalogueService(String catalogueId) {
    return catalogueRepo.findById(catalogueId);
  }

  public List<Catalogue> getCatalogueStreamByUserId(String userId) {
    return catalogueRepo.findByUserId(userId);
  }

  public List<Catalogue> getCatalogueStreamAll() {
    return catalogueRepo.findAll();
  }
}
