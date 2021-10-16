import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Tris tris = new Tris();
        int el;

        while (!tris.isOver()) {
            //get a legal move
            el = s.nextInt();
            while (!tris.isLegal(el)) {
                System.out.println("Illegal move, try another one\n");
                el = s.nextInt();
            }

            tris.move(el);
            System.out.println(tris.toString());

            if (tris.isOver()) break;
            tris.move(new Mcts(1000, tris).monteCarloTreeSearch());
            System.out.println(tris.toString());            
        }
        String st =tris.winner()=='.' ? "It's a tie" : "Winner: " + tris.winner();
        System.out.println(st);
        s.close();
    }
}