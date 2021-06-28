package org.arjun.cataloguemicroservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Catalogue {
  CompletableFuture<org.arjun.cataloguemicroservice.entity.Catalogue>
      createCatalogueService(final org.arjun.cataloguemicroservice.entity.Catalogue catalogue);

  void deleteCatalogueService(final String catalogueId);

  CompletableFuture<org.arjun.cataloguemicroservice.entity.Catalogue>
      getCatalogueService(final String catalogueId);

  CompletableFuture<List<org.arjun.cataloguemicroservice.entity.Catalogue>>
      getCatalogueStreamByUserId(final String userId);

  CompletableFuture<List<org.arjun.cataloguemicroservice.entity.Catalogue>>
      getCatalogueStreamAll();

  org.arjun.cataloguemicroservice.Catalogue toProto(
          final org.arjun.cataloguemicroservice.entity.Catalogue catalogue);

  boolean checkCatalogueExistenceByUserIdAndCatalogueName(
          final String userId, final String userName);

  boolean checkCatalogueExistenceById(final String catalogueId);

}
