package org.arjun.cataloguemicroservice.client;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arjun.assignment1.Type;
import org.arjun.cataloguemicroservice.*;

@SuppressWarnings({"PMD.GuardLogStatement",
        "PMD.UnusedPrivateMethod","LinguisticNaming"})
public class GRPCClient {

  private final Logger logger =
          LogManager.getLogger(GRPCClient.class);

  private final ManagedChannel channel =
          ManagedChannelBuilder.forAddress(
                  "localhost", 6565).usePlaintext().build();
  private final UserServiceGrpc.UserServiceBlockingStub stub =
          UserServiceGrpc.newBlockingStub(channel);
  private final CatalogueServiceGrpc.CatalogueServiceBlockingStub stubCatalogue =
          CatalogueServiceGrpc.newBlockingStub(channel);
  private final ItemServiceGrpc.ItemServiceBlockingStub stubItem =
          ItemServiceGrpc.newBlockingStub(channel);
  private final ItemServiceGrpc.ItemServiceStub asyncStubItem =
          ItemServiceGrpc.newStub(channel);


  private void createUser(final String userName) {
    final User user = User.newBuilder()
            .setUserId(UUID.randomUUID().toString()).setUsername(userName).build();
    final CreateUserRequest request = CreateUserRequest.newBuilder()
            .setUser(user).build();
    try {
      final User response = stub.createUser(request);
      if (logger.isInfoEnabled()) {
        logger.info("Response: " + response.toString());
      }
    } catch (Exception except) {
      Status status = Status.fromThrowable(except);
      logger.error(status.getCode() + " : " + status.getDescription());
    }
  }

  private void deleteUser(final String userId) {
    final DeleteUserRequest request = DeleteUserRequest.newBuilder().setUserId(userId).build();
    final Empty response = stub.deleteUser(request);
    logger.info("Response: " + "Successfully Deleted" + response.toString());
  }

  private void getUser(final String userId) {
    final GetUserRequest request = GetUserRequest.newBuilder().setUserId(userId).build();
    final User response = stub.getUser(request);
    logger.info("Response: " + response.toString());
  }

  private void getUserStream() {
    final GetUserStreamRequest request = GetUserStreamRequest.newBuilder()
            .setAll(true).build();
    stub.getUserStream(request).forEachRemaining(e -> {
      logger.info("Response: " + e.toString());
    });
  }

  private void createCatalogue(
          final String userId, final String catalogueName, final String desc) {
    final Catalogue catalogue = Catalogue.newBuilder()
            .setCatalogueId(UUID.randomUUID().toString())
            .setUserId(userId)
            .setCatalogueName(catalogueName)
            .setDescription(desc).build();
    final CreateCatalogueRequest request = CreateCatalogueRequest.newBuilder()
            .setCatalogue(catalogue).build();
    final Catalogue response = stubCatalogue.createCatalogue(request);
    logger.info("Response: " + response.toString());
  }

  private void deleteCatalogue(final String catalogueId) {
    final DeleteCatalogueRequest request = DeleteCatalogueRequest.newBuilder()
            .setCatalogueId(catalogueId).build();
    final Empty response = stubCatalogue.deleteCatalogue(request);
    logger.info("Response: " + response.toString());
  }

  private void getCatalogue(final String catalogueId) {
    final GetCatalogueRequest request = GetCatalogueRequest.newBuilder()
            .setCatalogueId(catalogueId).build();
    final Catalogue response = stubCatalogue.getCatalogue(request);
    logger.info("Response: " + response.toString());
  }

  private void getCatalogueStream1() {
    final GetCatalogueStreamRequest request = GetCatalogueStreamRequest.newBuilder()
            .setUserId("3660ada0-f532-44f5-b28c-1ca573fc970b").build();
    stubCatalogue.getCatalogueStream(request).forEachRemaining(e -> {
      logger.info("Response: " + e.toString());
    });
  }

  private void getCatalogueStream2() {
    final GetCatalogueStreamRequest request = GetCatalogueStreamRequest.newBuilder()
            .setAll(true).build();
    stubCatalogue.getCatalogueStream(request).forEachRemaining(e -> {
      logger.info("Response: " + e.toString());
    });
  }

  private void createItem(final String name, final BigDecimal price,
                          final int quantity, final Type type,
                          final String catalogueId) {
    final Item item = Item.newBuilder()
            .setItemId(UUID.randomUUID().toString())
            .setCatalogueId(catalogueId)
            .setName(name)
            .setPrice(price.doubleValue())
            .setQuantity(quantity)
            .setType(Item.Type.valueOf(type.toString())).build();
    final CreateItemRequest request = CreateItemRequest.newBuilder().setItem(item).build();
    final Item response = stubItem.createItem(request);
    logger.info("Response: " + response.toString());
  }

