package org.arjun.CatalogueMicroservice.server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;
import org.arjun.CatalogueMicroservice.repository.CatalogueRepo;
import org.arjun.CatalogueMicroservice.repository.ItemRepo;
import org.arjun.CatalogueMicroservice.repository.UserRepo;
import org.arjun.assignment1.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ItemService extends ItemServiceGrpc.ItemServiceImplBase {
  @Autowired
  ItemRepo itemRepo;

  @Autowired
  CatalogueRepo catalogueRepo;

  @Autowired
  UserRepo userRepo;

  /**
   * <pre>
   * Add an item to the DB
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void createItem(CreateItemRequest request, StreamObserver<Item> responseObserver) {
    itemRepo.save(new org.arjun.CatalogueMicroservice.entity.Item(
            request.getItem().getName(),
            BigDecimal.valueOf(request.getItem().getPrice()),
            request.getItem().getQuantity(),
            Type.valueOf(request.getItem().getType().toString()),
            request.getItem().getItemId(),
            request.getItem().getCatalogueId()
    ));
    responseObserver.onNext(request.getItem());
    responseObserver.onCompleted();
  }

  /**
   * <pre>
   * Add a stream of Items to the DB
   * </pre>
   *
   * @param responseObserver
   */
  @Override
  public StreamObserver<CreateItemStreamRequest> createItemStream(StreamObserver<CreateItemStreamResponse> responseObserver) {
    return new StreamObserver<CreateItemStreamRequest>() {
      @Override
      public void onNext(CreateItemStreamRequest value) {
        itemRepo.save(new org.arjun.CatalogueMicroservice.entity.Item(
                value.getItem().getName(),
                BigDecimal.valueOf(value.getItem().getPrice()),
                value.getItem().getQuantity(),
                Type.valueOf(value.getItem().getType().toString()),
                value.getItem().getItemId(),
                value.getItem().getCatalogueId()));
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("rpc call cancelled");
      }

      @Override
      public void onCompleted() {
        responseObserver.onNext(CreateItemStreamResponse.newBuilder().
                setStatus("Success").setStatusCode(0).build());
        responseObserver.onCompleted();
      }
    };
  }

  /**
   * <pre>
   * Delete a specific Item
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteItem(DeleteItemRequest request, StreamObserver<Empty> responseObserver) {
    itemRepo.deleteById(request.getItemId());
    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  /**
   * <pre>
   * Get an specific Item
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getItem(GetItemRequest request, StreamObserver<Item> responseObserver) {
    Optional<org.arjun.CatalogueMicroservice.entity.Item> entry = itemRepo.findById(request.getItemId());
    entry.map(e -> e.toProto())
            .ifPresent(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  /**
   * <pre>
   * Get multiple items (all, of particular catalogue or of particular user)
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getItemStream(GetItemStreamRequest request, StreamObserver<ItemStream> responseObserver) {
    if (!request.getCatalogueId().isBlank()) {
      itemRepo.findByCatalogueId(request.getCatalogueId()).forEach(e -> {
        responseObserver.onNext(ItemStream.newBuilder().setItem(e.toProto()).build());
      });
      responseObserver.onCompleted();
    }
    else if (!request.getUserId().isBlank()) {
      catalogueRepo.findByUserId(request.getUserId()).forEach(e -> {
        itemRepo.findByCatalogueId(e.getCatalogueId()).forEach(ele -> {
          responseObserver.onNext(ItemStream.newBuilder().setItem(ele.toProto()).build());
        });
      });
      responseObserver.onCompleted();
    }
    else {
      itemRepo.findAll().forEach(ele -> {
        responseObserver.onNext(ItemStream.newBuilder().setItem(ele.toProto()).build());
      });
      responseObserver.onCompleted();
    }
  }
}
