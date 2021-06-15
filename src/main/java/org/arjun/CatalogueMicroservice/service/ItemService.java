package org.arjun.CatalogueMicroservice.service;

import io.grpc.stub.StreamObserver;
import org.arjun.CatalogueMicroservice.*;

public class ItemService extends ItemServiceGrpc.ItemServiceImplBase {
  /**
   * @param responseObserver
   */
  @Override
  public StreamObserver<Item> addItems(StreamObserver<Status> responseObserver) {
    return super.addItems(responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteItem(Id request, StreamObserver<Status> responseObserver) {
    super.deleteItem(request, responseObserver);
  }

  /**
   * <pre>
   * Long Running operation
   * </pre>
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteItems(Empty request, StreamObserver<Status> responseObserver) {
    super.deleteItems(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getItem(Id request, StreamObserver<Item> responseObserver) {
    super.getItem(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getItemsCatalogue(Catalogue request, StreamObserver<Item> responseObserver) {
    super.getItemsCatalogue(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getItemsUser(User request, StreamObserver<Item> responseObserver) {
    super.getItemsUser(request, responseObserver);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getItemsAll(Empty request, StreamObserver<Item> responseObserver) {
    super.getItemsAll(request, responseObserver);
  }
}
