package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.DisplayStackToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizerTestUtils;

public class DisplayStackTokenizerTest {

  private static final Tokenizer<DisplayStackToken> TOKENIZER = new ResultHistoryTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, 'd');
  }

  @Test
  public void testBuildToken() {
    String command = "A command";
    TokenizationResult<DisplayStackToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

}