import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class ExampleCourseworkTest2 extends SystemOutTest {

  private final SRPN srpn = new SRPN();

  @Test
  public void testCase1() {
    srpn.processCommand("3");
    srpn.processCommand("3");
    srpn.processCommand("*");
    srpn.processCommand("4");
    srpn.processCommand("4");
    srpn.processCommand("*");
    srpn.processCommand("+");
    srpn.processCommand("=");
    assertEquals("25", getLatestPrintedContent());
  }

  @Test
  public void testCase2() {
    srpn.processCommand("1234");
    srpn.processCommand("2345");
    srpn.processCommand("3456");
    srpn.processCommand("d");

    List<String> outputContent = getAllPrintedLinesAndRefresh();
    assertEquals(3, outputContent.size());
    assertEquals("1234", outputContent.get(0));
    assertEquals("2345", outputContent.get(1));
    assertEquals("3456", outputContent.get(2));

    srpn.processCommand("+");
    srpn.processCommand("d");
    outputContent = getAllPrintedLinesAndRefresh();
    assertEquals(2, outputContent.size());
    assertEquals("1234", outputContent.get(0));
    assertEquals("5801", outputContent.get(1));

    srpn.processCommand("+");
    srpn.processCommand("d");
    outputContent = getAllPrintedLinesAndRefresh();
    assertEquals(1, outputContent.size());
    assertEquals("7035", outputContent.get(0));

    srpn.processCommand("=");
    outputContent = getAllPrintedLinesAndRefresh();
    assertEquals(1, outputContent.size());
    assertEquals("7035", outputContent.get(0));
  }

}
