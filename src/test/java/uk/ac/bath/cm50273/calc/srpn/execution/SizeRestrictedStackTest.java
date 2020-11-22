package uk.ac.bath.cm50273.calc.srpn.execution;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import org.junit.Test;

public class SizeRestrictedStackTest {

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionThrownIfStackSizeIs0() {
    new SizeRestrictedStack<>(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionThrownIfStackSizeIsNegative() {
    new SizeRestrictedStack<>(-1);
  }

  @Test
  public void testPush() {
    Stack<Integer> stack = new SizeRestrictedStack<>(3);
    assertEquals(1, stack.push(1).intValue());
    assertEquals(2, stack.push(2).intValue());
    assertEquals(3, stack.push(3).intValue());

    verifyStackOverflowExceptionThrownWhen(() -> stack.push(4));

    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(stack));
  }

  @Test
  public void testAddItems() {
    Stack<Integer> stack = new SizeRestrictedStack<>(3);
    stack.addElement(1);
    stack.addElement(2);
    stack.addElement(3);

    verifyStackOverflowExceptionThrownWhen(() -> stack.addElement(4));

    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(stack));
  }

  @Test
  public void testAdd() {
    Stack<Integer> stack = new SizeRestrictedStack<>(3);
    assertTrue(stack.add(1));
    assertTrue(stack.add(2));
    assertTrue(stack.add(3));
    assertFalse(stack.add(4));

    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(stack));
  }

  @Test
  public void testInsertElementAt() {
    Stack<Integer> stack = new SizeRestrictedStack<>(3);
    stack.insertElementAt(1, 0);
    stack.insertElementAt(2, 0);
    stack.insertElementAt(3, 0);

    verifyStackOverflowExceptionThrownWhen(() -> stack.insertElementAt(4, 0));
    assertEquals(Arrays.asList(3, 2, 1), new ArrayList<>(stack));
  }

  @Test
  public void testAddAtIndex() {
    Stack<Integer> stack = new SizeRestrictedStack<>(3);
    stack.add(0, 1);
    stack.add(0, 2);
    stack.add(0, 3);

    verifyStackOverflowExceptionThrownWhen(() -> stack.add(0, 4));

    assertEquals(Arrays.asList(3, 2, 1), new ArrayList<>(stack));
  }

  @Test
  public void testAddAll() {
    Stack<Integer> stack = new SizeRestrictedStack<>(3);
    stack.addAll(Arrays.asList(1, 2));
    assertFalse(stack.addAll(Arrays.asList(3, 4)));
    assertEquals(Arrays.asList(1, 2), new ArrayList<>(stack));

    assertTrue(stack.addAll(Collections.singletonList(3)));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(stack));
  }

  @Test
  public void testAddAllAtIndex() {
    Stack<Integer> stack = new SizeRestrictedStack<>(5);
    stack.addAll(Arrays.asList(1, 2, 3));
    assertFalse(stack.addAll(2, Arrays.asList(4, 5, 6)));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(stack));

    assertTrue(stack.addAll(1, Arrays.asList(4, 5)));
    assertEquals(Arrays.asList(1, 4, 5, 2, 3), new ArrayList<>(stack));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSetSizeIsNotSupported() {
    new SizeRestrictedStack<>(23).setSize(20);
  }

  private void verifyStackOverflowExceptionThrownWhen(Runnable statement) {
    try {
      statement.run();
      fail("No exception thrown");
    } catch (StackOverflowException e) {
      // Satisfied that an exception has been thrown
    }
  }

}