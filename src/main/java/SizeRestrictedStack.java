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

  /**
   * Pops the top item off of the stack and returns this item.
   * @return The item that was popped off of the stack.
   * @throws StackUnderflowException Thrown if the stack is empty.
   */
  @Override
  public synchronized E pop() throws StackUnderflowException {
    if (isEmpty()) {
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

  /**
   * Pushes the item onto the stack if the stack is not at its maximum capacity.
   * @param item The item to push onto the stack.
   * @return {@code true} if the item was pushed onto the stack, or {@code false} if this could not
   * be done as the stack is at its maximum capacity.
   */
  @Override
  public boolean add(E item) {
    if (atMaximumCapacity()) {
      return false;
    }
    super.add(item);
    return true;
  }

  /**
   * Inserts the element at the given index.
   * @param element The element to add.
   * @param index The index to insert the index.
   * @throws StackOverflowException Thrown if the stack is currently at its maximum capacity.
   */
  @Override
  public void insertElementAt(E element, int index) throws StackOverflowException {
    verifyNotAtMaximumCapacity();
    super.insertElementAt(element, index);
  }

  /**
   * Pushes all elements in the collection onto the stack. If this is not possible as the maximum
   * stack size would be exceeded, this operation has no effect.
   * @param elements The elements to push onto the stack.
   * @return {@code true} if the elements were successfully pushed onto the stack.
   */
  @Override
  public boolean addAll(Collection<? extends E> elements) {
    return canAddCollection(elements) && super.addAll(elements);
  }

  /**
   * Adds all elements in the collection into the stack at the given index. If this is not possible
   * as the maximum stack size would be exceeded, this operation has no effect.
   * @param index The index to at which to insert the elements.
   * @param elements The elements to add.
   * @return {@code true} if the elements were successfully added.
   */
  @Override
  public synchronized boolean addAll(int index, Collection<? extends E> elements) {
    return canAddCollection(elements) && super.addAll(index, elements);
  }

  /**
   * @throws StackOverflowException If this stack is at its maximum capacity.
   */
  private void verifyNotAtMaximumCapacity() throws StackOverflowException {
    if (atMaximumCapacity()) {
      throw new StackOverflowException();
    }
  }

  /**
   * Determines whether the stack is at its maximum capacity.
   * @return {@code true} if the stack is at its maximum capacity.
   */
  public boolean atMaximumCapacity() {
    return size() >= maxSize;
  }

  /**
   * If the stack is not full, the given {@code action} is performed. If the stack is full, a
   * {@link StackOverflowException} is thrown.
   * @param action The action to perform if the stack is not full.
   */
  public void doIfNotFullOrThrowException(Consumer<SizeRestrictedStack<E>> action)
      throws StackOverflowException {
    if (atMaximumCapacity()) {
      throw new StackOverflowException();
    }
    action.accept(this);
  }

  /**
   * Determines if the stack can successfully push all elements of the other collection without
   * exceeding its maximum capacity.
   * @param collection The other collection to add.
   * @return {@code true} if all elements of {@code collection} can be pushed onto {@code this}
   * stack without exceeding the maximum capacity.
   */
  private boolean canAddCollection(Collection<? extends E> collection) {
    return size() + collection.size() <= maxSize;
  }

  /**
   * This operation has no effect on this collection type.
   * @param newSize The new size.
   * @throws UnsupportedOperationException Thrown for all collections of this type. A
   * {@code SizeRestrictedStack} has a fixed size.
   */
  @Override
  public synchronized void setSize(int newSize) throws UnsupportedOperationException {
    /* Any throwing of this exception implies that we're breaking Liskov's Substitution Principle.
     * However, there are several other members of the Collection library that do this, e.g.
     * Collections.SingletonList. Being able to pass this object around as though it's part of the
     * standard Java Collections library is too convenient to ignore. */
    throw new UnsupportedOperationException("Size is fixed at initialisation");
  }
}
