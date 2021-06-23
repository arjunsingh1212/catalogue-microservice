package org.arjun.cataloguemicroservice.service.item;

import java.util.ArrayList;
import java.util.List;
import org.arjun.cataloguemicroservice.entity.Item;
import org.arjun.cataloguemicroservice.repository.CatalogueRepo;
import org.arjun.cataloguemicroservice.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ItemServiceUtil implements org.arjun.cataloguemicroservice.service.Item {

  @Autowired
  private ItemRepo itemRepo;

  @Autowired
  private CatalogueRepo catalogueRepo;

  @Override
  public Item createItemService(final Item item) {
    return itemRepo.save(item);
  }

  @Override
  public void deleteItemService(final String itemId) {
    itemRepo.deleteById(itemId);
  }

  @Override
  public Item getItemService(final String itemId, final String parentCatalogueId) {
    return itemRepo.findByItemIdAndCatalogueId(itemId,parentCatalogueId);
  }

  @Override
  public List<Item> getItemStreamByCatalogueId(final String catalogueId) {
    return itemRepo.findByCatalogueId(catalogueId);
  }

  @Override
  public List<Item> getItemStreamByUserId(final String userId) {
    final List<Item> itemList = new ArrayList<>();
    catalogueRepo.findByUserId(userId).forEach(e -> {
      itemRepo.findByCatalogueId(e.getCatalogueId()).forEach(ele -> {
        itemList.add(ele);
      });
    });
    return itemList;
  }

  @Override
  public List<Item> getItemStreamAll() {
    return itemRepo.findAll();
  }

  @Override
  public org.arjun.cataloguemicroservice.Item toProto(final Item item) {
    org.arjun.cataloguemicroservice.Item.Type type;
    switch (item.getType()) {
      case "RAW":
        type = org.arjun.cataloguemicroservice.Item.Type.RAW;
        break;

      case "MANUFACTURED":
        type = org.arjun.cataloguemicroservice.Item.Type.MANUFACTURED;
        break;

      case "IMPORTED":
        type = org.arjun.cataloguemicroservice.Item.Type.IMPORTED;
        break;

      default:
        type = org.arjun.cataloguemicroservice.Item.Type.TYPE_UNSPECIFIED;
        break;
    }
    return org.arjun.cataloguemicroservice.Item.newBuilder()
            .setItemId(item.getItemId()).setCatalogueId(
                    item.getCatalogueId()).setName(item.getName())
            .setPrice(Double.parseDouble(
                    item.getPrice().toString())).setQuantity(item.getQuantity())
            .setType(type).build();
  }

  @Override
  public boolean checkItemExistenceByCatalogueIdAndItemName(
          final String catalogueId, final String itemName) {
    return itemRepo.findByCatalogueIdAndName(catalogueId,itemName) != null;
  }

  @Override
  public boolean checkItemExistenceById(final String itemId) {
    return itemRepo.findById(itemId).isPresent();
  }
}
