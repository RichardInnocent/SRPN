import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

public class ExampleCourseworkTest4 extends SystemOutTest {

  private final SRPN srpn = new SRPN();

  @Test
  public void testCase1() {
    srpn.processCommand("1");
    srpn.processCommand("+");
    assertEquals("Stack underflow.", getLatestPrintedContent());
  }

  @Test
  public void testCase2() {
    srpn.processCommand("10");
    srpn.processCommand("5");
    srpn.processCommand("- 5");
    srpn.processCommand("+");
    srpn.processCommand("/");
    assertEquals("Stack underflow.", getLatestPrintedContent());
  }

  @Test
  public void testCase3() {
    srpn.processCommand("11+1+1+d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(2, printedLines.size());
    assertEquals("Stack underflow.", printedLines.get(0));
    assertEquals("13", printedLines.get(1));
  }

  @Test
  public void testCase4() {
    srpn.processCommand("# This i s a comment #");
    srpn.processCommand("1 2 + # And so i s t h i s #");
    srpn.processCommand("d");
    assertEquals("3", getLatestPrintedContent());
  }

  @Test
  public void testCase5() {
    srpn.processCommand("3 3 ^ 3 ^ 3 ^=");
    assertEquals("3", getLatestPrintedContent());
  }

  @Test
  public void testCase6() {
    srpn.processCommand("r r r r r r r r r r r r r r r r r r r r r r d r r r d");
    List<String> printedLines = getAllPrintedLinesAndRefresh();
    assertEquals(22 + 25, printedLines.size());
    assertEquals("Stack overflow.", printedLines.get(22));
    assertEquals("Stack overflow.", printedLines.get(23));
    for (int i = 0; i < printedLines.size() && i != 22 && i != 23; i++) {
      assertTrue(Integer.parseInt(printedLines.get(i)) >= 0);
    }
  }

}
