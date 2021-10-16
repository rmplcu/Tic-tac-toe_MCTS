import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Tris {
    //CAMPI
    //grid representation as a list of lists
    private ArrayList<ArrayList<Character>> tris;
    //indicates the state of the game; true->game is over, false->game isn't over
    private boolean over;
    //indicates wich player has the turn (X or O)
    private char turn;
    //keeps track of all the legal moves
    private ArrayList<Integer> moves;
    //to create random values
    private Random rand;

    //Initializes an empty 3x3 grid 
    //              over as false
    //              turn as X
    //              moves as all the possible moves (0, 1, ... , 8)
    //              rand as a new instace of the class Random
    public Tris() {
        tris = new ArrayList<ArrayList<Character>>();
        moves = new ArrayList<Integer>();
        for (int i=0;i<3; i++) {
            ArrayList<Character> x = new ArrayList<Character>();
            for (int j=0;j<3; j++) {
                x.add('.');
            }
            tris.add(x);
        }

        for (int i=0; i<9; i++) {
            moves.add(i);
        }
        turn = 'X';
        over = false;
        rand = new Random();
    }

    //returns true if the game is over, false otherwise
    public boolean isOver() {
        winner();
        if (!over) {
            over = true;
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if (tris.get(i).get(j) == '.') {
                        over = false;
                        break;
                    }
                }
            }
        }

        return over;
    }

    //returns . if there's no winner 
    //        X if X is the winner (3 consecutive 'X' on the same row, column or diagonal)
    //        O if O is the winner (3 consecutive 'O' on the same row, column or diagonal)
    //Modifies win to true if there's a winner
    public char winner() {
        //row win?
        for (int i=0; i<3; i++) {
            if (tris.get(i).get(0).equals(tris.get(i).get(1)) && tris.get(i).get(0).equals(tris.get(i).get(2)) && !tris.get(i).get(0).equals('.') ) {
                over = true;
                return tris.get(i).get(0);
            }
        }

        //col win?
        for (int i=0;i<3;i++) {
            if (tris.get(0).get(i).equals(tris.get(1).get(i)) && tris.get(0).get(i).equals(tris.get(2).get(i)) && !tris.get(0).get(i).equals('.') ) {
                over=true;
                return tris.get(0).get(i);
            } 
        }

        //diag win?
        if (tris.get(0).get(0).equals(tris.get(1).get(1)) && tris.get(0).get(0).equals(tris.get(2).get(2)) && !tris.get(1).get(1).equals('.') ) {
            over = true;
            return  tris.get(1).get(1);
        }
        if (tris.get(0).get(2).equals(tris.get(1).get(1)) && tris.get(1).get(1).equals(tris.get(2).get(0)) && !tris.get(1).get(1).equals('.')) {
            over = true;
            return tris.get(1).get(1);
        }

        //no win
        return '.';
    } 

    //Check if move m is legal
    public boolean isLegal(int m) {
        return isLegal(m/3, m%3);
    }

    //Check if move is legal: if tris[row][col] is empty ( '.' ) the move is legal, throws exception if not legal
    public boolean isLegal(int row, int col) {
        if ( row >= 3 || row <0 || col>=3 || col<0 ) throw new IllegalArgumentException("Out of bounds");
        return tris.get(row).get(col).equals('.');
    }

    //returns all the legal moves 
    public List<Integer> getMoves() {
        return moves;
    }

    //returns the character representing the player who has the turn
    public char getTurn() {
        return turn;
    }

    //Makes a move for either player X or player O depending on the turn
    public void move(int m) {
        move(m/3, m%3);
    }

    //Makes a move for either player 'X' or player 'O', depending on the turn throwing exception if the move is not legal
    //tris[row][col] = 'X' if turn == 'X'; tris[row][col] = 'O' if turn == 'O'
    public void move(int row, int col) {
        if (!isLegal(row, col)) throw new IllegalArgumentException("Illegal move");
        
        tris.get(row).set(col, turn);
        moves.remove(moves.indexOf(row*3+col));
        turn = turn =='X' ? 'O' : 'X';
    }

    //Returns a random available action, if does not exist throws an exception
    public int randomAction() {
        if (isOver()) throw new IllegalArgumentException("Game is over: no moves available");
        return moves.get(rand.nextInt(moves.size()));
    }

    //returns 1 if player wins, -1 if player lose, 0 if it's a tie
    public int value() {
        char c = winner();
        if (c == 'X') return 1;
        if (c =='O') return -1;
        return 0;
    }

    //returns a copy of the grid
    public Tris copy() {
        Tris t = new Tris();
        for (int i=0; i<3; i++) {
            for (int j=0;j<3; j++) {
                t.tris.get(i).set(j, tris.get(i).get(j));
            }
        }
        t.moves = new ArrayList<>(moves);
        t.over = over;
        t.turn = turn;
        return t;
    }

    //returns a string representation of this 
    @Override
    public String toString() {
        String str = "";
        for (int i=0; i<3; i++) {
            str += tris.get(i).toString().charAt(1) + " | " + tris.get(i).toString().charAt(4) + " | " + tris.get(i).toString().charAt(7) + "\n";
        }
        str +="Moves: " +  moves.toString() + "\n";
        return str;
    }


}