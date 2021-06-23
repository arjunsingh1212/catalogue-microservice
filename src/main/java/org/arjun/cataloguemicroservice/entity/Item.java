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
    //default Empty constructor req. by JPA.
  }

  public Item(final String name, final BigDecimal price, final int quantity,
              final String type, final String itemId, final String catalogueId) {
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

  public void setItemId(final String itemId) {
    this.itemId = itemId;
  }

  public String getCatalogueId() {
    return catalogueId;
  }

  public void setCatalogueId(final String catalogueId) {
    this.catalogueId = catalogueId;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Item{" + "itemId=" + itemId + ", catalogueId="
            + catalogueId + '}';
  }

}
