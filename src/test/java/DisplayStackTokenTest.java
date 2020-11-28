import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class DisplayStackTokenTest extends SystemOutTest {

  private final DisplayStackToken token = new DisplayStackToken();

  @Test
  public void testApplyPrintsMinimumIntegerWhenEmpty() {
    token.apply(new SizeRestrictedStack<>(1));
    assertEquals(Integer.toString(Integer.MIN_VALUE), getLatestPrintedContent());
  }

  @Test
  public void testApplyPrintsStackAsIntegersWhenPopulated() {
    SizeRestrictedStack<Double> sizeRestrictedStack = new SizeRestrictedStack<>(3);
    double operand1 = 34.6;
    double operand2 = 54.7;
    double operand3 = -45.6;
    sizeRestrictedStack.add(operand1);
    sizeRestrictedStack.add(operand2);
    sizeRestrictedStack.add(operand3);

    token.apply(sizeRestrictedStack);

    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(Integer.toString((int) operand1), printedLines.get(0));
    assertEquals(Integer.toString((int) operand2), printedLines.get(1));
    assertEquals(Integer.toString((int) operand3), printedLines.get(2));
  }

  @Test
  public void testHashCode() {
    assertEquals("display".hashCode(), token.hashCode());
  }

  @Test
  public void testEquals() {
    Token displayStackToken1 = new DisplayStackToken();
    Token displayStackToken2 = new DisplayStackToken();
    Token otherToken = new AdditionToken();

    assertTrue(displayStackToken1.equals(displayStackToken2));
    assertTrue(displayStackToken2.equals(displayStackToken1));
    assertFalse(displayStackToken1.equals(otherToken));
  }

}