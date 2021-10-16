import java.util.HashMap;
import java.util.List;

public class Mcts {
    private Tris tris;
    private int symul;

    //initializes a new Mcts, throwing exception if n<=0
    public Mcts(int n, Tris t) {
        if (n<=0) throw new IllegalArgumentException();
        tris = t;
        symul = n;
    }

    //returns the best move on tris with Monte Carlo tree search
    public int monteCarloTreeSearch() {
        List<Integer> moves = tris.getMoves();
        HashMap<Integer, Double> rates = new HashMap<>();
        if (moves.size()==0) return 0;
        int action = moves.get(0);

        for (int x: moves) {
            rates.put(x,winningRate(x));
            if (rates.get(action) > rates.get(x)) action=x;
        }

        return action;
    } 

    //calculates the winning rate (n_wins / n_simulaltion) for symul simulation
    private double winningRate(int action) {
        int value=0;
        Tris cp = tris.copy();
        cp.move(action);
        for (int i=0; i<symul; i++) {
            int k = simulation(cp);
            value +=k;
        } 

        return 1.0 * value / symul;
    }

    //simulates a random game of tic tac toe, returning the value of the final state (1: X wins, -1: O wins, 0: tie)
    private int simulation(Tris t) {
        Tris grid = t.copy();
        while (!grid.isOver()) {

            //select random action for player1
            int moveP1 = grid.randomAction();             
            grid.move(moveP1);

            if (grid.isOver()) break;

            //select random move for player 2
            int moveP2 = grid.randomAction();     
            grid.move(moveP2);
        }
        return grid.value();
    }

    //returns tris
    public Tris getTris() {
        return tris;
    }

    @Override
    public String toString() {
        return tris.toString() + "Number of simulation: " + symul + "\n";
    }
}
