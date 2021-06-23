package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arjun.cataloguemicroservice.*;
import org.arjun.cataloguemicroservice.service.converter.Converter;
import org.arjun.cataloguemicroservice.service.item.ItemServiceUtil;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GRpcService
public class ItemService extends ItemServiceGrpc.ItemServiceImplBase {

  private final Logger logger =
          LogManager.getLogger(ItemService.class);

  @Autowired
  private ItemServiceUtil itemServiceUtil;

  @Autowired
  private Converter converter;

  @Override
  public void createItem(final CreateItemRequest request,
                         final StreamObserver<Item> responseObserver) {
    itemServiceUtil.createItemService(converter.toItem(request));
    responseObserver.onNext(request.getItem());
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<CreateItemStreamRequest> createItemStream(
          final StreamObserver<CreateItemStreamResponse> responseObserver) {
    final StreamObserver<CreateItemStreamRequest> requestStreamObserver =
            new StreamObserver<CreateItemStreamRequest>() {
      @Override
      public void onNext(final CreateItemStreamRequest value) {
        itemServiceUtil.createItemService(
                converter.toItem(converter.convertToCreateItemRequest(value)));
      }

      @Override
      public void onError(final Throwable t) {
        if (logger.isInfoEnabled()) {
          logger.info("Error :" + t.getMessage());
        }
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
  public void deleteItem(final DeleteItemRequest request,
                         final StreamObserver<Empty> responseObserver) {
    itemServiceUtil.deleteItemService(request.getItemId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }


  @Override
  public void getItem(final GetItemRequest request, final StreamObserver<Item> responseObserver) {
    final org.arjun.cataloguemicroservice.entity.Item item =
            itemServiceUtil.getItemService(request.getItemId(),request.getParentCatalogueId());
    responseObserver.onNext(itemServiceUtil.toProto(item));
    responseObserver.onCompleted();
  }

  @Override
  public void getItemStream(final GetItemStreamRequest request,
                            final StreamObserver<ItemStream> responseObserver) {
    if (!request.getCatalogueId().isBlank()) {
      itemServiceUtil.getItemStreamByCatalogueId(
              request.getCatalogueId()).forEach(e -> {
        responseObserver.onNext(ItemStream.newBuilder()
                .setItem(itemServiceUtil.toProto(e)).build());
      });
      responseObserver.onCompleted();
    } else if (!request.getUserId().isBlank()) {
      itemServiceUtil.getItemStreamByUserId(request.getUserId()).forEach(ele -> {
        responseObserver.onNext(ItemStream.newBuilder()
                .setItem(itemServiceUtil.toProto(ele)).build());
      });
      responseObserver.onCompleted();
    } else {
      itemServiceUtil.getItemStreamAll().forEach(ele -> {
        responseObserver.onNext(ItemStream.newBuilder().
                setItem(itemServiceUtil.toProto(ele)).build());
      });
      responseObserver.onCompleted();
    }
  }
}



