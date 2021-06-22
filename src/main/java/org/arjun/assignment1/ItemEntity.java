package org.arjun.assignment1;

import java.math.BigDecimal;
import org.arjun.assignment1.Type;

/**
 * Class as blueprint for Item Entity.
 */
public class ItemEntity {

  /**
   * Name of item.
   */
  private String name;
  /**
   * type(RAW, MANUFACTURED, IMPORTED) of item.
   */
  private Type type;
  /**
   * quantity of item.
   */
  private int quantity;
  /**
   * price of item.
   */
  private BigDecimal price;
  /**
   * tax of item.
   */
  private BigDecimal tax;

  public ItemEntity() {
    name = "";
    type = Type.UNKNOWN;
    quantity = 0;
    price = BigDecimal.valueOf(0);
  }

  /**
   * Overloaded Constructor.
   */
  public ItemEntity(final String name, final BigDecimal price,
                    final int quantity, final Type type) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.type = type;
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
  public Type getType() {
    return type;
  }

  /**
   * Setter.
   */
  public void setType(final Type type) {
    this.type = type;
  }

  /**
   * Getter.
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Setter.
   */
  public void setPrice(final BigDecimal price) {
    this.price = price;
  }

  /**
   * Getter.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Setter.
   */
  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  /**
   * Getter.
   */
  public BigDecimal getTax() {
    return tax;
  }

  /**
   * Setter.
   */
  public void setTax(final BigDecimal tax) {
    this.tax = tax;
  }

  /**
   * Overloading toString().
   */
  @Override
  public String toString() {
    return name + price + quantity + type;
  }

  /**
   * method that calculates the tax.
   */
  public void calculateTax() {
    switch (type) {
      case RAW:
        //raw tax: 12.5% of the item cost
        tax = price.multiply(BigDecimal.valueOf(0.125));
        break;
      case MANUFACTURED:
        //manufactured tax: 12.5% of the item cost + 2% of (item cost + 12.5% of the item cost)
        tax = price.multiply(BigDecimal.valueOf(0.125));
        tax = tax.add(price.add(tax).multiply(BigDecimal.valueOf(0.02)));
        break;
      case IMPORTED:
        final BigDecimal surcharge100 = BigDecimal.valueOf(100);
        final BigDecimal surcharge200 = BigDecimal.valueOf(200);
        //imported: 10% import duty on item cost + a surcharge
        // (surcharge is: Rs. 5 if the final cost after applying tax &
        // import duty is up to Rs. 100, Rs. 10 if the cost exceeds 100 and up to 200
        // and 5% of the final cost if it exceeds 200).
        final BigDecimal importDuty = price.multiply(BigDecimal.valueOf(0.1));
        //Further additional surcharge to be added according to final cost
        final BigDecimal finalCost = price.add(importDuty);
        if (finalCost.compareTo(surcharge100) <= 0) {
          tax = finalCost.add(BigDecimal.valueOf(5));
        } else if (finalCost.compareTo(surcharge100) > 0
                && finalCost.compareTo(surcharge200) <= 0) {
          tax = finalCost.add(BigDecimal.valueOf(10));
        } else if (finalCost.compareTo(surcharge200) > 0) {
          tax = finalCost.add(finalCost.multiply(BigDecimal.valueOf(0.05)));
        }
        tax = tax.subtract(price); //subtract price to have only tax, not final price
        break;
      default:
        tax = BigDecimal.valueOf(0);
        break;
    }
  }
}

