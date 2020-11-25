import static org.junit.Assert.*;

import java.util.function.Function;
import org.junit.Test;

public class MultiCharacterTokenTokenizerTest {

  @Test
  public void testCanTokenizeSingleCharacterAsFullCommand() {
    Tokenizer<DummyToken> tokenizer = buildTokenizer((character -> true));

    String command = "a";
    TokenizationResult<DummyToken> result = tokenizer.tokenize(command);
    assertEquals(command, result.getToken().getValue());
    assertTrue(result.getRemainingCommand().isEmpty());
  }

  @Test
  public void testCanTokenizeMultipleCharactersAsFullCommand() {
    Tokenizer<DummyToken> tokenizer = buildTokenizer((character -> true));

    String command = "Full command";
    TokenizationResult<DummyToken> result = tokenizer.tokenize(command);
    assertEquals(command, result.getToken().getValue());
    assertTrue(result.getRemainingCommand().isEmpty());
  }

  @Test
  public void testCanTokenizeSingleCharacterAsPartialCommand() {
    Tokenizer<DummyToken> tokenizer = buildTokenizer((character -> character == 'a'));

    String command = "abc";
    TokenizationResult<DummyToken> result = tokenizer.tokenize(command);
    assertEquals("a", result.getToken().getValue());
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testCanTokenizeMultipleCharactersAsPartialCommand() {
    Tokenizer<DummyToken> tokenizer =
        buildTokenizer((character -> character == 'a' || character == 'b' || character == 'c'));

    String command = "abcdefg";
    TokenizationResult<DummyToken> result = tokenizer.tokenize(command);
    assertEquals("abc", result.getToken().getValue());
    assertEquals(command.substring(3), result.getRemainingCommand());
  }

  @Test
  public void testExceptionsAreWrapped() {
    Exception exception = new Exception("Test exception message");

    Tokenizer<DummyToken> tokenizer =
        new MultiCharacterTokenTokenizer<>() {
      @Override
      protected DummyToken buildToken(String tokenString) throws Exception {
        throw exception;
      }

      @Override
      public boolean canTokenize(char character) {
        return true;
      }
    };

    String command = "A command";
    try {
      tokenizer.tokenize(command);
      fail("No exception was thrown");
    } catch (TokenizationException e) {
      assertEquals("Failed to tokenize token " + command, e.getMessage());
      assertEquals(exception, e.getCause());
    }
  }

  private MultiCharacterTokenTokenizer<DummyToken> buildTokenizer(
      Function<Character, Boolean> canTokenizeFunction) {
    return new MultiCharacterTokenTokenizer<>() {
      @Override
      protected DummyToken buildToken(String tokenString) {
        return new DummyToken(tokenString);
      }

      @Override
      public boolean canTokenize(char character) {
        return canTokenizeFunction.apply(character);
      }
    };
  }

  private static class DummyToken extends AbstractToken {
    private final String value;

    private DummyToken(String value) {
      this.value = value;
    }

    @Override
    public void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException {}

    public String getValue() {
      return value;
    }

    @Override
    public String getReadableValue() {
      return "dummy";
    }
  }

}