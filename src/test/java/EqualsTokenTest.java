import static org.junit.Assert.*;

import org.junit.Test;

public class EqualsTokenTest extends SystemOutTest {

  private static final EqualsToken TOKEN = new EqualsToken();

  @Test
  public void testReadableValue() {
    assertEquals("equals", TOKEN.getReadableValue());
  }

  @Test
  public void testPrintsEmptyStackWhenStackIsEmpty() {
    TOKEN.apply(new SizeRestrictedStack<>(1));
    assertEquals("Stack empty.", getLatestPrintedContent());
  }

  @Test
  public void testPrintsItemOnTopOfStackWhenStackIsPopulated() {
    double top = -11.5;
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(3);
    operandStack.add(20.4);
    operandStack.add(-12.5);
    operandStack.add(top);
    TOKEN.apply(operandStack);
    String output = getLatestPrintedContent();
    assertEquals(Integer.toString((int) top), output);
  }

}