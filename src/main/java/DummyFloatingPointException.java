/**
 * Occasionally, the legacy SRPN calculator will throw a floating point exception. This exception
 * is not caught by the program, and the program is terminated.
 */
public class DummyFloatingPointException extends RuntimeException {

  /**
   * Creates a new floating point exception to represent a floating point exception in the legacy
   * SRPN that would cause program termination.
   */
  public DummyFloatingPointException() {
    super("Floating point exception(core dumped)");
  }

}
