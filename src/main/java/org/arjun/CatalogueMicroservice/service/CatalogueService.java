package org.arjun.CatalogueMicroservice.service;

import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;

public class CatalogueService extends CatalogueServiceGrpc.CatalogueServiceImplBase {
  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void addCatalogue(Catalogue request, StreamObserver<Status> responseObserver) {
    super.addCatalogue(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteCatalogue(Id request, StreamObserver<Status> responseObserver) {
    super.deleteCatalogue(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getCatalogueUser(User request, StreamObserver<Catalogue> responseObserver) {
    super.getCatalogueUser(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getCataloguesAll(Empty request, StreamObserver<Catalogue> responseObserver) {
    super.getCataloguesAll(request, responseObserver);
  }
}
