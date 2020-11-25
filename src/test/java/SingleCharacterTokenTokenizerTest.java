import static org.junit.Assert.*;

import java.util.function.Function;
import org.junit.Test;

public class SingleCharacterTokenTokenizerTest {

  @Test
  public void testCanTokenizeEntireCommand() {
    String command = "a";

    Tokenizer<DummyToken> tokenizer = buildTokenizer(c -> true);
    TokenizationResult<DummyToken> result = tokenizer.tokenize(command);

    assertTrue(result.getRemainingCommand().isEmpty());
    assertEquals('a', result.getToken().getValue());
  }

  @Test
  public void testCanTokenizePartialCommand() {
    String command = "abc";

    Tokenizer<DummyToken> tokenizer = buildTokenizer(c -> true);
    TokenizationResult<DummyToken> result = tokenizer.tokenize(command);

    assertEquals('a', result.getToken().getValue());
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testExceptionsAreWrapped() {
    String command = "Test command";
    Exception exception = new Exception("Test exception");

    Tokenizer<DummyToken> tokenizer = new SingleCharacterTokenTokenizer<>() {
      @Override
      protected DummyToken buildToken(char character) throws Exception {
        throw exception;
      }

      @Override
      public boolean canTokenize(char character) {
        return true;
      }
    };

    try {
      tokenizer.tokenize(command);
      fail("No exception thrown");
    } catch (TokenizationException e) {
      assertEquals("Failed to tokenize token " + command.charAt(0), e.getMessage());
      assertEquals(exception, e.getCause());
    }
  }

  private SingleCharacterTokenTokenizer<DummyToken> buildTokenizer(
      Function<Character, Boolean> canTokenizeFunction) {
    return new SingleCharacterTokenTokenizer<>() {
      @Override
      protected DummyToken buildToken(char character) {
        return new DummyToken(character);
      }

      @Override
      public boolean canTokenize(char character) {
        return canTokenizeFunction.apply(character);
      }
    };
  }

  private static class DummyToken extends AbstractToken {
    private final char value;

    private DummyToken(char value) {
      this.value = value;
    }

    @Override
    public void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException {}

    public char getValue() {
      return value;
    }

    @Override
    public String getReadableValue() {
      return "dummy";
    }
  }

}