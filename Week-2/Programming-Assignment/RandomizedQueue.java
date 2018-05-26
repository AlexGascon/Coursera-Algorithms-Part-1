import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int array_size;
    private Item[] elements;
    private int num_elements;

    public RandomizedQueue() {
        this.array_size = 1;
        this.elements = (Item[]) new Object[array_size];
        this.num_elements = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return num_elements;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new java.lang.IllegalArgumentException("The item can't be null");
        }

        if (size() == array_size) resize(2 * array_size);

        elements[size()] = item;
        num_elements++;
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
        num_elements--;

        if (size() == array_size / 4) resize(array_size / 2);

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

    private void resize(int capacity) {
        if (isEmpty()) { return; }

        this.array_size = capacity;
        Item[] copy = (Item[]) new Object[this.array_size];

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
            throw new java.lang.UnsupportedOperationException("This operation is not supported by this class");
        }
    }
}