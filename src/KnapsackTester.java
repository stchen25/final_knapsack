//import jdk.incubator.vector.*;


public class KnapsackTester {
    public static void main(String[] args) {
        final int WARMUP_ITERATIONS = 5;
        final int TEST_ITERATIONS = 10;
        int items = 1000;
        int capacity = 1000000;
        int[] values = new int[items + 1];
        int[] weights = new int[items + 1];
        values[0] = 0;
        weights[0] = 0;

        for (int i = 1; i < values.length; i++) {
            values[i] = (int) (Math.random() * 1000);
        }

        for (int i = 1; i < weights.length; i++) {
            weights[i] = (int) (Math.random() * 1000);
        }

        Knapsack ks = new Knapsack(values, weights, capacity);
        ParallelKnapsack pks = new ParallelKnapsack(values, weights, capacity);

        System.out.println("Performing Knapsack problem with " + items + " items and " + capacity + " capacity, with randomly generated values and weights between 1 and 1000");


        int x = ks.baselineKnapsack();
        int y = pks.optimizedKnapsack();
        System.out.println("Baseline test outcome: " + x + "\n Optimized test outcome: " + y);

        if (x == y){
            System.out.println("correctness test passed");

        }
        else{
            System.out.println("correctness test failed");
        }


        for (int i = 0; i < WARMUP_ITERATIONS;i++){
          pks.optimizedKnapsack();
          //System.out.println("done");
        }

        long start = System.nanoTime();
        for (int i = 0; i < TEST_ITERATIONS; i++) {
                pks.optimizedKnapsack();
        }
        long elapsedMS = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Optimized took " + elapsedMS + " ms");

        long start2 = System.nanoTime();
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            pks.baselineKnapsack();
        }
        long elapsedMS2 = (System.nanoTime() - start2) / 1_000_000;
        System.out.println("Baseline took " + elapsedMS2 + " ms");

    }
    }

