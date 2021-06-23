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
  private CatalogueRepo catalogueRepo;

  @Override
  public Catalogue createCatalogueService(final Catalogue catalogue) {
    return catalogueRepo.save(catalogue);
  }

  @Override
  public void deleteCatalogueService(final String catalogueId) {
    catalogueRepo.deleteById(catalogueId);
  }

  @Override
  public Optional<Catalogue> getCatalogueService(final String catalogueId) {
    return catalogueRepo.findById(catalogueId);
  }

  @Override
  public List<Catalogue> getCatalogueStreamByUserId(final String userId) {
    return catalogueRepo.findByUserId(userId);
  }

  @Override
  public List<Catalogue> getCatalogueStreamAll() {
    return catalogueRepo.findAll();
  }

  @Override
  public org.arjun.cataloguemicroservice.Catalogue toProto(final Catalogue catalogue) {
    return org.arjun.cataloguemicroservice.Catalogue.newBuilder()
            .setUserId(catalogue.getUserId()).setCatalogueName(catalogue.getCatalogueName())
            .setDescription(catalogue.getDescription()).setCatalogueId(catalogue.getCatalogueId()).build();
  }

  @Override
  public boolean checkCatalogueExistenceByUserIdAndCatalogueName(
          final String userId, final String userName) {
    return catalogueRepo.findByUserIdAndCatalogueName(userId,userName) != null;
  }

  @Override
  public boolean checkCatalogueExistenceById(final String catalogueId) {
    return catalogueRepo.findById(catalogueId).isPresent();
  }
}
