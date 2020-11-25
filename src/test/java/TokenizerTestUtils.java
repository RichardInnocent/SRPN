import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TokenizerTestUtils {

  private TokenizerTestUtils() {}

  public static Stream<Character> streamFirst256Characters() {
    return IntStream.range(0, 256)
                    .mapToObj(i -> (char) i);
  }

  public static Stream<Character> streamFirst256CharactersExcept(char exclusion) {
    return streamFirst256Characters()
        .filter(character -> character != exclusion);
  }

  public static Stream<Character> streamFirst256CharactersExcept(char[] exclusions) {
    return streamFirst256Characters()
        .filter(character -> {
          for (char exclusion : exclusions) {
            if (character == exclusion) {
              return false;
            }
          }
          return true;
        });
  }

  public static Stream<Character> streamFirst256CharactersExcept(String exclusionsAsString) {
    return streamFirst256CharactersExcept(exclusionsAsString.toCharArray());
  }

  public static void assertTokenizerCanOnlyTokenize(Tokenizer<?> tokenizer, char character) {
    streamFirst256Characters()
        .forEach(c -> {
          if (c == character) {
            assertTrue("Tokenizer should be able to tokenize " + c, tokenizer.canTokenize(c));
          } else {
            assertFalse("Tokenizer should not be able to tokenize " + c, tokenizer.canTokenize(c));
          }
        });
  }

  public static void assertTokenizerCanOnlyTokenize(Tokenizer<?> tokenizer, char[] characters) {
    streamFirst256Characters()
        .forEach(c -> {
          if (arrayContains(characters, c)) {
            assertTrue(
                String.format("Tokenizer should be able to tokenize %c (char %d)", c, (int) c),
                tokenizer.canTokenize(c)
            );
          } else {
            assertFalse(
                String.format("Tokenizer should not be able to tokenize %c (char %d)", c, (int) c),
                tokenizer.canTokenize(c)
            );
          }
        });
  }

  public static void assertTokenizerCanOnlyTokenize(Tokenizer<?> tokenizer, String characters) {
    assertTokenizerCanOnlyTokenize(tokenizer, characters.toCharArray());
  }

  private static boolean arrayContains(char[] characters, char search) {
    for (char c : characters) {
      if (c == search) {
        return true;
      }
    }
    return false;
  }

}
