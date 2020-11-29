import static org.junit.Assert.*;

import org.junit.Test;

public class DummySegmentationFaultExceptionTest {

  @Test
  public void testExceptionMessage() {
    assertEquals(
        "Segmentation fault (core dumped)",
        new DummySegmentationFaultException().getMessage()
    );
  }

}