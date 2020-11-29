import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class AbstractTokenizerTest {

  @Test
  public void testAttemptTokenizationWillReturnResultIfSuccessful() {
    Tokenizer successfulTokenizer = new AbstractTokenizer() {
      @Override
      protected ExecutionState attemptTokenizationAndThrowExceptions(
          TokenizationResultBuilder resultBuilder) {
        return ExecutionState.SUCCEEDED;
      }
    };

    assertEquals(
        ExecutionState.SUCCEEDED,
        successfulTokenizer.attemptTokenization(mock(TokenizationResultBuilder.class))
    );
  }

  @Test
  public void testAttemptTokenizationWillWrapExceptionInTokenizationException() {
    Exception exceptionToThrow = new Exception("Dummy exception");
    Tokenizer badTokenizer = new AbstractTokenizer() {
      @Override
      protected ExecutionState attemptTokenizationAndThrowExceptions(
          TokenizationResultBuilder resultBuilder) throws Exception {
        throw exceptionToThrow;
      }
    };

    String unprocessedCommand = "Unprocessed command";
    TokenizationResultBuilder resultBuilder = mock(TokenizationResultBuilder.class);
    when(resultBuilder.getUnprocessedCommand()).thenReturn(unprocessedCommand);

    try {
      badTokenizer.attemptTokenization(resultBuilder);
    } catch (TokenizationException e) {
      assertEquals(
          "Failed to generate next token from remaining command " + unprocessedCommand,
          e.getMessage()
      );
      assertEquals(exceptionToThrow, e.getCause());
    }
  }

  @Test
  public void testAttemptTokenizationWillNotWrapFatalSrpnExceptions() {
    Exception exceptionToThrow = new FatalSrpnException("Test exception");
    Tokenizer badTokenizer = new AbstractTokenizer() {
      @Override
      protected ExecutionState attemptTokenizationAndThrowExceptions(
          TokenizationResultBuilder resultBuilder) throws Exception {
        throw exceptionToThrow;
      }
    };

    String unprocessedCommand = "Unprocessed command";
    TokenizationResultBuilder resultBuilder = mock(TokenizationResultBuilder.class);
    when(resultBuilder.getUnprocessedCommand()).thenReturn(unprocessedCommand);

    try {
      badTokenizer.attemptTokenization(resultBuilder);
    } catch (FatalSrpnException e) {
      assertEquals(e, exceptionToThrow);
    }
  }

}