import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

public class InputLineTokenizerTest {

  private static final InputLineTokenizer TOKENIZER = new InputLineTokenizer();

  @Test
  public void testCanTokenizeSmallNumbers() {
    String command = "35";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(IntegerToken.forValue(command), tokens.get(0));
  }

  @Test
  public void testCanTokenizeLargeNumbers() {
    String command = "1234567890123456789012345678901234567890";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(IntegerToken.forValue(command), tokens.get(0));
  }

  @Test
  public void testCanTokenizeNumberOf120CharactersInLength() {
    String command = "9".repeat(120);
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(IntegerToken.forValue(command), tokens.get(0));
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void testCannotTokenizeNumberOf121CharactersInLength() {
    TOKENIZER.tokenize("1" + "0".repeat(120));
  }

  @Test
  public void testCanTokenizeAdditionSigns() {
    String command = "+";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(new AdditionToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeMinusSigns() {
    String command = "-";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(new SubtractionToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeMultiplicationSigns() {
    String command = "*";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(new MultiplicationToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeDivisionSigns() {
    String command = "/";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(new DivisionToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeModuloSigns() {
    String command = "%";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(new ModuloToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeRaiseToPowerSigns() {
    String command = "^";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(1, tokens.size());
    assertEquals(new RaiseToPowerToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeEqualsSigns() {
    List<Token> tokens = TOKENIZER.tokenize("=");
    assertEquals(1, tokens.size());
    assertEquals(new EqualsToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeDisplaySign() {
    List<Token> tokens = TOKENIZER.tokenize("d");
    assertEquals(1, tokens.size());
    assertEquals(new DisplayStackToken(), tokens.get(0));
  }

  @Test
  public void testCanTokenizeRandomNumber() {
    List<Token> tokens = TOKENIZER.tokenize("r");
    assertEquals(1, tokens.size());
    assertTrue(tokens.get(0) instanceof RandomNumberToken);
  }

  @Test
  public void testInvalidTokensAreIgnored() {
    assertTrue(TOKENIZER.tokenize("£$\\").isEmpty());
  }

  @Test
  public void testCanTokenizeComplexSequence() {
    String command = "1 \t2 \n+= \r5r d 12345678901234567890 -656";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(16, tokens.size());
    assertEquals(IntegerToken.forValue(1), tokens.get(0));
    assertEquals(new WhitespaceToken(), tokens.get(1));
    assertEquals(IntegerToken.forValue(2), tokens.get(2));
    assertEquals(new WhitespaceToken(), tokens.get(3));
    assertEquals(new AdditionToken(), tokens.get(4));
    assertEquals(new EqualsToken(), tokens.get(5));
    assertEquals(new WhitespaceToken(), tokens.get(6));
    assertEquals(IntegerToken.forValue(5), tokens.get(7));
    assertTrue(tokens.get(8) instanceof RandomNumberToken); // could be any number
    assertEquals(new WhitespaceToken(), tokens.get(9));
    assertEquals(new DisplayStackToken(), tokens.get(10));
    assertEquals(new WhitespaceToken(), tokens.get(11));
    assertEquals(IntegerToken.forValue("12345678901234567890"), tokens.get(12));
    assertEquals(new WhitespaceToken(), tokens.get(13));
    assertEquals(new SubtractionToken(), tokens.get(14));
    assertEquals(IntegerToken.forValue(656), tokens.get(15));
  }

  @Test
  public void testCanTokenizeComplexSequenceWithInvalidCharacters() {
    String command = "1 \t2 \n+$= \r5r d f12345678901234567890 v-656";
    List<Token> tokens = TOKENIZER.tokenize(command);
    assertEquals(16, tokens.size());
    assertEquals(IntegerToken.forValue(1), tokens.get(0));
    assertEquals(new WhitespaceToken(), tokens.get(1));
    assertEquals(IntegerToken.forValue(2), tokens.get(2));
    assertEquals(new WhitespaceToken(), tokens.get(3));
    assertEquals(new AdditionToken(), tokens.get(4));
    assertEquals(new EqualsToken(), tokens.get(5));
    assertEquals(new WhitespaceToken(), tokens.get(6));
    assertEquals(IntegerToken.forValue(5), tokens.get(7));
    assertTrue(tokens.get(8) instanceof RandomNumberToken); // could be any number
    assertEquals(new WhitespaceToken(), tokens.get(9));
    assertEquals(new DisplayStackToken(), tokens.get(10));
    assertEquals(new WhitespaceToken(), tokens.get(11));
    assertEquals(IntegerToken.forValue("12345678901234567890"), tokens.get(12));
    assertEquals(new WhitespaceToken(), tokens.get(13));
    assertEquals(new SubtractionToken(), tokens.get(14));
    assertEquals(IntegerToken.forValue(656), tokens.get(15));
  }

}