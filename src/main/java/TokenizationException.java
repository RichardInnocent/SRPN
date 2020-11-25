/**
 * An exception that indicates that an unexpected problem was encountered when attempting to
 * tokenize a symbol.
 */
public class TokenizationException extends RuntimeException {

  /**
   * Creates a new exception to indicate that an unexpected problem encountered when attempting to
   * tokenize a symbol.
   * @param message The exception message.
   */
  public TokenizationException(String message) {
    super(message);
  }

  /**
   * Creates a new exception to indicate that an unexpected problem encountered when attempting to
   * tokenize a symbol.
   * @param message The exception message.
   * @param cause The cause of the exception.
   */
  public TokenizationException(String message, Throwable cause) {
    super(message, cause);
  }

}
