# final_knapsack

A Java project that solves the 0/1 knapsack problem with dynamic programming and measures how much faster the DP table can be filled when the work is spread across many threads and, where possible, SIMD lanes.

The classic DP builds a table of size items + 1 by capacity + 1, where each cell holds the best achievable value for the first i items under a capacity of j. Each row depends only on the row directly above it, so the cells within a single row can be computed independently. That is the property this project exploits.

## Contents

- src/Knapsack.java - single-threaded baseline solver, a straightforward nested-loop DP.
- src/ParallelKnapsack.java - optimized solver. It fills the same table, but splits each row along the capacity axis into fixed-size chunks and hands each chunk to a worker thread, joining all workers before starting the next row so the row-to-row dependency is preserved. Table initialization is vectorized with the incubator Vector API.
- src/KnapsackThread.java - worker thread that fills one slice of a DP row using the previous row.
- src/KnapsackThread2.java - alternative worker kept from experimenting with a fully vectorized inner loop.
- src/KnapsackTester.java - benchmark harness and correctness check.
- src/run-test.sh - SLURM batch script used to run the benchmark on a compute cluster.

## Benchmark

KnapsackTester generates 1000 items with random values and weights and a capacity of 1000000, which is on the order of a billion DP cells. It first verifies that the parallel solver returns the same optimal value as the baseline, then performs warmup iterations to let the JIT settle before timing 10 iterations of each solver and printing the elapsed milliseconds for both.

## Building and running

JDK 19 or newer is required, since the Vector API is still an incubator module.

```
cd src
javac --add-modules jdk.incubator.vector *.java
java --add-modules jdk.incubator.vector -Xmx256g KnapsackTester
```

The heap size and thread count should be tuned to the machine. run-test.sh shows the configuration used for the reported runs: 116 CPUs with hyperthreading, JDK 19 and a 256 GB heap.
