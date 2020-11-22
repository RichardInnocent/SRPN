package uk.ac.bath.cm50273.calc.srpn.compiler;

import java.util.ArrayList;
import java.util.List;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.TokenGroup;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.WhitespaceToken;

public class TokenGrouper {

  public List<TokenGroup> group(List<Token> tokens) {
    List<TokenGroup> groups = new ArrayList<>();

    TokenGroup currentGroup = new TokenGroup();
    for (Token token : tokens) {
      if (token instanceof WhitespaceToken) {
        // End of group

        if (!currentGroup.isEmpty()) {
          // Group is not empty - add the group to the groups list
          groups.add(currentGroup);
          // Start a new group
          currentGroup = new TokenGroup();
        }

        // No need to do anything if the group is empty anyway
      } else {
        currentGroup.addAndTidy(token);
      }
    }

    // Add the remaining group, but only if it actually contains some tokens
    if (!currentGroup.isEmpty()) {
      groups.add(currentGroup);
    }

    return groups;
  }

}
