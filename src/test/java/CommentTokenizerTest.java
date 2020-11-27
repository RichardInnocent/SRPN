import static org.junit.Assert.*;

import org.junit.Test;

public class CommentTokenizerTest {

  private final CommentTokenizer tokenizer = new CommentTokenizer();

  @Test
  public void testCannotTokenizeWhenNoComment() {
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder("Not a comment");
    assertFalse(tokenizer.attemptTokenization(resultBuilder).succeeded());
    assertEquals(0, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testCannotTokenizeWhenHashNotPrecededByWhitespace() {
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder("a# No preceding whitespace here");
    resultBuilder.setCurrentIndex(1);
    assertFalse(tokenizer.attemptTokenizationAndThrowExceptions(resultBuilder).succeeded());
    assertEquals(1, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testCannotTokenizeWhenHashNotProceededByWhitespace() {
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder("#No whitespace here");
    assertFalse(tokenizer.attemptTokenizationAndThrowExceptions(resultBuilder).succeeded());
    assertEquals(0, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testFullLineComment() {
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder("# this is a complete comment #");
    assertTrue(tokenizer.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.isComplete());
    assertTrue(resultBuilder.getTokens().isEmpty());

    // Comment should have ended so check that we fail to tokenize anything else
    TokenizationResultBuilder noComment =
        new TokenizationResultBuilder("This does not contain a comment");
    assertFalse(tokenizer.attemptTokenization(noComment).succeeded());
  }

  @Test
  public void testMidLineComment() {
    String command = "This is a # mid line # comment";
    TokenizationResultBuilder resultBuilder =
        new TokenizationResultBuilder(command);
    resultBuilder.setCurrentIndex(command.indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.getTokens().isEmpty());
    assertEquals(command.indexOf("comment"), resultBuilder.getCurrentIndex());
  }

  @Test
  public void testCommentOverMultipleLinesWhereCommentIsOnlyCharacter() {
    TokenizationResultBuilder line1 = new TokenizationResultBuilder("#");
    assertTrue(tokenizer.attemptTokenization(line1).succeeded());
    assertTrue(line1.isComplete());
    assertTrue(line1.getTokens().isEmpty());

    TokenizationResultBuilder line2 = new TokenizationResultBuilder("This is a comment");
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertTrue(line2.isComplete());
    assertTrue(line2.getTokens().isEmpty());

    TokenizationResultBuilder line3 = new TokenizationResultBuilder("#");
    assertTrue(tokenizer.attemptTokenization(line3).succeeded());
    assertTrue(line3.isComplete());
    assertTrue(line3.getTokens().isEmpty());

    TokenizationResultBuilder line4 = new TokenizationResultBuilder("This is not a comment");
    assertFalse(tokenizer.attemptTokenization(line4).succeeded());
    assertEquals(0, line4.getCurrentIndex());
  }

  @Test
  public void testStartingAndCompletingCommentAtStartOfLine() {
    TokenizationResultBuilder line1 = new TokenizationResultBuilder("# Some comment");
    assertTrue(tokenizer.attemptTokenization(line1).succeeded());
    assertTrue(line1.isComplete());
    assertTrue(line1.getTokens().isEmpty());

    TokenizationResultBuilder line2 = new TokenizationResultBuilder("# This is not a comment");
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertFalse(line2.isComplete());
    assertEquals(2, line2.getCurrentIndex());
  }

  @Test
  public void testStartingAndCompletingCommentAtStartOfLineMustHaveTrailingWhitespace() {
    TokenizationResultBuilder line1 = new TokenizationResultBuilder("#Some command");
    assertFalse(tokenizer.attemptTokenization(line1).succeeded());
    assertEquals(0, line1.getCurrentIndex());

    TokenizationResultBuilder line2 = new TokenizationResultBuilder("# Some comment");
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertTrue(line2.isComplete());
    assertTrue(line2.getTokens().isEmpty());

    TokenizationResultBuilder line3 = new TokenizationResultBuilder("#Some command");
    assertTrue(tokenizer.attemptTokenization(line3).succeeded());
    assertTrue(line3.isComplete());
    assertTrue(line3.getTokens().isEmpty());

    TokenizationResultBuilder line4 = new TokenizationResultBuilder("# This is not a comment");
    assertTrue(tokenizer.attemptTokenization(line4).succeeded());
    assertFalse(line4.isComplete());
    assertEquals(2, line4.getCurrentIndex());
  }

  @Test
  public void testStartingAndCompletingCommentAtEndOfLine() {
    TokenizationResultBuilder line1 = new TokenizationResultBuilder("Some command #");
    line1.setCurrentIndex(line1.getOriginalCommand().indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(line1).succeeded());
    assertTrue(line1.isComplete());
    assertTrue(line1.getTokens().isEmpty());

    TokenizationResultBuilder line2 = new TokenizationResultBuilder("This is a comment #");
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertTrue(line2.isComplete());
    assertTrue(line2.getTokens().isEmpty());

    TokenizationResultBuilder line3 = new TokenizationResultBuilder("This is not a comment");
    assertFalse(tokenizer.attemptTokenization(line3).succeeded());
    assertEquals(0, line3.getCurrentIndex());
  }

  @Test
  public void testStartingAndCompletingCommentAtEndOfLineMustHaveLeadingWhitespace() {
    TokenizationResultBuilder line1 = new TokenizationResultBuilder("Some command#");
    line1.setCurrentIndex(line1.getOriginalCommand().indexOf('#'));
    assertFalse(tokenizer.attemptTokenization(line1).succeeded());
    assertEquals(line1.getOriginalCommand().indexOf('#'), line1.getCurrentIndex());

    TokenizationResultBuilder line2 = new TokenizationResultBuilder("Some command #");
    line2.setCurrentIndex(line2.getOriginalCommand().indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertTrue(line2.isComplete());
    assertTrue(line2.getTokens().isEmpty());

    TokenizationResultBuilder line3 = new TokenizationResultBuilder("This is a comment#");
    assertTrue(tokenizer.attemptTokenization(line3).succeeded());
    assertTrue(line3.isComplete());

    TokenizationResultBuilder line4 = new TokenizationResultBuilder("This is a comment #");
    assertTrue(tokenizer.attemptTokenization(line4).succeeded());
    assertTrue(line4.isComplete());

    TokenizationResultBuilder line5 = new TokenizationResultBuilder("Some command");
    assertFalse(tokenizer.attemptTokenization(line5).succeeded());
    assertEquals(0, line5.getCurrentIndex());
  }

  @Test
  public void testMidlineCommentsMustHaveLeadingWhitespace() {
    TokenizationResultBuilder line1 =
        new TokenizationResultBuilder("Some command# This is not a comment# Also not a comment");
    line1.setCurrentIndex(line1.getOriginalCommand().indexOf('#'));
    assertFalse(tokenizer.attemptTokenization(line1).succeeded());
    assertEquals(line1.getOriginalCommand().indexOf('#'), line1.getCurrentIndex());

    TokenizationResultBuilder line2 =
        new TokenizationResultBuilder("Some command # This is a comment# Also a comment");
    line2.setCurrentIndex(line2.getOriginalCommand().indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertTrue(line2.isComplete());
    assertTrue(line2.getTokens().isEmpty());

    // Stop processing everything as a comment, ready for next test
    tokenizer.attemptTokenization(new TokenizationResultBuilder("#"));

    TokenizationResultBuilder line3 =
        new TokenizationResultBuilder("Some command # This is a comment # Not a comment");
    line3.setCurrentIndex(line3.getOriginalCommand().indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(line3).succeeded());
    assertEquals(line3.getOriginalCommand().indexOf("Not a comment"), line3.getCurrentIndex());
    assertTrue(line3.getTokens().isEmpty());
  }

  @Test
  public void testMidlineCommentsMustHaveTrailingWhitespace() {
    TokenizationResultBuilder line1 =
        new TokenizationResultBuilder("Some command #This is not a comment #Also not a comment");
    line1.setCurrentIndex(line1.getOriginalCommand().indexOf('#'));
    assertFalse(tokenizer.attemptTokenization(line1).succeeded());
    assertEquals(line1.getOriginalCommand().indexOf('#'), line1.getCurrentIndex());

    TokenizationResultBuilder line2 =
        new TokenizationResultBuilder("Some command # This is a comment #Also a comment");
    line2.setCurrentIndex(line2.getOriginalCommand().indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(line2).succeeded());
    assertTrue(line2.isComplete());
    assertTrue(line2.getTokens().isEmpty());

    // Stop processing everything as a comment, ready for next test
    tokenizer.attemptTokenization(new TokenizationResultBuilder("#"));

    TokenizationResultBuilder line3 =
        new TokenizationResultBuilder("Some command # This is a comment # Not a comment");
    line3.setCurrentIndex(line3.getOriginalCommand().indexOf('#'));
    assertTrue(tokenizer.attemptTokenization(line3).succeeded());
    assertEquals(line3.getOriginalCommand().indexOf("Not a comment"), line3.getCurrentIndex());
    assertTrue(line3.getTokens().isEmpty());
  }

}