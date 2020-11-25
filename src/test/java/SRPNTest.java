import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import org.junit.Test;

public class SRPNTest extends SystemOutTest {

  private final SRPN srpn = new SRPN();

  @Test
  public void testAdditionOverMultipleLines() {
    srpn.processCommand("10");
    srpn.processCommand("2");
    srpn.processCommand("+");
    srpn.processCommand("=");
    assertEquals("12", getLatestPrintedContent());
  }

  @Test
  public void testAdditionOverSingleLine() {
    srpn.processCommand("10 2 + =");
    assertEquals("12", getLatestPrintedContent());
  }

  @Test
  public void testSubtractionOverMultipleLines() {
    srpn.processCommand("10");
    srpn.processCommand("2");
    srpn.processCommand("-");
    srpn.processCommand("=");
    assertEquals("8", getLatestPrintedContent());
  }

  @Test
  public void testSubtractOverSingleLine() {
    srpn.processCommand("10 2 - =");
    assertEquals("8", getLatestPrintedContent());
  }

  @Test
  public void testMultiplicationOverMultipleLines() {
    srpn.processCommand("10");
    srpn.processCommand("2");
    srpn.processCommand("*");
    srpn.processCommand("=");
    assertEquals("20", getLatestPrintedContent());
  }

  @Test
  public void testMultiplicationOverSingleLine() {
    srpn.processCommand("10 2 * =");
    assertEquals("20", getLatestPrintedContent());
  }

  @Test
  public void testDivisionOverMultipleLines() {
    srpn.processCommand("10");
    srpn.processCommand("3");
    srpn.processCommand("/");
    srpn.processCommand("=");
    assertEquals("3", getLatestPrintedContent());
  }

  @Test
  public void testDivisionOverSingleLine() {
    srpn.processCommand("10 3 / =");
    assertEquals("3", getLatestPrintedContent());
  }

  @Test
  public void testModuloOverMultipleLines() {
    srpn.processCommand("10");
    srpn.processCommand("3");
    srpn.processCommand("%");
    srpn.processCommand("=");
    assertEquals("1", getLatestPrintedContent());
  }

  @Test
  public void testModuleOverSingleLine() {
    srpn.processCommand("10 3 % =");
    assertEquals("1", getLatestPrintedContent());
  }

  @Test
  public void testRaiseToPowerOverMultipleLines() {
    srpn.processCommand("10");
    srpn.processCommand("3");
    srpn.processCommand("^");
    srpn.processCommand("=");
    assertEquals("1000", getLatestPrintedContent());
  }

  @Test
  public void testRaiseToPowerOverSingleLine() {
    srpn.processCommand("10 3 ^ =");
    assertEquals("1000", getLatestPrintedContent());
  }

  @Test
  public void testOneTwoThreeDivideEquals() {
    srpn.processCommand("1");
    srpn.processCommand("2");
    srpn.processCommand("3");
    srpn.processCommand("/");
    srpn.processCommand("=");
    assertEquals("0", getLatestPrintedContent());
  }

  @Test
  public void testDividingByZero() {
    srpn.processCommand("1");
    srpn.processCommand("0");
    srpn.processCommand("/");
    assertEquals("Divide by 0.", getLatestPrintedContent());

    srpn.processCommand("d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("1", printedLines.get(0));
    assertEquals("0", printedLines.get(1));
  }

  @Test
  public void testModuloByZero() {
    try {
      srpn.processCommand("1");
      srpn.processCommand("0");
      srpn.processCommand("%");
      fail("No exception thrown");
    } catch (DummyFloatingPointException e) {
      assertEquals("Floating point exception(core dumped)", e.getMessage());
    }
  }

  @Test
  public void testEqualsWhenStackIsEmpty() {
    srpn.processCommand("=");
    assertEquals("Stack empty.", getLatestPrintedContent());
  }

  @Test
  public void testDisplayStackWhenStackIsEmpty() {
    srpn.processCommand("d");
    assertEquals("-2147483648", getLatestPrintedContent());
  }

  @Test
  public void testMinusRUnderflows() {
    srpn.processCommand("-r=");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("1804289383", printedLines.get(0));
    assertEquals("Stack underflow.", printedLines.get(1));
  }

  @Test
  public void testCannotRaiseANumberToANegativePower1() {
    srpn.processCommand("35^-3d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(3, printedLines.size());
    assertEquals("Negative power.", printedLines.get(0));
    assertEquals("35", printedLines.get(1));
    assertEquals("-3", printedLines.get(2));
  }

  @Test
  public void testCannotRaiseANumberToANegativePower2() {
    srpn.processCommand("35^-3=");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("-3", printedLines.get(0));
    assertEquals("Negative power.", printedLines.get(1));
  }

  @Test
  public void testPlusMinusOperand() {
    srpn.processCommand("+-3d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("Stack underflow.", printedLines.get(0));
    assertEquals("-3", printedLines.get(1));
  }

  @Test
  public void testMinusMinusOperand() {
    srpn.processCommand("--3d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(3, printedLines.size());
    assertEquals("Stack underflow.", printedLines.get(0));
    assertEquals("Stack underflow.", printedLines.get(1));
    assertEquals("3", printedLines.get(2));
  }

  @Test
  public void testSequenceOfAllOperators() {
    srpn.processCommand("1674+457-159*54/421%27 d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    // TODO this doesn't work as the operands are stored as integers - they should be doubles
    assertEquals("1594", printedLines.get(0)); // This is the BODMAS answer from a calculator
  }

  @Test
  public void testSequenceOfNumbersThenOperators() {
    srpn.processCommand("1674 457 159 54 421 27");
    srpn.processCommand("+-*/% d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    assertEquals("328", printedLines.get(0));
  }

  @Test
  public void testTooManyOperatorsInASingleGroup1() {
    srpn.processCommand("3 12-3/ d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    assertEquals("-1", printedLines.get(0));
  }

  @Test
  public void testTooManyOperatorsInASingleGroup2() {
    srpn.processCommand("3 12/3- d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    assertEquals("-1", printedLines.get(0));
  }

  @Test
  public void testTooManyOperatorsInASingleGroup3a() {
    srpn.processCommand("12-3/ d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("Stack underflow.", printedLines.get(0));
    assertEquals("4", printedLines.get(1));
  }

  @Test
  public void testTooManyOperatorsInASingleGroup3b() {
    srpn.processCommand("12-3/1 d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    assertEquals("9", printedLines.get(0));
  }

  @Test
  public void testTooManyOperatorsInASingleGroup3c() {
    srpn.processCommand("12-3/1+ d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("Stack underflow.", printedLines.get(0));
    assertEquals("9", printedLines.get(1));
  }

  @Test
  public void testTooManyOperatorsInASingleGroup3d() {
    srpn.processCommand("12-3+1/ d");
    //                   12-3+
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("Stack underflow.", printedLines.get(0));
    assertEquals("15", printedLines.get(1));
  }

  @Test
  public void testCanHandleOctal() {
    srpn.processCommand("010 d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    assertEquals("8", printedLines.get(0));
  }

  @Test
  public void testCanHandleNegativeOctal() {
    srpn.processCommand("-010 d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(1, printedLines.size());
    assertEquals("-8", printedLines.get(0));
  }
}