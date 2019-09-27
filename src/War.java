import java.io.File;
import java.util.Scanner;

public class War {

    public static void main(String[] args) throws Exception{
        Scanner file = new Scanner(new File("input.dat"));

        while (file.hasNext()){
            String[] p1Hand = file.nextLine().split(" ");
            String[] p2Hand = file.nextLine().split(" ");
            RingBuffer<Integer> p1 = new RingBuffer<>(52);
            RingBuffer<Integer> p2 = new RingBuffer<>(52);
            RingBuffer<Integer> table = new RingBuffer<>(52);
            String order = "  23456789TJQKA";

            int hand = 0;

            for (int i = 0; i < p1Hand.length; i++) {
                p1.enqueue(order.indexOf(p1Hand[i].substring(0,1)));
            }

            for (int i = 0; i < p2Hand.length; i++) {
                p2.enqueue(order.indexOf(p2Hand[i].substring(0,1)));
            }
            try {

                for (int i = 0; i < 100000; i++) {
                    hand(p1, p2, table).enqueue(table);

                    if (i >= 100000 - 1) {
                        System.out.println("Tie game stopped at 100000 plays.");
                    }

                    hand = i;
                }
            }
            catch(IllegalStateException e){
                    if (p1.isEmpty() && p2.isFull()) {
                        System.out.println("Player 2 wins!");
                    }
                    else if(p1.isFull() && p2.isEmpty()) {
                        System.out.println("Player 1 wins!");
                    }
                    else System.out.println("Something went wrong");
            }
        }
    }

    public static RingBuffer<Integer> hand(RingBuffer<Integer> r1, RingBuffer<Integer> r2,  RingBuffer<Integer> game) throws Exception{
        int a = r1.dequeue();
        int b = r2.dequeue();
        game.enqueue(a);
        game.enqueue(b);

        if (a > b) {
            return r1;
        } else if (a < b) {
            return r2;
        }
        else{
            return war(r1, r2, game);
        }
    }

    public static RingBuffer<Integer> war(RingBuffer<Integer> r1, RingBuffer<Integer> r2,  RingBuffer<Integer> game) throws Exception {
        game.enqueue(r1.dequeue());
        game.enqueue(r2.dequeue());

        return hand(r1, r2, game);
    }

}
