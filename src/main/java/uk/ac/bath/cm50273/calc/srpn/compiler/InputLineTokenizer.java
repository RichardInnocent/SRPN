package uk.ac.bath.cm50273.calc.srpn.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationResult;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.Tokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc.EqualsTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc.ResultHistoryTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc.WhitespaceTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.IntegerTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.RandomNumberTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.operator.MathOperatorTokenizer;

public class InputLineTokenizer {

  private final List<Tokenizer<?>> tokenizers;
  private boolean parsingCommand = false;

  public InputLineTokenizer() {
    this.tokenizers = Arrays.asList(
        new IntegerTokenizer(),
        new MathOperatorTokenizer(),
        new RandomNumberTokenizer(),
        new ResultHistoryTokenizer(),
        new WhitespaceTokenizer(),
        new EqualsTokenizer()
    );
  }

  public List<Token> tokenize(String command) {
    if (command == null || command.isBlank()) {
      // Nothing to process
      return Collections.emptyList();
    }

    List<Token> tokens = new ArrayList<>();

    String remainingCommand = command;

    while (!remainingCommand.isEmpty()) {
      char nextChar = remainingCommand.charAt(0);
      if (nextChar == '#') {
        parsingCommand = !parsingCommand;
        remainingCommand = remainingCommand.substring(1);
      } else {
        if (parsingCommand) {
          remainingCommand = remainingCommand.substring(1);
          continue;
        }
        Optional<TokenizationResult<?>> tokenizationResult = getNextToken(remainingCommand);
        if (tokenizationResult.isPresent()) {
          tokens.add(tokenizationResult.get().getToken());
          remainingCommand = tokenizationResult.get().getRemainingCommand();
        } else {
          remainingCommand = remainingCommand.substring(1);
        }
      }
    }

    return tokens;
  }

  private Optional<TokenizationResult<?>> getNextToken(String remainingCommand) {
    Optional<Tokenizer<?>> tokenizer = getTokenizerForNextElement(remainingCommand);
    if (tokenizer.isPresent()) {
      return Optional.of(tokenizer.get().tokenize(remainingCommand));
    } else {
      // If a character couldn't be tokenized, it must be invalid.
      // Display a warning and ignore the character
      System.out.printf("Unrecognised operator or operand \"%c\"\n", remainingCommand.charAt(0));
      return Optional.empty();
    }
  }

  private Optional<Tokenizer<?>> getTokenizerForNextElement(String remainingCommand) {
    char nextCharacter = remainingCommand.charAt(0);
    return tokenizers.stream()
                     .filter(tokenizer -> tokenizer.canTokenize(nextCharacter))
                     .findAny();
  }

}
