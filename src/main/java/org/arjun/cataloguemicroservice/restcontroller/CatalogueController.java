package org.arjun.cataloguemicroservice.restcontroller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.service.catalogue.CatalogueServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogueController {

  @Autowired
  private CatalogueServiceUtil catalogueServiceUtil;

  @PostMapping("/api/catalogues")
  public CompletableFuture<Catalogue> createCatalogue(final Catalogue catalogue) {
    return catalogueServiceUtil.createCatalogueService(catalogue);
  }

  @DeleteMapping("/api/catalogues/{catalogueId}")
  public void deleteCatalogue(@PathVariable("catalogueId") final String catalogueId) {
    catalogueServiceUtil.deleteCatalogueService(catalogueId);
  }

  @GetMapping("/api/catalogues/{catalogueId}")
  public CompletableFuture<Catalogue> getCatalogue(
          @PathVariable("catalogueId") final String catalogueId) {
    return catalogueServiceUtil.getCatalogueService(catalogueId);
  }

  @GetMapping("/api/catalogues")
  public CompletableFuture<List<Catalogue>> getCatalogueStreamAll() {
    return catalogueServiceUtil.getCatalogueStreamAll();
  }

  @GetMapping("/api/catalogues-user/{userId}")
  public CompletableFuture<List<Catalogue>> getCatalogueStreamByUserId(
          @PathVariable("userId") final String userId) {
    return catalogueServiceUtil.getCatalogueStreamByUserId(userId);
  }

}