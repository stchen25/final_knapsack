import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class KnapsackThread2 extends Thread implements Runnable{
    int start;
    int end;
    int[] weights;
    int row;
    int[][] memo;

    int[] values;
    VectorSpecies<Integer> SPECIES;
    public KnapsackThread2(int[][] m, int[] w, int[] v, int i, int j, int r){
        memo = m;
        start = i;
        end = j;
        weights = w;
        row = r;
        values = v;
        //SPECIES = IntVector.SPECIES_PREFERRED;

    }

    public void run(){
        //System.out.println(SPECIES.length());
        for (int j = start; j < end; j++){
            if (weights[row] <= j){
                memo[row][j] = Math.max(values[row] + memo[row - 1][j - weights[row]], memo[row - 1][j]);

            }
            else{
                memo[row][j] = memo[row-1][j];
            }
        }
    }

}
