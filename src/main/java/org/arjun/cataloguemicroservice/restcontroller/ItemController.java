package org.arjun.cataloguemicroservice.restcontroller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.arjun.cataloguemicroservice.entity.Item;
import org.arjun.cataloguemicroservice.service.item.ItemServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ItemController {

  @Autowired
  private ItemServiceUtil itemServiceUtil;

  @PostMapping("/api/catalogues/{catalogueId}/items")
  public CompletableFuture<Item> createItem(final Item item) {
    return itemServiceUtil.createItemService(item);
  }

  @DeleteMapping("/api/catalogues/{catalogueId}/items/{itemId}")
  public void deleteItem(@PathVariable("itemId") final String itemId) {
    itemServiceUtil.deleteItemService(itemId);
  }

  @GetMapping("/api/catalogues/{catalogueId}/items/{itemId}")
  public CompletableFuture<Item> getItemByCatalogueIdAndItemId(
          @PathVariable("catalogueId") final String catalogueId,
          @PathVariable("itemId") final String itemId) {
    return itemServiceUtil.getItemService(itemId, catalogueId);
  }

  @GetMapping("/api/items")
  public CompletableFuture<List<Item>> getItemStreamAll() {
    return itemServiceUtil.getItemStreamAll();
  }

  @GetMapping("/api/items-user/{userId}")
  public CompletableFuture<List<Item>> getItemStreamByUserId(
          @PathVariable("userId") final String userId) {
    return itemServiceUtil.getItemStreamByUserId(userId);
  }

  @GetMapping("/api/catalogues/{catalogueId}/items")
  public CompletableFuture<List<Item>> getItemStreamByCatalogueId(
          @PathVariable("catalogueId") final String catalogueId) {
    return itemServiceUtil.getItemStreamByCatalogueId(catalogueId);
  }

}
