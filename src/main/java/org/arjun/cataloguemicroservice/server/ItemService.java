package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import org.arjun.cataloguemicroservice.*;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.ItemRepo;
import org.arjun.cataloguemicroservice.repository.UserRepo;
import org.arjun.cataloguemicroservice.service.converter.Converter;
import org.arjun.cataloguemicroservice.service.item.ItemServiceUtil;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class ItemService extends ItemServiceGrpc.ItemServiceImplBase {
  @Autowired
  ItemRepo itemRepo;

  @Autowired
  CatalogueRepo catalogueRepo;

  @Autowired
  UserRepo userRepo;

  @Autowired
  ItemServiceUtil itemServiceUtil;

  @Autowired
  Converter converter;

  @Override
  public void createItem(CreateItemRequest request,
                         StreamObserver<Item> responseObserver) {
    itemServiceUtil.createItemService(converter.toItem(request));
    responseObserver.onNext(request.getItem());
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<CreateItemStreamRequest> createItemStream(
          StreamObserver<CreateItemStreamResponse> responseObserver) {
    StreamObserver<CreateItemStreamRequest> requestStreamObserver =
            new StreamObserver<CreateItemStreamRequest>() {
      @Override
      public void onNext(CreateItemStreamRequest value) {
        itemServiceUtil.createItemService(
                converter.toItem(converter.convertToCreateItemRequest(value)));
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("rpc call cancelled");
      }

      @Override
      public void onCompleted() {
        responseObserver.onNext(CreateItemStreamResponse.newBuilder()
                .setStatus("Success").setStatusCode(0).build());
        responseObserver.onCompleted();
      }
    };
    return requestStreamObserver;
  }

  @Override
  public void deleteItem(DeleteItemRequest request,
                         StreamObserver<Empty> responseObserver) {
    itemServiceUtil.deleteItemService(request.getItemId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }


  @Override
  public void getItem(GetItemRequest request, StreamObserver<Item> responseObserver) {
    org.arjun.cataloguemicroservice.entity.Item item =
            itemServiceUtil.getItemService(request.getItemId(),request.getParentCatalogueId());
    responseObserver.onNext(item.toProto());
    responseObserver.onCompleted();
  }

  @Override
  public void getItemStream(GetItemStreamRequest request,
                            StreamObserver<ItemStream> responseObserver) {
    if (!request.getCatalogueId().isBlank()) {
      itemServiceUtil.getItemStreamByCatalogueId(request.getCatalogueId()).forEach(e -> {
        responseObserver.onNext(ItemStream.newBuilder().setItem(e.toProto()).build());
      });
      responseObserver.onCompleted();
    } else if (!request.getUserId().isBlank()) {
      itemServiceUtil.getItemStreamByUserId(request.getUserId()).forEach(ele -> {
        responseObserver.onNext(ItemStream.newBuilder().setItem(ele.toProto()).build());
      });
      responseObserver.onCompleted();
    } else {
      itemServiceUtil.getItemStreamAll().forEach(ele -> {
        responseObserver.onNext(ItemStream.newBuilder().setItem(ele.toProto()).build());
      });
      responseObserver.onCompleted();
    }
  }
}



