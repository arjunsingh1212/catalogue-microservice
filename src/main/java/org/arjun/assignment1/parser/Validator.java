package org.arjun.assignment1.parser;

import org.arjun.assignment1.exceptions.GenericApplicationException;
import org.arjun.assignment1.item.ItemDTO;

public class Validator implements Valid {
  private boolean validateName(final String name) {
    return name.matches("^[ A-Za-z]+$") && name.length() <= 25;
  }

  //Price can contain commas also.
  private boolean validatePrice(final String price) {
    return price.matches("^\\d{1,2}+(,\\d{2})*(,\\d{3})(\\.\\d{1,2})?$")  //including comma
            || price.matches("^\\d+(\\.\\d{1,2})?$")  //Not including comma
            || price.matches("\\s*");  //Possible to be blank (then default will be set)
  }

  private boolean validateQuantity(final String quantity) {
    return quantity.matches("[0-9]*")
            && !quantity.matches("0+");  //Quantity can't be zero
  }

  private boolean validateType(final String type) {
    return "MANUFACTURED".equals(type)
            || "RAW".equals(type)
            || "IMPORTED".equals(type);
  }

  @Override
  public boolean validate(final ItemDTO item) throws GenericApplicationException {
    if (!validateName(item.getName())) {
      throw new GenericApplicationException("Invalid Name Format");
    }
    if (!validatePrice(item.getPrice())) {
      throw new GenericApplicationException("Invalid Price Format");
    }
    if (!validateQuantity(item.getQuantity())) {
      throw new GenericApplicationException("Invalid Quantity Format");
    }
    if (!validateType(item.getType())) {
      throw new GenericApplicationException("Invalid Type Format");
    }
    return true;
  }
}
