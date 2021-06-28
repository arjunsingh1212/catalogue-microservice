package org.arjun.cataloguemicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.ExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arjun.cataloguemicroservice.CreateItemRequest;
import org.arjun.cataloguemicroservice.CreateItemStreamRequest;
import org.arjun.cataloguemicroservice.CreateItemStreamResponse;
import org.arjun.cataloguemicroservice.DeleteItemRequest;
import org.arjun.cataloguemicroservice.GetItemRequest;
import org.arjun.cataloguemicroservice.GetItemStreamRequest;
import org.arjun.cataloguemicroservice.Item;
import org.arjun.cataloguemicroservice.ItemServiceGrpc;
import org.arjun.cataloguemicroservice.ItemStream;
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
    if (itemServiceUtil.checkItemExistenceByCatalogueIdAndItemName(
            request.getItem().getCatalogueId(), request.getItem().getName())) {
      final Status status = Status.ALREADY_EXISTS
              .withDescription("Item with the given catalogueId and Item Name already present");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
    itemServiceUtil.createItemService(converter.toItem(request));
    responseObserver.onNext(request.getItem());
    responseObserver.onCompleted();

  }

  @Override
  public StreamObserver<CreateItemStreamRequest> createItemStream(
          final StreamObserver<CreateItemStreamResponse> responseObserver) {
    return new StreamObserver<CreateItemStreamRequest>() {
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
  }

  @Override
  public void deleteItem(final DeleteItemRequest request,
                         final StreamObserver<Empty> responseObserver) {
    if (itemServiceUtil.checkItemExistenceById(request.getItemId())) {
      itemServiceUtil.deleteItemService(request.getItemId());
      responseObserver.onNext(Empty.getDefaultInstance());
      responseObserver.onCompleted();
    } else {
      final Status status = Status.NOT_FOUND
              .withDescription("Item with the given item Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }


  @Override
  public void getItem(final GetItemRequest request, final StreamObserver<Item> responseObserver) {
    if (itemServiceUtil.checkItemExistenceById(request.getItemId())) {
      org.arjun.cataloguemicroservice.entity.Item item = null;
      try {
        item = itemServiceUtil.getItemService(request.getItemId(),
                request.getParentCatalogueId()).get();
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
      responseObserver.onNext(itemServiceUtil.toProto(item));
      responseObserver.onCompleted();
    } else {
      final Status status = Status.NOT_FOUND
              .withDescription("Item with the given item Id not found");
      responseObserver.onError(status.asRuntimeException());
      return;
    }
  }

  @Override
  public void getItemStream(final GetItemStreamRequest request,
                            final StreamObserver<ItemStream> responseObserver) {
    if (request.getCatalogueId().isBlank() && request.getUserId().isBlank()) {
      try {
        itemServiceUtil.getItemStreamAll().get()
                .forEach(e -> responseObserver.onNext(ItemStream.newBuilder()
                        .setItem(itemServiceUtil.toProto(e)).build()));
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
      responseObserver.onCompleted();
    } else if (request.getCatalogueId().isBlank()) {
      try {
        itemServiceUtil.getItemStreamByUserId(request.getUserId()).get()
                .forEach(e -> responseObserver.onNext(ItemStream.newBuilder()
                        .setItem(itemServiceUtil.toProto(e)).build()));
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
      responseObserver.onCompleted();
    } else {
      try {
        itemServiceUtil.getItemStreamByCatalogueId(request.getCatalogueId()).get()
                .forEach(e -> responseObserver.onNext(ItemStream.newBuilder()
                        .setItem(itemServiceUtil.toProto(e)).build()));
      } catch (InterruptedException | ExecutionException e) {
        logger.info(e.getMessage());
      }
      responseObserver.onCompleted();
    }
  }
}



