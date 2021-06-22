package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.arjun.cataloguemicroservice.*;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.arjun.cataloguemicroservice.service.catalogue.CatalogueServiceUtil;
import org.arjun.cataloguemicroservice.service.converter.Converter;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class CatalogueService extends CatalogueServiceGrpc.CatalogueServiceImplBase {
  @Autowired
  CatalogueRepo catalogueRepo;

  @Autowired
  UserRepo userRepo;

  @Autowired
  Converter converter;

  @Autowired
  CatalogueServiceUtil catalogueServiceUtil;

  @Override
  public void createCatalogue(CreateCatalogueRequest request, StreamObserver<Catalogue> responseObserver) {
    responseObserver.onNext(catalogueServiceUtil.createCatalogueService(converter.toCatalogue(request)).toProto());
    responseObserver.onCompleted();
  }

  @Override
  public void deleteCatalogue(DeleteCatalogueRequest request,
                              StreamObserver<Empty> responseObserver) {
    catalogueServiceUtil.deleteCatalogueService(request.getCatalogueId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public void getCatalogue(GetCatalogueRequest request,
                           StreamObserver<Catalogue> responseObserver) {
    Optional<org.arjun.cataloguemicroservice.entity.Catalogue> entry =
            catalogueServiceUtil.getCatalogueService(request.getCatalogueId());
    entry.map(e -> e.toProto())
            .ifPresent(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  @Override
  public void getCatalogueStream(GetCatalogueStreamRequest request,
                                 StreamObserver<CatalogueStream> responseObserver) {
    if (!request.getUserId().isBlank()) {
      catalogueServiceUtil.getCatalogueStreamByUserId(request.getUserId())
              .forEach(ele -> {
                responseObserver.onNext(CatalogueStream.newBuilder()
                        .setCatalogue(ele.toProto()).build());
              });
      responseObserver.onCompleted();
    } else {
      catalogueServiceUtil.getCatalogueStreamAll().forEach(ele -> {
        responseObserver.onNext(CatalogueStream.newBuilder()
                .setCatalogue(ele.toProto()).build());
      });
      responseObserver.onCompleted();
    }
  }
}
