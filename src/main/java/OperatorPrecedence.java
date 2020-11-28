/**
 * <p>Specifies the precedence of the operators. The values could have been implemented directly in
 * the {@link Operator#getPrecedence()} definitions, but it's clearer to see how they compare by
 * extracting these precedences out into a separate class.</p>
 * <p>The operators follow the BODMAS standard.</p>
 */
public class OperatorPrecedence {

  /**
   * The precedence for modulo operations.
   */
  public static int MODULO = 60;

  /**
   * The precedence for raise to power operations.
   */
  public static int RAISE_TO_POWER = 50;

  /**
   * The precedence for division operations.
   */
  public static int DIVISION = 40;

  /**
   * The precedence for multiplication operations.
   */
  public static int MULTIPLICATION = 30;

  /**
   * The precedence for addition operations.
   */
  public static int ADDITION = 20;

  /**
   * The precedence for subtraction operations.
   */
  public static int SUBTRACTION = 10;

}
