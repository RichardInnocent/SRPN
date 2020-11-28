import static org.junit.Assert.*;

import org.junit.Test;

public class DivideByZeroExceptionTest {

  @Test
  public void testMessage() {
    assertEquals("Divide by 0.", new DivideByZeroException().getMessage());
  }

}