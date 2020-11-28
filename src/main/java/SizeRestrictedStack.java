import java.util.Collection;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * A stack with a maximum size as specified at initialisation. A {@link StackOverflowException} if
 * this limit is attempted to be exceeded.
 * @param <E> The element type stored in the stack.
 */
public class SizeRestrictedStack<E> extends Stack<E> {

  private final int maxSize;

  /**
   * Creates a stack with the given maximum size.
   * @param maxSize The maximum number of elements that can be stored in the stack at any point in
   * time.
   */
  public SizeRestrictedStack(int maxSize) throws IllegalArgumentException {
    if (maxSize < 1) {
      throw new IllegalArgumentException("Max size cannot be < 1. Specified size: " + maxSize);
    }
    this.maxSize = maxSize;
  }

  @Override
  public synchronized E pop() throws StackUnderflowException {
    if (size() < 1) {
      throw new StackUnderflowException();
    }
    return super.pop();
  }

  /**
   * Pushes the item onto the stack.
   * @param item The item to push onto the stack.
   * @throws StackOverflowException Thrown if the maximum size of the stack would be exceeded by
   * performing this operator. There is already a {@link StackOverflowError} type that we could have
   * used, but this exception type is thrown for a different purpose (i.e. method stack overflows).
   * It's also not a recommended practice to catch for errors, so a {@link StackOverflowException}
   * is more appropriate.
   * @see Stack#push(Object)
   */
  @Override
  public void addElement(E item) throws StackOverflowException {
    // The java.util.Stack implementation of push uses this method in the Vector class so we only
    // need to override this one.
    verifyNotAtMaximumCapacity();
    super.addElement(item);
  }

  @Override
  public boolean add(E item) {
    if (atMaximumCapacity()) {
      return false;
    }
    super.add(item);
    return true;
  }

  @Override
  public void insertElementAt(E element, int index) throws StackOverflowException {
    verifyNotAtMaximumCapacity();
    super.insertElementAt(element, index);
  }

  @Override
  public boolean addAll(Collection<? extends E> elements) {
    /* If the new size would be greater than capacity, return false and do nothing. Otherwise, add
     * the elements */
    return canAddCollection(elements) && super.addAll(elements);
  }

  @Override
  public synchronized boolean addAll(int index, Collection<? extends E> elements) {
    /* If the new size would be greater than capacity, return false and do nothing. Otherwise, add
     * the elements */
    return canAddCollection(elements) && super.addAll(index, elements);
  }

  private void verifyNotAtMaximumCapacity() throws StackOverflowException {
    if (atMaximumCapacity()) {
      throw new StackOverflowException();
    }
  }

  public boolean atMaximumCapacity() {
    return size() >= maxSize;
  }

  private boolean canAddCollection(Collection<? extends E> collection) {
    return size() + collection.size() <= maxSize;
  }

  @Override
  public synchronized void setSize(int newSize) {
    /* Any throwing of this exception implies that we're breaking Liskov's Substitution Principle.
     * However, there are several other members of the Collection library that do this, e.g.
     * Collections.SingletonList. Being able to pass this object around as though it's part of the
     * standard Java Collections library is too convenient to ignore. */
    throw new UnsupportedOperationException("Size is fixed at initialisation");
  }

  public void doIfNotFullOrThrowException(Consumer<SizeRestrictedStack<E>> action) {
    if (atMaximumCapacity()) {
      throw new StackOverflowException();
    }
    action.accept(this);
  }
}
