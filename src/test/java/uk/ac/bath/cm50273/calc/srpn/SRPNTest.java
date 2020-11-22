package uk.ac.bath.cm50273.calc.srpn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.DummyFloatingPointException;

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

}