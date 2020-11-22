package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.WhitespaceToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizerTestUtils;

public class WhitespaceTokenizerTest {

  private static final Tokenizer<WhitespaceToken> TOKENIZER = new WhitespaceTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, new char[] {
        '\013', // Vertical tab
        '\014', // NP form feed, new page
        '\r',
        '\034', // File separator
        '\035', // Group separator
        '\036', // Record separator
        '\037', // Unit separator
        ' ',
        '\t',
        '\n'
    });
  }

  @Test
  public void testBuildToken() {
    String command = " \t\n command";
    TokenizationResult<WhitespaceToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.trim(), result.getRemainingCommand());
  }

}