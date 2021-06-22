package org.arjun.cataloguemicroservice.entity;


import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

  @Id
  private String itemId;
  private String catalogueId;
  private String name;
  private BigDecimal price;
  private int quantity;
  private String type;

  public Item() {
  }

  public Item(String name, BigDecimal price, int quantity, String type, String itemId, String catalogueId) {
    this.itemId = itemId;
    this.catalogueId = catalogueId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.type = type;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Item{" + "itemId=" + itemId + ", catalogueId="
            + catalogueId + '}';
  }

  public org.arjun.cataloguemicroservice.Item toProto() {

    org.arjun.cataloguemicroservice.Item.Type type;
    switch (getType()) {
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
            .setItemId(getItemId()).setCatalogueId(getCatalogueId()).setName(getName())
            .setPrice(Double.parseDouble(getPrice().toString())).setQuantity(getQuantity())
            .setType(type).build();
  }
}