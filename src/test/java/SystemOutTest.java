import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;

public class SystemOutTest {

  protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  protected final PrintStream originalOut = System.out;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  private String getAllPrintedContent() {
    return outContent.toString();
  }

  protected List<String> getAllPrintedLinesAndRefresh() {
    List<String> printedLines =
        Arrays.stream(getAllPrintedContent().split("\n"))
              .map(this::removeTrailingNewLine)
              .collect(Collectors.toList());
    outContent.reset();
    return printedLines;
  }

  protected String getLatestPrintedContent() {
    List<String> lines = getAllPrintedLinesAndRefresh();
    if (!lines.isEmpty()) {
      return removeTrailingNewLine(lines.get(lines.size()-1));
    }
    return null; // No content has been printed
  }

  protected String removeTrailingNewLine(String input) {
    if (input.endsWith("\r\n")) {
      return input.substring(0, input.length()-2);
    } else if (input.endsWith("\n")) {
      return input.substring(0, input.length()-1);
    } else if (input.endsWith("\r")) {
      return input.substring(0, input.length()-1);
    } else {
      return input;
    }
  }

}
