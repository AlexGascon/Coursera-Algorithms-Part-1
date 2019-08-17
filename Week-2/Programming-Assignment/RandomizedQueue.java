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

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return numElements;
    }

    public void enqueue(Item item) {
        if (size() == arraySize) resize(2 * arraySize);

        elements[size()] = item;
        numElements++;
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is empty");
        }

        // Retrieving the item to return
        int index = randomIndex();
        Item item = elements[index];

        // Moving the last element to the position that will get empty
        elements[index] = elements[size()-1];
        elements[size()-1] = null;
        numElements--;

        if (size() == arraySize / 4) resize(arraySize / 2);

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is empty");
        }

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

    private validateItem(Item item) {
        if (item == null) throw new IllegalArgumentException("The item can't be null");
    }

    private void resize(int capacity) {
        if (isEmpty()) { return; }

        this.arraySize = capacity;
        Item[] copy = (Item[]) new Object[this.arraySize];

        for (int i = 0; i < size(); i++) {
            copy[i] = elements[i];
        }

        this.elements = copy;
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