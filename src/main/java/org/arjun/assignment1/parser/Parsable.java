package org.arjun.assignment1.parser;

import org.arjun.assignment1.exceptions.GenericApplicationException;
import org.arjun.assignment1.item.ItemDTO;
import org.arjun.assignment1.item.ItemEntity;

/**
 * Interface for Parser methods.
 */
public interface Parsable {

  /**
   * Method to parse the string.
   */
  ItemEntity parse(ItemDTO itemDTO) throws GenericApplicationException;
}
