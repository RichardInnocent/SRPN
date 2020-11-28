import static org.junit.Assert.*;

import org.junit.Test;

public class NegativePowerExceptionTest {

  @Test
  public void testMessage() {
    assertEquals("Negative power.", new NegativePowerException().getMessage());
  }

}