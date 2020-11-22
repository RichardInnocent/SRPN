package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizerTestUtils;

public class IntegerTokenizerTest {

  private static final Tokenizer<IntegerToken> TOKENIZER = new IntegerTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, "1234567890");
  }

  @Test
  public void testBuildToken() {
    String command = "1234+56789";
    TokenizationResult<IntegerToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.replace("1234", ""), result.getRemainingCommand());
  }

  @Test
  public void testBuildTokenWithOverflowingNumber() {
    String value = "12345678901234567890";
    TokenizationResult<IntegerToken> result = TOKENIZER.tokenize(value);
    assertEquals(IntegerToken.forValue(value), result.getToken());
    assertTrue(result.getRemainingCommand().isEmpty());
  }

}