import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int arraySize;
    private Item[] elements;
    private int numElements;

    public RandomizedQueue() {
        this.arraySize = 1;
        this.elements = (Item[]) new Object[arraySize];
        this.numElements = 0;
    }

    /**
     * Returns true if there aren't any elements in the queue
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Number of elements in the queue
     *
     * @return integer - Number of elements currently in the queue
     */
    public int size() {
        return numElements;
    }

    /**
     * Puts an item in the queue
     *
     * @param item Element to add to the queue
     */
    public void enqueue(Item item) {
        validateItem(item);

        if (isFull()) duplicateSize();

        elements[size()] = item;
        numElements++;
    }

    /**
     * Removes a random element from the queue and returns it
     *
     * @return Item Element extracted from the queue
     */
    public Item dequeue() {
        validateNotEmpty();

        Item itemToReturn = removeRandomItemAndReorder();

        if (isAlmostEmpty()) halfSize();

        return itemToReturn;
    }

    /**
     * Returns a random element from the queue without removing it
     *
     * @return Item - Random element from the queue
     */
    public Item sample() {
        validateNotEmpty();

        return elements[randomIndex()];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        // Empty method
    }

    private int randomIndex() {
        return StdRandom.uniform(size());
    }

    private void validateItem(Item item) {
        if (item == null) throw new IllegalArgumentException("The item can't be null");
    }

    private void validateNotEmpty() {
        if (isEmpty()) throw new java.util.NoSuchElementException("The queue is empty");
    }

    private Item removeRandomItemAndReorder() {
        int index = randomIndex();
        int lastIndex = size() - 1;

        Item item = elements[index];
        elements[index] = elements[lastIndex];
        elements[lastIndex] = null;

        numElements--;

        return item;
    }

    private boolean isFull() {
        return size() == arraySize;
    }

    private boolean isAlmostEmpty() {
        return size() <= arraySize / 4;
    }

    private void duplicateSize() {
        resize(2 * arraySize);
    }

    private void halfSize() {
        resize(arraySize / 2);
    }

    private void resize(int capacity) {
        if (isEmpty()) { return; }

        arraySize = capacity;
        Item[] copy = (Item[]) new Object[arraySize];

        int maxSize = size();
        for (int i = 0; i < maxSize; i++) copy[i] = elements[i];

        elements = copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final RandomizedQueue<Item> copy;

        RandomizedQueueIterator() {
            copy = new RandomizedQueue<Item>();

            for (int i = 0; i < size(); i++) {
                copy.enqueue(elements[i]);
            }
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("There are no more elements");
            
            return copy.dequeue();
        }

        public boolean hasNext() {
            return copy.size() > 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported by this class");
        }
    }
}