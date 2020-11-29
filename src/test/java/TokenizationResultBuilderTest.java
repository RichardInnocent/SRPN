import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.Test;

public class TokenizationResultBuilderTest {

  @Test(expected = NullPointerException.class)
  public void testCannotCreateResultBuilderWithNullCommand() {
    new TokenizationResultBuilder(null);
  }

  @Test
  public void testConstructorInitialisation() {
    String command = "A command";
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(command);
    assertEquals(command, resultBuilder.getOriginalCommand());
    assertEquals(0, resultBuilder.getCurrentIndex());
    assertTrue(resultBuilder.getTokens().isEmpty());
  }

  @Test
  public void testAddToken() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("A command");
    Token token = mock(Token.class);
    resultBuilder.addToken(token);
    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    assertEquals(token, tokens.get(0));
  }

  @Test(expected = NullPointerException.class)
  public void testCannotAddNullToken() {
    new TokenizationResultBuilder("A command").addToken(null);
  }

  @Test
  public void testSetCurrentIndex() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("A command");
    int newIndex = 3;
    resultBuilder.setCurrentIndex(newIndex);
    assertEquals(newIndex, resultBuilder.getCurrentIndex());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCannotSetCurrentIndexBelowZero() {
    new TokenizationResultBuilder("A command").setCurrentIndex(-1);
  }

  @Test
  public void testCannotRevertCurrentIndex() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("A command");
    int originalIndex = 3;
    resultBuilder.setCurrentIndex(originalIndex);
    try {
      resultBuilder.setCurrentIndex(originalIndex-1);
      fail("No exception thrown");
    } catch (IllegalArgumentException e) {
      assertEquals(originalIndex, resultBuilder.getCurrentIndex());
    }
  }

  @Test
  public void testIsComplete() {
    String command = "A command";
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(command);
    assertFalse(resultBuilder.isComplete());
    resultBuilder.setCurrentIndex(command.length()-1);
    assertFalse(resultBuilder.isComplete());
    resultBuilder.setCurrentIndex(command.length());
    assertTrue(resultBuilder.isComplete());
    resultBuilder.incrementCurrentIndex();
    assertTrue(resultBuilder.isComplete());
  }

  @Test
  public void testGetUnprocessedCommand() {
    String command = "A command";
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(command);
    assertEquals(command, resultBuilder.getUnprocessedCommand());
    resultBuilder.setCurrentIndex(3);
    assertEquals(command.substring(3), resultBuilder.getUnprocessedCommand());
    resultBuilder.setCurrentIndex(command.length()-1);
    assertEquals(command.substring(command.length()-1), resultBuilder.getUnprocessedCommand());
    resultBuilder.setCurrentIndex(command.length());
    assertTrue(resultBuilder.getUnprocessedCommand().isEmpty());
  }

  @Test
  public void testGetNextUnprocessedCharacter() {
    String command = "A command";
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(command);
    assertEquals(command.charAt(0), resultBuilder.getNextUnprocessedCharacter());
    resultBuilder.setCurrentIndex(3);
    assertEquals(command.substring(3).charAt(0), resultBuilder.getNextUnprocessedCharacter());
    resultBuilder.setCurrentIndex(command.length()-1);
    assertEquals(
        command.substring(command.length()-1).charAt(0),
        resultBuilder.getNextUnprocessedCharacter()
    );
    resultBuilder.setCurrentIndex(command.length());
    assertEquals('\0', resultBuilder.getNextUnprocessedCharacter());
  }

}