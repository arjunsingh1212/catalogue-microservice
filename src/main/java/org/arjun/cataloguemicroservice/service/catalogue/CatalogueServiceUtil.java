package org.arjun.cataloguemicroservice.service.catalogue;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class CatalogueServiceUtil
        implements org.arjun.cataloguemicroservice.service.Catalogue {

  @Autowired
  private CatalogueRepo catalogueRepo;

  private final ReadWriteLock lock = new ReentrantReadWriteLock();

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<Catalogue> createCatalogueService(final Catalogue catalogue) {
    lock.writeLock().lock();
    try {
      return CompletableFuture.completedFuture(catalogueRepo.save(catalogue));
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Override
  public void deleteCatalogueService(final String catalogueId) {
    catalogueRepo.deleteById(catalogueId);
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<Catalogue> getCatalogueService(final String catalogueId) {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(catalogueRepo.findById(catalogueId).get());
    } finally {
      lock.readLock().unlock();
    }
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<List<Catalogue>> getCatalogueStreamByUserId(final String userId) {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(catalogueRepo.findByUserId(userId));
    } finally {
      lock.readLock().unlock();
    }
  }

  @Async("threadPoolExecutor")
  @Override
  public CompletableFuture<List<Catalogue>> getCatalogueStreamAll() {
    lock.readLock().lock();
    try {
      return CompletableFuture.completedFuture(catalogueRepo.findAll());
    } finally {
      lock.readLock().unlock();
    }
  }

  @Override
  public org.arjun.cataloguemicroservice.Catalogue toProto(final Catalogue catalogue) {
    return org.arjun.cataloguemicroservice.Catalogue.newBuilder()
            .setUserId(catalogue.getUserId()).setCatalogueName(catalogue.getCatalogueName())
            .setDescription(catalogue.getDescription()).setCatalogueId(catalogue.getCatalogueId())
            .build();
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
