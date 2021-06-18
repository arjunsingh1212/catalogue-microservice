package org.arjun.assignment1.parser;

import org.arjun.assignment1.Type;
import org.arjun.assignment1.exceptions.GenericApplicationException;
import org.arjun.assignment1.item.ItemDTO;
import org.arjun.assignment1.item.ItemEntity;
import java.math.BigDecimal;

/**
 * Class to implement Parser.
 */
public class CommandLineParser implements Parsable {

  /**
   * Object of Valid class to be used in Parse method.
   */
  private final transient Valid validator;

  /**
   * Constructor to hardcode the Valid object.
   */
  public CommandLineParser() {
    validator = new Validator();
  }

  /**
   * Constructor to initialize Valid object using parameter.
   */
  public CommandLineParser(final Valid validator) {
    this.validator = validator;
  }

  /**
   * Building Item entity when everything is right and valid.
   */
  private ItemEntity buildUpItemEntity(final ItemDTO itemDTO) {
    final ItemEntity item = new ItemEntity();
    item.setName(itemDTO.getName());
    if (itemDTO.getPrice().isEmpty()) {
      item.setPrice(BigDecimal.valueOf(0.0));
    } else {
      item.setPrice(
              new BigDecimal(itemDTO.getPrice().replace(",","")));
    }
    if (itemDTO.getQuantity().isEmpty()) {
      item.setQuantity(1);
    } else {
      item.setQuantity(Integer.parseInt(itemDTO.getQuantity()));
    }
    item.setType(Type.valueOf(itemDTO.getType()));
    item.calculateTax(); //This populates the tax property of the Item
    return item;
  }

  /**
   * Parse Implementation.
   */
  @Override
  public ItemEntity parse(final ItemDTO itemDTO) throws GenericApplicationException {
    ItemEntity item;
    if (validator.validate(itemDTO)) {
      item = buildUpItemEntity(itemDTO);
    } else {
      throw new GenericApplicationException("Parsing or Validation Error!");
    }
    return item;
  }
}
