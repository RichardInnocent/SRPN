import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class OperatorTokenTest {

  @Test
  public void testHashCode() {
    Operator operator = mock(Operator.class);
    assertEquals(operator.hashCode(), createOperatorTokenFromOperator(operator).hashCode());
  }

  @Test
  public void testEquals() {
    Operator operator1 = mock(Operator.class);
    Operator operator2 = mock(Operator.class);

    OperatorToken token1a = createOperatorTokenFromOperator(operator1);
    OperatorToken token1b = createOperatorTokenFromOperator(operator1);
    OperatorToken token2 = createOperatorTokenFromOperator(operator2);

    assertTrue(token1a.equals(token1b));
    assertTrue(token1b.equals(token1a));
    assertFalse(token1a.equals(token2));
    assertFalse(token2.equals(token1a));
  }

  private static OperatorToken createOperatorTokenFromOperator(Operator operator) {
    return new OperatorToken() {
      @Override
      public Operator getOperator() {
        return operator;
      }
    };
  }

}