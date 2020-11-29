import static org.junit.Assert.*;

import org.junit.Test;

public class FatalSrpnExceptionTest {

  @Test
  public void testMessage() {
    String message = "Test exception message";
    assertEquals(message, new FatalSrpnException(message).getMessage());
  }

}