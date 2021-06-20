package org.arjun.CatalogueMicroservice.client;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;
import org.arjun.assignment1.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GRPCClient {

  ManagedChannel channel =
          ManagedChannelBuilder.forAddress(
                  "localhost", 6565).usePlaintext().build();
  UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
  CatalogueServiceGrpc.CatalogueServiceBlockingStub stubCatalogue =
          CatalogueServiceGrpc.newBlockingStub(channel);
  ItemServiceGrpc.ItemServiceBlockingStub stubItem =
          ItemServiceGrpc.newBlockingStub(channel);
  ItemServiceGrpc.ItemServiceStub asyncStubItem =
          ItemServiceGrpc.newStub(channel);


  private void createUser(String userName) {
    User user = User.newBuilder().setUserId(UUID.randomUUID().toString()).setUsername(userName).build();
    CreateUserRequest request = CreateUserRequest.newBuilder().setUser(user).build();
    User response = stub.createUser(request);
    System.out.println("Response: " + response.toString());
  }

  private void deleteUser(String userId) {
    DeleteUserRequest request = DeleteUserRequest.newBuilder().setUserId(userId).build();
    Empty response = stub.deleteUser(request);
    System.out.println("Response: " + response.toString());
  }

  private void getUser(String userId) {
    GetUserRequest request = GetUserRequest.newBuilder().setUserId(userId).build();
    User response = stub.getUser(request);
    System.out.println("Response: " + response.toString());
  }

  private void getUserStream() {
    GetUserStreamRequest request = GetUserStreamRequest.newBuilder().setAll(true).build();
    stub.getUserStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }

  private void createCatalogue(String userId,String catalogueName, String desc) {
    Catalogue catalogue = Catalogue.newBuilder().
            setCatalogueId(UUID.randomUUID().toString()).
            setUserId(userId).
            setCatalogueName(catalogueName).
            setDescription(desc).build();
    CreateCatalogueRequest request = CreateCatalogueRequest.newBuilder().setCatalogue(catalogue).build();
    Catalogue response = stubCatalogue.createCatalogue(request);
    System.out.println("Response: " + response.toString());
  }

  public void deleteCatalogue(String catalogueId) {
    DeleteCatalogueRequest request = DeleteCatalogueRequest.newBuilder().setCatalogueId(catalogueId).build();
    Empty response = stubCatalogue.deleteCatalogue(request);
    System.out.println("Response: " + response.toString());
  }

  private void getCatalogue(String catalogueId) {
    GetCatalogueRequest request = GetCatalogueRequest.newBuilder().setCatalogueId(catalogueId).build();
    Catalogue response = stubCatalogue.getCatalogue(request);
    System.out.println("Response: " + response.toString());
  }

  private void getCatalogueStream1() {
    GetCatalogueStreamRequest request = GetCatalogueStreamRequest.newBuilder().
            setUserId("3660ada0-f532-44f5-b28c-1ca573fc970b").build();
    stubCatalogue.getCatalogueStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }

  private void getCatalogueStream2() {
    GetCatalogueStreamRequest request = GetCatalogueStreamRequest.newBuilder().
            setAll(true).build();
    stubCatalogue.getCatalogueStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }

  private void createItem(String name, BigDecimal price, int quantity, Type type, String catalogueId) {
    Item item = Item.newBuilder().
            setItemId(UUID.randomUUID().toString()).
            setCatalogueId(catalogueId).
            setName(name).
            setPrice(price.doubleValue()).
            setQuantity(quantity).
            setType(Item.Type.valueOf(type.toString())).build();
    CreateItemRequest request = CreateItemRequest.newBuilder().setItem(item).build();
    Item response = stubItem.createItem(request);
    System.out.println("Response: " + response.toString());
  }

  private void createItemStream() {
    List<CreateItemStreamRequest> itemStream = new ArrayList<>();
    //Fill in the Array
    Item item1 = Item.newBuilder().
            setItemId(UUID.randomUUID().toString()).
            setCatalogueId("f7794a0b-8a34-449b-a5b1-af3609aa31c6").
            setName("Book").
            setPrice(30.30).
            setQuantity(3).
            setType(Item.Type.valueOf("RAW")).build();
    CreateItemStreamRequest request1 = CreateItemStreamRequest.newBuilder().setItem(item1).build();
    itemStream.add(request1);
    Item item2 = Item.newBuilder().
            setItemId(UUID.randomUUID().toString()).
            setCatalogueId("f7794a0b-8a34-449b-a5b1-af3609aa31c6").
            setName("Pencil").
            setPrice(5).
            setQuantity(10).
            setType(Item.Type.valueOf("IMPORTED")).build();
    CreateItemStreamRequest request2 = CreateItemStreamRequest.newBuilder().setItem(item2).build();
    itemStream.add(request2);

    // Latch
    CountDownLatch latch = new CountDownLatch(1);

    StreamObserver<CreateItemStreamRequest> requestStreamObserver =
            asyncStubItem.createItemStream(new StreamObserver<CreateItemStreamResponse>() {
      @Override
      public void onNext(CreateItemStreamResponse itemStreamResponse) {
        System.out.println(itemStreamResponse.toString());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("Error :" + t.getMessage() );
      }

      @Override
      public void onCompleted() {
        System.out.println("Finished");
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
      e.printStackTrace();
    }
  }

  public void deleteItem(String itemId) {
    DeleteItemRequest request = DeleteItemRequest.newBuilder().setItemId(itemId).build();
    try {
      Empty response = stubItem.deleteItem(request);
      System.out.println("Response: " + response.toString());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void getItem(String itemId) {
    GetItemRequest request = GetItemRequest.newBuilder().setItemId(itemId).build();
    Item response = stubItem.getItem(request);
    System.out.println("Response: " + response.toString());
  }

  private void getItemStream1() {
    GetItemStreamRequest request = GetItemStreamRequest.newBuilder().
            setUserId("3660ada0-f532-44f5-b28c-1ca573fc970b").build();
    stubItem.getItemStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }

  private void getItemStream2() {
    GetItemStreamRequest request = GetItemStreamRequest.newBuilder().
            setCatalogueId("f7794a0b-8a34-449b-a5b1-af3609aa31c6").build();
    stubItem.getItemStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }

  private void getItemStream3() {
    GetItemStreamRequest request = GetItemStreamRequest.newBuilder().
            setAll(true).build();
    stubItem.getItemStream(request).forEachRemaining(e -> {
      System.out.println("Response: " + e.toString());
    });
  }


  public static void main(String[] args) {
    GRPCClient client = new GRPCClient();
//    client.createUser("Partner C");
//    client.deleteUser("ae38f97c-5532-48e7-89ee-d6f70aad752a");
//    client.getUser("00ed1cba-b9f2-46fc-b662-4ff30955f470");
//    client.getUserStream();

//    client.createCatalogue("3660ada0-f532-44f5-b28c-1ca573fc970b",
//            "Books","This catalogue contains the books");

//    client.deleteCatalogue("c7fd4585-8c5f-4c88-9197-eb4f97c491bd");

//    client.getCatalogue("142e7a2b-a847-4e81-b7eb-014ee52efe64");
//    client.getCatalogueStream2();

//    client.createItem("Note Book",BigDecimal.valueOf(40.50),2,Type.RAW,
//            "f7794a0b-8a34-449b-a5b1-af3609aa31c6");

//      client.createItemStream();

      client.deleteItem("99eaf317-c27a-43d2-957f-8ad11d8e2a38");

//    client.getItem("8c0e7de8-1156-41f2-b582-d756785927dd");

//    client.getItemStream1();
//    client.getItemStream2();
//    client.getItemStream3();
  }
}
