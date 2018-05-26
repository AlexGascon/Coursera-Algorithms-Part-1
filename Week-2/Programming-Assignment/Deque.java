/**
* Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization
* of a stack and a queue that supports adding and removing items from either the
* front or the back of the data structure. 
*/

/**
* Your deque implementation must support each deque operation (including 
* construction) in constant worst-case time. A deque containing n items must use
* at most 48n + 192 bytes of memory and use space proportional to the number of
* items currently in the deque. Additionally, your iterator implementation must
* support each operation (including construction) in constant worst-case time. 
*/

/**
* The fact that we are asked to use constant WORST-CASE time indicates that we 
* have to implement the Deque with linked-list, because an implementation with
* arrays will require extra time if we need to resize it. 
*/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    public Deque() {
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        // Equivalent to push

        if (item == null) throw new java.lang.IllegalArgumentException("The item can't be null");

        Node oldFirst = first;

        // Creating the new first Node
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        if (oldFirst != null) oldFirst.previous = first;
        if (last == null) last = first;

        count++;
    }

    public void addLast(Item item) {
        // Equivalent to enqueue

        if (item == null) throw new java.lang.IllegalArgumentException("The item can't be null");

        Node oldLast = last;

        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;

        if (isEmpty()) first = last;
        else oldLast.next = last;

        count++;
    }

    public Item removeFirst() {
        // Equivalent to dequeue

        if (isEmpty()) throw new java.util.NoSuchElementException("The deque is empty");

        Node oldFirst = first;
        first = oldFirst.next;

        if (isEmpty()) last = null;
        else first.previous = null;

        count--;

        return oldFirst.item;
    }

    public Item removeLast() {
        // Equivalent to pop

        if (isEmpty()) throw new java.util.NoSuchElementException("The deque is empty");

        Node oldLast = last;
        last = oldLast.previous;

        if (last == null) first = null;
        else last.next = null;

        count--;

        return oldLast.item;
    }

    public Iterator<Item> iterator() { return new DequeIterator(); }

    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();

        System.out.println("IS EMPTY: " + deck.isEmpty());

        for (int i = 0; i < 10; i++) {
            deck.addFirst(i);
            System.out.println("SIZE: " + deck.size());
            System.out.println("IS EMPTY: " + deck.isEmpty());
        }

        System.out.println("Elements 0-9 added. We should have seen 1 to 10 printed");

        for (Integer i : deck) {
            System.out.println(i);
        }

        System.out.println("Finished iterating over the iterator. Elements should appear from 9 to 0.");

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.removeLast());
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 removed. They should appear from 0 to 9.");

        for (int i = 0; i < 10; i++) {
            deck.addLast(i);
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 added.");

        for (Integer i : deck) {
            System.out.println(i);
        }

        System.out.println("Finished iterating over the iterator. Elements should appear from 0 to 9");

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.removeFirst());
            System.out.println("IS EMPTY: " + deck.isEmpty());
            System.out.println("Deck size: " + deck.size());
        }

        System.out.println("Elements 0-9 removed. Elements should appear from 0 to 9");

    }



    private class Node {
        Item item;
        Node next;
        Node previous;

        Node() {
            // Empty method
        }

        Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }

        Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("There are no more elements");

            Item item = current.item;
            current = current.next;

            return item;
        }

        public boolean hasNext() { return current != null; }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("This operation is not supported by this class");
        }
    }
}
