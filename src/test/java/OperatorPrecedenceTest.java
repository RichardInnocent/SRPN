import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorPrecedenceTest {

  @Test
  public void testPrecedences() {
    assertTrue(OperatorPrecedence.MODULO > OperatorPrecedence.RAISE_TO_POWER);
    assertTrue(OperatorPrecedence.RAISE_TO_POWER > OperatorPrecedence.DIVISION);
    assertTrue(OperatorPrecedence.DIVISION > OperatorPrecedence.MULTIPLICATION);
    assertTrue(OperatorPrecedence.MULTIPLICATION > OperatorPrecedence.ADDITION);
    assertTrue(OperatorPrecedence.ADDITION > OperatorPrecedence.SUBTRACTION);
  }

}