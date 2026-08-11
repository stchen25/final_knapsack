public class Knapsack {
    int[][] memo;
    int[] values;
    int[] weights;
    int capacity;
    public Knapsack(int[] v, int[] w, int c){
        memo = new int[v.length + 1][c + 1];
        values = v;
        weights = w;
        capacity = c;
    }
    public int baselineKnapsack(){
        for (int i = 0; i < memo[0].length; i++){
            memo[0][i] = 0;
        }
        for (int i = 0; i < memo.length; i++){
            memo[i][0] = 0;
        }
        for (int i = 1; i < values.length; i++){
            for (int j = 0; j < capacity; j++){
                if (weights[i] <= j){
                    memo[i][j] = Math.max(values[i] + memo[i - 1][j - weights[i]], memo[i - 1][j]);

                }
                else{
                    memo[i][j] = memo[i-1][j];
                }
            }
        }
        return memo[values.length - 1][capacity - 1];
    }
}
