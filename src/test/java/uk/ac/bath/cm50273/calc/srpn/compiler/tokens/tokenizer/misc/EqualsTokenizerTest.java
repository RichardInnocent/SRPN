package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.AdditionOperatorTest;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.EqualsToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.WhitespaceToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizerTestUtils;

public class EqualsTokenizerTest {

  private static final Tokenizer<EqualsToken> TOKENIZER = new EqualsTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, '=');
  }

  @Test
  public void testBuildToken() {
    String command = "A command";
    TokenizationResult<EqualsToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testHashCode() {
    assertEquals("equals".hashCode(), new EqualsToken().hashCode());
  }

  @Test
  public void testEquals() {
    assertEquals(new EqualsToken(), new EqualsToken());
    assertFalse(new EqualsToken().equals(new WhitespaceToken()));
  }

}