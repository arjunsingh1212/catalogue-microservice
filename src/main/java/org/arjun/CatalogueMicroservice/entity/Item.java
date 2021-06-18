package org.arjun.CatalogueMicroservice.entity;

import org.arjun.assignment1.Type;
import org.arjun.assignment1.item.ItemEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Item extends ItemEntity {

  @Id
  private Integer itemId;
  private Integer catalogueId;

  public Item() {
  }

  public Item(Integer itemId, Integer catalogueId) {
    this.itemId = itemId;
    this.catalogueId = catalogueId;
  }

  public Item(String name, BigDecimal price, int quantity, Type type, Integer itemId, Integer catalogueId) {
    super(name, price, quantity, type);
    this.itemId = itemId;
    this.catalogueId = catalogueId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Integer getCatalogueId() {
    return catalogueId;
  }

  public void setCatalogueId(Integer catalogueId) {
    this.catalogueId = catalogueId;
  }

  @Override
  public String toString() {
    return "Item{" +
            "itemId=" + itemId +
            ", catalogueId=" + catalogueId +
            '}';
  }
}
