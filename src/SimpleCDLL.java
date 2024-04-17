import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Simple doubly-linked lists, with the dummy node between end and start and
 * two counters in list and iterator first so that all the iterators will die
 * out except the lastest-updated one whenever the iterator is caleled.
 * 
 * @author Samuel Rebelsky
 * @author Vivien Yan
 */

public class SimpleCDLL<T> implements SimpleList<T> {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The front of the list
   */
  Node2<T> dummy;

  /**
   * The number of values in the list.
   */
  int size = 0;

  /*
   * The number of change in the list
   */

  int counter = 0;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this.dummy = new Node2<T>(null);
    this.dummy.next = null;
    this.dummy.prev = null;
    this.size = 0;
  } // SimpleDLL

  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator() {
    return listIterator();
  } // iterator()

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {
      // +--------+--------------------------------------------------------
      // | Fields |
      // +--------+

      /**
       * The position in the list of the next value to be returned.
       * Included because ListIterators must provide nextIndex and
       * prevIndex.
       */
      int pos = 0;

      int numchange = SimpleCDLL.this.counter;
      /**
       * The cursor is between neighboring values, so we start links
       * to the previous and next value..
       */
      Node2<T> prev = SimpleCDLL.this.dummy.prev;
      Node2<T> next = SimpleCDLL.this.dummy.next;

      /**
       * The node to be updated by remove or set. Has a value of
       * null when there is no such value.
       */
      Node2<T> update = null;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      public void add(T val) throws UnsupportedOperationException {
        // Special case: The list is empty)
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        if (SimpleCDLL.this.dummy.next == null) {
          Node2<T> newnode = new Node2<T>(val);
          newnode.next = SimpleCDLL.this.dummy;
          newnode.prev = SimpleCDLL.this.dummy;
          SimpleCDLL.this.dummy.next = newnode;
          SimpleCDLL.this.dummy.prev = newnode;

          this.prev = newnode;
          this.next = newnode;
        } // empty list
        // Normal case
        else {
          this.prev = this.prev.insertAfter(val);
        } // normal case

        // Note that we cannot update
        this.update = null;

        // Increase the size
        ++SimpleCDLL.this.size;

        // Update the position. (See SimpleArrayList.java for more of
        // an explanation.)
        ++this.pos;

        ++numchange;
        ++counter;
      } // add(T)

      public boolean hasNext() {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public boolean hasPrevious() {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        return (this.pos > 0);
      } // hasPrevious()

      public T next() {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        if (!this.hasNext()) {
          throw new NoSuchElementException();
        } // if
        // Identify the node to update
        this.update = this.next;
        // Advance the cursor
        this.prev = this.next;
        this.next = this.next.next;
        // Note the movement
        ++this.pos;
        // And return the value
        return this.update.value;
      } // next()

      public int nextIndex() {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        return this.pos;
      } // nextIndex()

      public int previousIndex() {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        return this.pos - 1;
      } // prevIndex

      public T previous() throws NoSuchElementException {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        if (!this.hasPrevious())
          throw new NoSuchElementException();
        else {
          return this.update.prev.value;
        }
      } // previous()

      public void remove() {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        // Sanity check
        if (this.update == null) {
          throw new IllegalStateException();
        } // if

        // Update the cursor
        if (this.next == this.update) {
          this.next = this.update.next;
        } // if
        if (this.prev == this.update) {
          this.prev = this.update.prev;
          --this.pos;
        } // if

        // Update the front
        if (SimpleCDLL.this.dummy.next == this.update) {
          SimpleCDLL.this.dummy.next = this.update.next;
        } // if

        // Do the real work
        this.update.remove();
        --SimpleCDLL.this.size;
        --this.numchange;
        --SimpleCDLL.this.counter;

        // Note that no more updates are possible
        this.update = null;
      } // remove()

      public void set(T val) {
        if (this.numchange != SimpleCDLL.this.counter) {
          throw new ConcurrentModificationException();
        }
        // Sanity check
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // Do the real work
        this.update.value = val;
        // Note that no more updates are possible
        this.update = null;
      } // set(T)
    };
  } // listIterator()

} // class SimpleDLL<T>
