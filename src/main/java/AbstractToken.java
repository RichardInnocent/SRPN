/**
 * As the {@link #toString()} can't be overridden from the {@link Token} interface, this abstract
 * implementation of a {@link Token} overrides it instead. Overriding the {@link #toString()} method
 * in this way makes it a lot easier to assess tokens at a glance, improving the maintainability of
 * the code.
 */
public abstract class AbstractToken implements Token {

  @Override
  public String toString() {
    return getReadableValue();
  }

}
