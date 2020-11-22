package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.RandomNumberToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizerTestUtils;

public class RandomNumberTokenizerTest {

  private static final Tokenizer<RandomNumberToken> TOKENIZER = new RandomNumberTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, 'r');
  }

  @Test
  public void testBuildToken() {
    String command = "A command";
    TokenizationResult<RandomNumberToken> result = TOKENIZER.tokenize(command);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

}