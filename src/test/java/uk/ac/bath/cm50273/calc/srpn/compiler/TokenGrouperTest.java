package uk.ac.bath.cm50273.calc.srpn.compiler;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.TokenGroup;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.WhitespaceToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.AdditionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.ModuloToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.MultiplicationToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.SubtractionToken;

public class TokenGrouperTest {

  private static final TokenGrouper GROUPER = new TokenGrouper();

  @Test
  public void testGroupReturnsEmptyListIfNoTokensExist() {
    assertTrue(GROUPER.group(Collections.emptyList()).isEmpty());
  }

  @Test
  public void testGroupReturnsEmptyListIfTokensAreOnlyWhitespace() {
    List<Token> tokens =
        Arrays.asList(new WhitespaceToken(), new WhitespaceToken(), new WhitespaceToken());
    assertTrue(GROUPER.group(tokens).isEmpty());
  }

  @Test
  public void testGroupingSingleToken() {
    Token token = IntegerToken.forValue(3);
    List<TokenGroup> groups = GROUPER.group(Collections.singletonList(token));
    assertEquals(1, groups.size());
    TokenGroup group = groups.get(0);
    assertEquals(1, group.size());
    assertEquals(token, group.getToken(0));
  }

  @Test
  public void testGroupingMultipleTokensWithNoWhitespace() {
    Token token1 = IntegerToken.forValue(3);
    Token token2 = new AdditionToken();
    Token token3 = IntegerToken.forValue(5);

    List<TokenGroup> groups = GROUPER.group(Arrays.asList(token1, token2, token3));
    assertEquals(1, groups.size());
    TokenGroup group = groups.get(0);
    assertEquals(3, group.size());
    assertEquals(token1, group.getToken(0));
    assertEquals(token2, group.getToken(1));
    assertEquals(token3, group.getToken(2));
  }

  @Test
  public void testGroupingMultipleTokensEndingWithWhitespace() {
    Token token1 = IntegerToken.forValue(3);
    Token token2 = new AdditionToken();
    Token token3 = IntegerToken.forValue(5);
    Token whitespaceToken = new WhitespaceToken();

    List<TokenGroup> groups = GROUPER.group(Arrays.asList(token1, token2, token3, whitespaceToken));
    assertEquals(1, groups.size());
    TokenGroup group = groups.get(0);
    assertEquals(3, group.size());
    assertEquals(token1, group.getToken(0));
    assertEquals(token2, group.getToken(1));
    assertEquals(token3, group.getToken(2));
  }

  @Test
  public void testGroupingMultipleTokensInterspersedWithWhitespace() {
    Token whitespaceToken = new WhitespaceToken();

    Token group1Token1 = IntegerToken.forValue(3);
    Token group1Token2 = new AdditionToken();
    Token group1Token3 = IntegerToken.forValue(5);
    Token group2Token1 = new AdditionToken();
    Token group3Token1 = new SubtractionToken();
    Token group3Token2 = IntegerToken.forValue(5);
    Token group3Token3 = new ModuloToken();
    Token group4Token1 = new MultiplicationToken();
    Token group4Token2 = IntegerToken.forValue(7);

    List<Token> tokens = Arrays.asList(
        group1Token1,
        group1Token2,
        group1Token3,
        whitespaceToken,
        group2Token1,
        whitespaceToken,
        group3Token1,
        group3Token2,
        group3Token3,
        whitespaceToken,
        group4Token1,
        group4Token2
    );

    List<TokenGroup> groups = GROUPER.group(tokens);
    assertEquals(4, groups.size());

    TokenGroup group1 = groups.get(0);
    TokenGroup group2 = groups.get(1);
    TokenGroup group3 = groups.get(2);
    TokenGroup group4 = groups.get(3);

    assertEquals(3, group1.size());
    assertEquals(group1Token1, group1.getToken(0));
    assertEquals(group1Token2, group1.getToken(1));
    assertEquals(group1Token3, group1.getToken(2));

    assertEquals(1, group2.size());
    assertEquals(group2Token1, group2.getToken(0));

    assertEquals(2, group3.size());
    assertEquals(IntegerToken.forValue(-5), group3.getToken(0));
    assertEquals(group3Token3, group3.getToken(1));

    assertEquals(2, group4.size());
    assertEquals(group4Token1, group4.getToken(0));
    assertEquals(group4Token2, group4.getToken(1));
  }

}