package org.arjun.assignment1.item;

public class ItemDTO {
  /**
   * Name of item.
   */
  private String name;
  /**
   * type(RAW, MANUFACTURED, IMPORTED) of item.
   */
  private String type;
  /**
   * quantity of item.
   */
  private String quantity;
  /**
   * price of item.
   */
  private String price;

  public ItemDTO() {
    name = "";
    price = "";
    quantity = "";
    type = "";
  }

  /**
   * Getter.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter.
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Getter.
   */
  public String getType() {
    return type;
  }

  /**
   * Setter.
   */
  public void setType(final String type) {
    this.type = type;
  }

  /**
   * Getter.
   */
  public String getPrice() {
    return price;
  }

  /**
   * Setter.
   */
  public void setPrice(final String price) {
    this.price = price;
  }

  /**
   * Getter.
   */
  public String getQuantity() {
    return quantity;
  }

  /**
   * Setter.
   */
  public void setQuantity(final String quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "-name " + name + " -price " + price + " -quantity "
            + quantity + " -type " + type;
  }

}
