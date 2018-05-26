import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
/**
* Write a client program Permutation.java that takes an integer k as a 
* command-line argument; reads in a sequence of strings from standard input
* using StdIn.readString(); and prints exactly k of them, uniformly at random.
* Print each item from the sequence at most once. 
*/
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randQueue.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randQueue.dequeue());
        }

    }


}