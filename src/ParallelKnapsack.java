import jdk.incubator.vector.*;
//import jdk.incubator.vector.FloatVector;
//import jdk.incubator.vector.*;

import java.util.ArrayList;

public class ParallelKnapsack {
    int[][] memo;
    int[] values;
    int[] weights;
    int capacity;
    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;

    public ParallelKnapsack(int[] v, int[] w, int c) {
        memo = new int[v.length + 1][c + 1];

        values = v;
        weights = w;
        capacity = c;
    }

    public int baselineKnapsack() {
        for (int i = 0; i < memo[0].length; i++) {

            memo[0][i] = 0;
        }
        for (int i = 0; i < memo.length; i++) {
            memo[i][0] = 0;
        }
        for (int i = 1; i < values.length; i++) {
            for (int j = 0; j < capacity; j++) {
                if (weights[i] <= j) {
                    memo[i][j] = Math.max(values[i] + memo[i - 1][j - weights[i]], memo[i - 1][j]);

                } else {
                    memo[i][j] = memo[i - 1][j];
                }
            }
        }
        return memo[values.length - 1][capacity - 1];
    }

    public int optimizedKnapsack() {
        int step = 100000;

        for (int i = 0; i < SPECIES.loopBound(memo[0].length); i += SPECIES.length()) {
            var arr = IntVector.zero(SPECIES);
           arr.intoArray(memo[0], i);
            //memo[0][i] = 0;
        }
        for (int i = 0; i < memo.length; i++) {
            memo[i][0] = 0;
        }
        for (int i = 1; i < values.length; i++) {
            ArrayList<Thread> thread = new ArrayList<>();
            for (int j = 0; j < capacity; j += step) {

                thread.add(new KnapsackThread(memo[i], memo[i-1],weights, values, j, j + step - 1, i));
                //thread.add(new KnapsackThread2(memo, weights, values, j, j + step - 1, i));


            }
            for (Thread t : thread){
                t.start();
            }
            for (Thread t : thread) {
                try {
                    t.join();
                }
                catch (InterruptedException ignored) {
                    // don't care if t was interrupted
                }
            }

        }

//        for (int i = 0; i < memo.length; i++){
//            for (int j = 0; j < memo[0].length; j++){
//                System.out.print(memo[i][j] + " ");
//            }
//            System.out.println();
//        }
        return memo[values.length - 1][capacity - 2];
        //return memo[values.length - 1][capacity - 16];
    }
}


