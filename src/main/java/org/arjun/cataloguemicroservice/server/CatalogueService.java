package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.arjun.cataloguemicroservice.*;
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
    responseObserver.onNext(catalogueServiceUtil.toProto(catalogueServiceUtil
            .createCatalogueService(converter.toCatalogue(request))));
    responseObserver.onCompleted();
  }

  @Override
  public void deleteCatalogue(final DeleteCatalogueRequest request,
                              final StreamObserver<Empty> responseObserver) {
    catalogueServiceUtil.deleteCatalogueService(request.getCatalogueId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public void getCatalogue(final GetCatalogueRequest request,
                           final StreamObserver<Catalogue> responseObserver) {
    final Optional<org.arjun.cataloguemicroservice.entity.Catalogue> entry =
            catalogueServiceUtil.getCatalogueService(request.getCatalogueId());
    entry.map(e -> catalogueServiceUtil.toProto(e))
            .ifPresent(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  @Override
  public void getCatalogueStream(final GetCatalogueStreamRequest request,
                                 final StreamObserver<CatalogueStream> responseObserver) {
    if (request.getUserId().isBlank()) {
      catalogueServiceUtil.getCatalogueStreamAll().forEach(ele -> {
        responseObserver.onNext(CatalogueStream.newBuilder()
                .setCatalogue(catalogueServiceUtil.toProto(ele)).build());
      });
      responseObserver.onCompleted();
    } else {
      catalogueServiceUtil.getCatalogueStreamByUserId(request.getUserId())
              .forEach(ele -> {
                responseObserver.onNext(CatalogueStream.newBuilder()
                        .setCatalogue(catalogueServiceUtil.toProto(ele)).build());
              });
      responseObserver.onCompleted();
    }
  }
}
