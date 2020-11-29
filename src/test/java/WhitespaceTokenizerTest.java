import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class WhitespaceTokenizerTest {

  private static final WhitespaceTokenizer TOKENIZER = new WhitespaceTokenizer();

  @Test
  public void testCannotTokenizeCommandWhenNotWhitespace() {
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder("Not whitespace");
    assertFalse(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.getTokens().isEmpty());
    assertEquals(0, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testCannotCommandWhenNotWhitespaceAtIndex() {
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder("Not whitespace");
    resultBuilder.setCurrentIndex(1);
    assertFalse(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.getTokens().isEmpty());
    assertEquals(1, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testCanTokenizeFullCommandOfSingleWhitespaceCharacter() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(" ");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  @Test
  public void testCanTokenizeCommandStartingWithWhitespaceCharacter() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(" command");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(1, resultBuilder.getCurrentIndex());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  @Test
  public void testCanTokenizeCommandStartingWithMultipleWhitespaceCharacters() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(" \t\ncommand");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(3, resultBuilder.getCurrentIndex());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  @Test
  public void testCanTokenizeWhitespaceInMiddleOfCommand() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("A command");
    resultBuilder.setCurrentIndex(1);
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(2, resultBuilder.getCurrentIndex());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  @Test
  public void testCanTokenizeMultipleWhitespacesInMiddleOfCommand() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("A \t\ncommand");
    resultBuilder.setCurrentIndex(1);
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertEquals(4, resultBuilder.getCurrentIndex());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  @Test
  public void testCanTokenizeCommandWithWhitespaceCharacterAtEnd() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("command ");
    resultBuilder.setCurrentIndex(7);
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  @Test
  public void testCanTokenizeCommandWithMultipleWhitespaceCharactersAtEnd() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("command \t\n");
    resultBuilder.setCurrentIndex(7);
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());
    assertContainsOneWhitespaceToken(resultBuilder.getTokens());
  }

  private void assertContainsOneWhitespaceToken(List<Token> tokens) {
    assertEquals(1, tokens.size());
    assertTrue(tokens.get(0) instanceof WhitespaceToken);
  }

}