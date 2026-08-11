import jdk.incubator.vector.*;



public class KnapsackThread extends Thread implements Runnable{
    int start;
    int end;
    int[] weights;
    int row;
    int[] memo;
    int[] mbelow;
    int[] values;
    VectorSpecies<Integer> SPECIES;
    public KnapsackThread(int[] m, int[] mb, int[] w, int[] v, int i, int j, int r){
        memo = m;
        mbelow = mb;
        start = i;
        end = j;
        weights = w;
        row = r;
        values = v;
         SPECIES = IntVector.SPECIES_PREFERRED;

        }

    public void run(){
        //System.out.println(SPECIES.length());
        try {
//            for (int j = start; j < SPECIES.loopBound(end); j += SPECIES.length()) {
//
//                int[] cap = new int[SPECIES.length()];
//                int k = j;
//                for (int i = 0; i < cap.length; i++) {
//                    cap[i] = k;
//                    k++;
//                }
//                //System.out.println();
////                for (int i = 0; i < cap.length; i++){
////                    System.out.print(cap[i] + " ");
////                }
//
//                var capacities = IntVector.fromArray(SPECIES, cap, 0);
//
//                var rowWeights = IntVector.broadcast(SPECIES, weights[row]);
//
//
//
//                var rowValues = IntVector.broadcast(SPECIES, values[row]);
//
//
//                var memoJ = IntVector.fromArray(SPECIES, memo, j);
//
//
//                var memoBelowJ = IntVector.fromArray(SPECIES, mbelow, j);
//
//
//                var newMemos = memoBelowJ;
//
//
//                var mask = rowWeights.compare(VectorOperators.LT, capacities);
//
//                int[] prev = new int[SPECIES.length()];
//                var index = capacities.sub(rowWeights, mask);
//                for (int i = 0; i < prev.length; i++) {
//
//                    prev[i] = mbelow[index.lane(i)];
//                }
//
//                var previous = IntVector.fromArray(SPECIES, prev, 0);
//
//
//
//                var newHighest = rowValues.add(previous);
//
//
//
//
//                newMemos = newMemos.sub(newMemos, mask);
//
//
//
//                newMemos = newMemos.add(newHighest.max(memoJ), mask);
//
//
//                newMemos.intoArray(memo, j);
//

//                if (weights[row] <= j) {
//                    memo[j] = Math.max(values[row] + mbelow[j - weights[row]], memo[j]);
//
//                } else {
//                    memo[j] = mbelow[j];
//                }
                //   }

            for (int j = start; j < end; j++) {

                if (weights[row] <= j) {
                    memo[j] = Math.max(values[row] + mbelow[j - weights[row]], mbelow[j]);

                } else {
                    memo[j] = mbelow[j];
                }
            }
            }



        catch(Exception e) {
            //e.printStackTrace();
        }
        }
        }




