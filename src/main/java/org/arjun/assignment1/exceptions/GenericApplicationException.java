package org.arjun.assignment1.exceptions;

/**
 * Class for custom exception.
 */
public class GenericApplicationException extends Exception {
  public static final long serialVersionUID = 43287433;

  /**
   * Constructor taking custom message for custom exception.
   */
  public GenericApplicationException(final String message) {
    super(message);
  }
}
