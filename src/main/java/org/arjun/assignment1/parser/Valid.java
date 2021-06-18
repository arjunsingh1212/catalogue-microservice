package org.arjun.assignment1.parser;

import org.arjun.assignment1.exceptions.GenericApplicationException;
import org.arjun.assignment1.item.ItemDTO;

/**
 * Interface for validation methods.
 */
public interface Valid {

  /**
   * method to validate the string.
   */
  boolean validate(ItemDTO item) throws GenericApplicationException;
}
