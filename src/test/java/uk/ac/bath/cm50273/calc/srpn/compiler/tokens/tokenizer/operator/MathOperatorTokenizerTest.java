package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.operator;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.AdditionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.DivisionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.ModuloToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.MultiplicationToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.OperatorToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.RaiseToPowerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.SubtractionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizerTestUtils;

public class MathOperatorTokenizerTest {

  private static final Tokenizer<OperatorToken> TOKENIZER = new MathOperatorTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, "+-/*%^");
  }

  @Test
  public void testBuildAdditionToken() {
    String command = "+ command";
    TokenizationResult<OperatorToken> result = TOKENIZER.tokenize(command);
    Token token = result.getToken();
    assertTrue(token instanceof AdditionToken);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testBuildSubtractionToken() {
    String command = "- command";
    TokenizationResult<OperatorToken> result = TOKENIZER.tokenize(command);
    Token token = result.getToken();
    assertTrue(token instanceof SubtractionToken);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testBuildDivisionToken() {
    String command = "/ command";
    TokenizationResult<OperatorToken> result = TOKENIZER.tokenize(command);
    Token token = result.getToken();
    assertTrue(token instanceof DivisionToken);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testBuildMultiplicationToken() {
    String command = "* command";
    TokenizationResult<OperatorToken> result = TOKENIZER.tokenize(command);
    Token token = result.getToken();
    assertTrue(token instanceof MultiplicationToken);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testBuildModuloToken() {
    String command = "% command";
    TokenizationResult<OperatorToken> result = TOKENIZER.tokenize(command);
    Token token = result.getToken();
    assertTrue(token instanceof ModuloToken);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testBuildRaiseToPowerToken() {
    String command = "^ command";
    TokenizationResult<OperatorToken> result = TOKENIZER.tokenize(command);
    Token token = result.getToken();
    assertTrue(token instanceof RaiseToPowerToken);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

}