package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
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
    responseObserver.onNext(catalogueServiceUtil.toProto(catalogueServiceUtil
            .createCatalogueService(converter.toCatalogue(request))));
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
      final Optional<org.arjun.cataloguemicroservice.entity.Catalogue> entry =
              catalogueServiceUtil.getCatalogueService(request.getCatalogueId());
      entry.map(e -> catalogueServiceUtil.toProto(e))
              .ifPresent(responseObserver::onNext);
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
      catalogueServiceUtil.getCatalogueStreamAll().forEach(ele -> {
        responseObserver.onNext(CatalogueStream.newBuilder()
                .setCatalogue(catalogueServiceUtil.toProto(ele)).build());
      });
    } else {
      catalogueServiceUtil.getCatalogueStreamByUserId(request.getUserId())
              .forEach(ele -> {
                responseObserver.onNext(CatalogueStream.newBuilder()
                        .setCatalogue(catalogueServiceUtil.toProto(ele)).build());
              });
    }
    responseObserver.onCompleted();
  }
}
