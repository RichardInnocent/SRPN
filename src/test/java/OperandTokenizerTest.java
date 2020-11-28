import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class OperandTokenizerTest {

  private static final OperandTokenizer TOKENIZER = new OperandTokenizer();

  @Test
  public void testCannotTokenizeEmptyString() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("");
    assertFalse(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(0, resultBuilder.getCurrentIndex());
    assertTrue(resultBuilder.getTokens().isEmpty());
  }

  @Test
  public void testCannotTokenizeNonNumeric() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("Not a number");
    assertFalse(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(0, resultBuilder.getCurrentIndex());
    assertTrue(resultBuilder.getTokens().isEmpty());
  }

  @Test
  public void testTokenizationOfNumberAsFullCommand() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("12");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());

    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    Token token = tokens.get(0);
    assertTokenEqualTo(token, 12);
  }

  @Test
  public void testTokenizationOfNumberAsPartialCommand() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("12 ");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(2, resultBuilder.getCurrentIndex());

    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    Token token = tokens.get(0);
    assertTokenEqualTo(token, 12);
  }

  @Test
  public void testTokenizationOfOctalNumber() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("012");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());

    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    Token token = tokens.get(0);
    assertTokenEqualTo(token, 10);
  }

  @Test
  public void testTokenizationOfNumberStartingWith0ButIsThenFollowedBy8() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("082");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());

    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    Token token = tokens.get(0);
    assertTokenEqualTo(token, 82);
  }

  @Test
  public void testTokenizationOfNumberStartingWith0ButIsThenFollowedBy9() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("092");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());

    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    Token token = tokens.get(0);
    assertTokenEqualTo(token, 92);
  }

  @Test
  public void testTokenizationOfInvalidOctalNumber() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("01289");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());
    assertTrue(resultBuilder.getTokens().isEmpty());
  }

  private static void assertTokenEqualTo(Token token, int expectedValue) {
    assertTrue(token instanceof OperandToken);
    assertEquals(expectedValue, ((OperandToken) token).intValue());
  }

}