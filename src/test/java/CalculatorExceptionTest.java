import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorExceptionTest {

  @Test
  public void testMessageConstructor() {
    String message = "Test message";
    assertEquals(message, new CalculatorException(message){}.getMessage());
  }

}