package uk.ac.bath.cm50273.calc.srpn.execution;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackOverflowExceptionTest {

  @Test
  public void testConstructor() {
    String expectedMessage = "Stack overflow.";
    assertEquals(expectedMessage, new StackOverflowException().getMessage());
  }

}