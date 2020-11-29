import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SRPNTest {

  private final InputLineTokenizer tokenizer = mock(InputLineTokenizer.class);
  private final TokenGrouper tokenGrouper = mock(TokenGrouper.class);
  private final TokenGroupExecutor executor = mock(TokenGroupExecutor.class);
  private final SRPN srpn = new SRPN(tokenizer, tokenGrouper, executor);

  @Test
  public void testDoesNotAttemptToProcessNullCommand() {
    srpn.processCommand(null);
    verifyNoInteractions(tokenizer, tokenGrouper, executor);
  }

  @Test
  public void testDoesNotAttemptToProcessEmptyCommand() {
    srpn.processCommand("");
    verifyNoInteractions(tokenizer, tokenGrouper, executor);
  }

  @Test
  public void testDoesNotAttemptToProcessBlankCommand() {
    srpn.processCommand(" \t\r\n");
    verifyNoInteractions(tokenizer, tokenGrouper, executor);
  }

  @Test
  public void testSuccessfulCompilationAndExecution() {
    String command = "A command";

    List<Token> tokens = Arrays.asList(mock(Token.class), mock(Token.class), mock(Token.class));
    when(tokenizer.tokenize(command)).thenReturn(tokens);

    List<TokenGroup> tokenGroups =
        Arrays.asList(mock(TokenGroup.class), mock(TokenGroup.class), mock(TokenGroup.class));
    when(tokenGrouper.group(tokens)).thenReturn(tokenGroups);

    srpn.processCommand(command);
    verify(executor, times(1)).execute(tokenGroups);
  }

}