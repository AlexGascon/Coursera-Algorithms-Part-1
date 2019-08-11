# Programming assignment 1 - Percolation
## Percolation
Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

## The model
We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

![percolates](https://coursera.cs.princeton.edu/algs4/assignments/percolation/percolates-yes.png)
![not-percolates](https://coursera.cs.princeton.edu/algs4/assignments/percolation/percolates-no.png)

## The problem
In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1, the system percolates. The plots below show the site vacancy probability p versus the percolation probability for 20-by-20 random grid (left) and 100-by-100 random grid (right)
![20x20 grid](https://coursera.cs.princeton.edu/algs4/assignments/percolation/percolation-threshold20.png)
![100x100 grid](https://coursera.cs.princeton.edu/algs4/assignments/percolation/percolation-threshold100.png)

When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates, and when p > p*, a random n-by-n grid almost always percolates. No mathematical solution for determining the percolation threshold p* has yet been derived. Your task is to write a computer program to estimate p*. 

## Percolation data type
To model a percolation system, create a data type Percolation with the following API:

```java
public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)

    // is the site (row, col) full?
    public boolean isFull(int row, int col)

    // returns the number of open sites
    public int numberOfOpenSites()

    // does the system percolate?
    public boolean percolates()

    // test client (optional)
    public static void main(String[] args)
}
```

#### Corner cases
By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site: Throw a `java.lang.IllegalArgumentException` if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0

#### Performance requirements
The constructor should take time proportional to n^2; all methods should take constant time plus a constant number of calls to the union–find methods union(), find(), connected(), and count(). 

## Monte Carlo simulation
To estimate the percolation threshold, consider the following computational experiment:

* Initialize all sites to be blocked.
* Repeat the following until the system percolates:
    * Choose a site uniformly at random among all blocked sites.
    * Open the site. 
    * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened. 

By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let x_t be the fraction of open sites in computational experiment t. The sample mean x¯ provides an estimate of the percolation threshold; the sample standard deviation s; measures the sharpness of the threshold. 

Assuming T is sufficiently large (say, at least 30), the following provides a 95% confidence interval for the percolation threshold:

*Lower limit of the confidence interval*: `x- - (1.96*s)/sqrt(T)`
*Higher limit of the confidence interval*: `x- + (1.96*s)/sqrt(T)`

To perform a series of computational experiments, create a data type `PercolationStats` with the following API

```java
public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)

    // sample mean of percolation threshold
    public double mean()

    // sample standard deviation of percolation threshold
    public double stddev()

    // low endpoint of 95% confidence interval
    public double confidenceLo()

    // high endpoint of 95% confidence interval
    public double confidenceHi()

   // test client (see below)
   public static void main(String[] args)

}
```

The constructor should throw a `java.lang.IllegalArgumentException` if either n ≤ 0 or trials ≤ 0.

Also, include a `main()` method that takes two command-line arguments n and T, performs T independent computational experiments (discussed above) on an n-by-n grid, and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold. Use `StdRandom` to generate random numbers; use `StdStats` to compute the sample mean and sample standard deviation. 

Now, implement the Percolation data type using the weighted quick union algorithm in WeightedQuickUnionUF. Answer the questions in the previous paragraph.

## Web submission
Submit a .zip file containing only `Percolation.java` (using the weighted quick-union algorithm from `WeightedQuickUnionUF`) and `PercolationStats.java`. We will supply algs4.jar. Your submission may not call library functions except those in `StdIn`, `StdOut`, `StdRandom`, `StdStats`, `WeightedQuickUnionUF`, and `java.lang`.


