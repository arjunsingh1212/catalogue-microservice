package org.arjun.CatalogueMicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;
import org.arjun.CatalogueMicroservice.repository.CatalogueRepo;
import org.arjun.CatalogueMicroservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CatalogueService extends CatalogueServiceGrpc.CatalogueServiceImplBase {
  @Autowired
  CatalogueRepo catalogueRepo;

  @Autowired
  UserRepo userRepo;

  /**
   * <pre>
   * Add a catalogue to the DB
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void createCatalogue(CreateCatalogueRequest request, StreamObserver<Catalogue> responseObserver) {
    catalogueRepo.save(new org.arjun.CatalogueMicroservice.entity.Catalogue(
            request.getCatalogue().getCatalogueId(),
            request.getCatalogue().getCatalogueName(),
            request.getCatalogue().getUserId(),
            request.getCatalogue().getDescription()));
    responseObserver.onNext(request.getCatalogue());
    responseObserver.onCompleted();
  }

  /**
   * <pre>
   * Delete a specific catalogue
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteCatalogue(DeleteCatalogueRequest request, StreamObserver<Empty> responseObserver) {
    catalogueRepo.deleteById(request.getCatalogueId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  /**
   * <pre>
   * Get a specific catalogue
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getCatalogue(GetCatalogueRequest request, StreamObserver<Catalogue> responseObserver) {
    Optional<org.arjun.CatalogueMicroservice.entity.Catalogue> entry = catalogueRepo.
            findById(request.getCatalogueId());
    entry.map(e -> e.toProto())
            .ifPresent(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  /**
   * <pre>
   * Get multiple catalogues (all, of one particular user)
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getCatalogueStream(GetCatalogueStreamRequest request, StreamObserver<CatalogueStream> responseObserver) {
    if (!request.getUserId().isBlank()) {
        catalogueRepo.findByUserId(request.getUserId()).forEach(ele -> {
          responseObserver.onNext(CatalogueStream.newBuilder().setCatalogue(ele.toProto()).build());
        });
      responseObserver.onCompleted();
    }
    else {
      catalogueRepo.findAll().forEach(ele -> {
        responseObserver.onNext(CatalogueStream.newBuilder().setCatalogue(ele.toProto()).build());
      });
      responseObserver.onCompleted();
    }
  }
}
