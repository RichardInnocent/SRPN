/**
 * Occasionally, the legacy SRPN calculator will throw a segmentation fault and terminate. This
 * exception should be thrown when this behaviour is to be mimicked.
 */
public class DummySegmentationFaultException extends FatalSrpnException {

  /**
   * Creates a new segmentation fault exception which mimics the behaviour of the legacy SRPN where
   * a segmentation fault would occur and the program would terminate.
   */
  public DummySegmentationFaultException() {
    super("Segmentation fault (core dumped)");
  }

}
