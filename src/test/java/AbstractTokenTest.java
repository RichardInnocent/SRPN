import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractTokenTest {

  @Test
  public void testToString() {
    String readableValue = "Test readable value";

    Token token = new AbstractToken() {
      @Override
      public String getReadableValue() {
        return readableValue;
      }

      @Override
      public void apply(SizeRestrictedStack<Double> operandStack) {}
    };

    assertEquals(readableValue, token.getReadableValue());
    assertEquals(readableValue, token.toString());
  }

}