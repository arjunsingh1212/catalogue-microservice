package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arjun.cataloguemicroservice.Catalogue;
import org.arjun.cataloguemicroservice.CatalogueServiceGrpc;
import org.arjun.cataloguemicroservice.CatalogueStream;
import org.arjun.cataloguemicroservice.CreateCatalogueRequest;
import org.arjun.cataloguemicroservice.DeleteCatalogueRequest;
import org.arjun.cataloguemicroservice.GetCatalogueRequest;
import org.arjun.cataloguemicroservice.GetCatalogueStreamRequest;
import org.arjun.cataloguemicroservice.service.catalogue.CatalogueServiceUtil;
import org.arjun.cataloguemicroservice.service.converter.Converter;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class CatalogueService extends CatalogueServiceGrpc.CatalogueServiceImplBase {

  @Autowired
  private Converter converter;

  @Autowired
  private CatalogueServiceUtil catalogueServiceUtil;

  private final Logger logger =
          LogManager.getLogger(CatalogueService.class);

  @Override
  public void createCatalogue(final CreateCatalogueRequest request,
                              final StreamObserver<Catalogue> responseObserver) {
    if (catalogueServiceUtil.checkCatalogueExistenceByUserIdAndCatalogueName(
            request.getCatalogue().getUserId(),request.getCatalogue().getCatalogueName())) {
      final Status status = Status.ALREADY_EXISTS
              .withDescription("Catalogue with the given userId and catalogueName already present");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
    try {
      responseObserver.onNext(catalogueServiceUtil.toProto(catalogueServiceUtil
              .createCatalogueService(converter.toCatalogue(request)).get()));
    } catch (InterruptedException | ExecutionException e) {
      logger.info(e.getMessage());
    }
    responseObserver.onCompleted();
  }

  @Override
  public void deleteCatalogue(final DeleteCatalogueRequest request,
                              final StreamObserver<Empty> responseObserver) {
    if (catalogueServiceUtil.checkCatalogueExistenceById(request.getCatalogueId())) {
      catalogueServiceUtil.deleteCatalogueService(request.getCatalogueId());
      responseObserver.onNext(Empty.getDefaultInstance());
      responseObserver.onCompleted();
    } else {
      final Status status = Status.NOT_FOUND
              .withDescription("Catalogue with the given Catalogue Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getCatalogue(final GetCatalogueRequest request,
                           final StreamObserver<Catalogue> responseObserver) {
    if (catalogueServiceUtil.checkCatalogueExistenceById(request.getCatalogueId())) {
      final CompletableFuture<org.arjun.cataloguemicroservice.entity.Catalogue> entry =
              catalogueServiceUtil.getCatalogueService(request.getCatalogueId());
      try {
        responseObserver.onNext(catalogueServiceUtil.toProto(entry.get()));
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
      responseObserver.onCompleted();
    } else {
      final Status status = Status.NOT_FOUND
              .withDescription("Catalogue with the given Catalogue Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getCatalogueStream(final GetCatalogueStreamRequest request,
                                 final StreamObserver<CatalogueStream> responseObserver) {
    if (request.getUserId().isBlank()) {
      try {
        StreamSupport.stream(catalogueServiceUtil.getCatalogueStreamAll().get()
                .spliterator(), false).forEach(e -> {
                  responseObserver.onNext(CatalogueStream.newBuilder()
                      .setCatalogue(catalogueServiceUtil.toProto(e)).build());
                });
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
    } else {
      try {
        StreamSupport.stream(catalogueServiceUtil.getCatalogueStreamByUserId(
                request.getUserId()).get().spliterator(), false).forEach(e -> {
                  responseObserver.onNext(CatalogueStream.newBuilder()
                      .setCatalogue(catalogueServiceUtil.toProto(e)).build());
                });
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
    }
    responseObserver.onCompleted();
  }
}
