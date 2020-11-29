/**
 * Replicates the crashes of the legacy SRPN.
 */
public class FatalSrpnException extends RuntimeException {

  /**
   * Creates a new fatal SRPN exception that should cause the program to crash.
   * @param message The exception message.
   */
  public FatalSrpnException(String message) {
    super(message);
  }

}
