package org.arjun.CatalogueMicroservice.entity;


import org.arjun.assignment1.Type;
import org.arjun.assignment1.item.ItemEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Item extends ItemEntity {

  @Id
  private String itemId;
  private String catalogueId;

  public Item() {
  }

  public Item(String itemId, String catalogueId) {
    this.itemId = itemId;
    this.catalogueId = catalogueId;
  }

  public Item(String name, BigDecimal price, int quantity, Type type, String itemId, String catalogueId) {
    super(name, price, quantity, type);
    this.itemId = itemId;
    this.catalogueId = catalogueId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getCatalogueId() {
    return catalogueId;
  }

  public void setCatalogueId(String catalogueId) {
    this.catalogueId = catalogueId;
  }

  @Override
  public String toString() {
    return "Item{" +
            "itemId=" + itemId +
            ", catalogueId=" + catalogueId +
            '}';
  }

  public org.arjun.CatalogueMicroservice.Item toProto() {

    org.arjun.CatalogueMicroservice.Item.Type type;
    switch (getType()) {
      case RAW:
        type = org.arjun.CatalogueMicroservice.Item.Type.RAW;
        break;

      case MANUFACTURED:
        type = org.arjun.CatalogueMicroservice.Item.Type.MANUFACTURED;
        break;

      case IMPORTED:
        type = org.arjun.CatalogueMicroservice.Item.Type.IMPORTED;
        break;

      default:
        type = org.arjun.CatalogueMicroservice.Item.Type.TYPE_UNSPECIFIED;
        break;
    }
    return org.arjun.CatalogueMicroservice.Item.newBuilder().
            setItemId(getItemId()).setCatalogueId(getCatalogueId()).setName(getName()).
            setPrice(Double.parseDouble(getPrice().toString())).setQuantity(getQuantity()).
            setType(type).build();
  }
}
