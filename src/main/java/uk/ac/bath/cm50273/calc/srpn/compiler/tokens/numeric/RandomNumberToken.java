package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric;

import uk.ac.bath.cm50273.calc.srpn.SrpnRandomNumberGenerator;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.AbstractToken;
import uk.ac.bath.cm50273.calc.srpn.execution.SizeRestrictedStack;
import uk.ac.bath.cm50273.calc.srpn.execution.StackOverflowException;
import uk.ac.bath.cm50273.calc.srpn.execution.StackUnderflowException;

public class RandomNumberToken extends AbstractToken {

  private static final SrpnRandomNumberGenerator RANDOM_NUMBER_GENERATOR =
      new SrpnRandomNumberGenerator();

  @Override
  public void apply(SizeRestrictedStack<Integer> operandStack)
      throws StackUnderflowException, StackOverflowException {
    operandStack.doIfNotFull(stack -> {
      operandStack.push(RANDOM_NUMBER_GENERATOR.nextInt());
    });
  }

  @Override
  public String getReadableValue() {
    return "random";
  }
}