  private void createItemStream() {
    final List<CreateItemStreamRequest> itemStream = new ArrayList<>();
    //Fill in the Array
    final Item item1 = Item.newBuilder()
            .setItemId(UUID.randomUUID().toString())
            .setCatalogueId("f7794a0b-8a34-449b-a5b1-af3609aa31c6")
            .setName("Book")
            .setPrice(30.30)
            .setQuantity(3)
            .setType(Item.Type.valueOf("RAW")).build();
    final CreateItemStreamRequest request1 =
            CreateItemStreamRequest.newBuilder().setItem(item1).build();
    itemStream.add(request1);
    final Item item2 = Item.newBuilder()
            .setItemId(UUID.randomUUID().toString())
            .setCatalogueId("f7794a0b-8a34-449b-a5b1-af3609aa31c6")
            .setName("Pencil")
            .setPrice(5)
            .setQuantity(10)
            .setType(Item.Type.valueOf("IMPORTED")).build();
    final CreateItemStreamRequest request2 =
            CreateItemStreamRequest.newBuilder().setItem(item2).build();
    itemStream.add(request2);

    // Latch
    final CountDownLatch latch = new CountDownLatch(1);

    final StreamObserver<CreateItemStreamRequest> requestStreamObserver =
            asyncStubItem.createItemStream(new StreamObserver<CreateItemStreamResponse>() {

              @Override
              public void onNext(final CreateItemStreamResponse itemStreamResponse) {
                logger.info(itemStreamResponse.toString());
              }

              @Override
              public void onError(final Throwable t) {
                logger.info("Error :" + t.getMessage());
              }

              @Override
              public void onCompleted() {
                logger.info("Finished");
                latch.countDown();
              }
            });
    // Send items selected from the list.
    itemStream.forEach(e -> {
      requestStreamObserver.onNext(e);
    });

    // Mark the end of requests
    requestStreamObserver.onCompleted();

    try {
      latch.await(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      logger.info("Error :" + e.getMessage());
    }
  }

  private void deleteItem(final String itemId) {
    final DeleteItemRequest request =
            DeleteItemRequest.newBuilder().setItemId(itemId).build();
    try {
      final Empty response = stubItem.deleteItem(request);
      logger.info("Response: " + response.toString());
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }

  private void getItem(final String itemId, final String parentCatalogueId) {
    final GetItemRequest request = GetItemRequest.newBuilder()
            .setItemId(itemId)
            .setParentCatalogueId(parentCatalogueId).build();
    final Item response = stubItem.getItem(request);
    logger.info("Response: " + response.toString());
  }

  private void getItemStreamUsingUserId(final String userId) {
    final GetItemStreamRequest request = GetItemStreamRequest.newBuilder()
            .setUserId(userId).build();
    stubItem.getItemStream(request).forEachRemaining(e -> {
      logger.info("Response: " + e.toString());
    });
  }

  private void getItemStreamUsingCatalogueId(final String catalogueId) {
    final GetItemStreamRequest request = GetItemStreamRequest.newBuilder()
            .setCatalogueId(catalogueId).build();
    stubItem.getItemStream(request).forEachRemaining(e -> {
      logger.info("Response: " + e.toString());
    });
  }

  private void getItemStreamAll() {
    final GetItemStreamRequest request = GetItemStreamRequest.newBuilder()
            .setAll(true).build();
    stubItem.getItemStream(request).forEachRemaining(e -> {
      logger.info("Response: " + e.toString());
    });
  }


  public static void main(final String[] args) {
    final GRPCClient client = new GRPCClient();
//    client.createUser("Partner X");
//    client.deleteUser("ae38f97c-5532-48e7-89ee-d6f70aad752a");
    client.getUser("00ed1cba-b9f2-46fc-b662-4ff30955f470");
//    client.getUserStream();
//
//    client.createCatalogue("3660ada0-f532-44f5-b28c-1ca573fc970b",
//            "Books","This catalogue contains the books");
//
//    client.deleteCatalogue("c7fd4585-8c5f-4c88-9197-eb4f97c491bd");
//
//    client.getCatalogue("142e7a2b-a847-4e81-b7eb-014ee52efe64");
//    client.getCatalogueStream2();
//
//    client.createItem("Note Book",BigDecimal.valueOf(40.50),2,Type.RAW,
//            "f7794a0b-8a34-449b-a5b1-af3609aa31c6");
//
//    client.createItemStream();
//
//    client.deleteItem("99eaf317-c27a-43d2-957f-8ad11d8e2a38");
//
//    client.getItem("8c0e7de8-1156-41f2-b582-d756785927dd",
//            "f7794a0b-8a34-449b-a5b1-af3609aa31c6");
//
//    client.getItemStreamUsingUserId("3660ada0-f532-44f5-b28c-1ca573fc970b");
//    client.getItemStreamUsingCatalogueId("f7794a0b-8a34-449b-a5b1-af3609aa31c6");
//    client.getItemStreamAll();
  }
}
