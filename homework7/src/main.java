import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

public class main {
    private final static char LIVE = '☻';
    private final static char DEAD = ' ';

    public static void main(String[] args) throws InterruptedException {


        Game game = new Game(LIVE, DEAD);
        System.out.println(game.toString());

        while (game.isPlay()) {
            game.nextStep();
            Thread.sleep(150);
            System.out.println(game.toString());
        }

        System.out.println("The end game");

        try {
            game.writeToFileProperties("game_end.properties");
        } catch (FileNotFoundException e) {
            System.out.println("Write file not found");
        }

//            Thread play = new Thread(game);
//            while (game.isPlay()) {
//                play.start();
//                Thread.sleep(150);
//                play.join();
//                System.out.println(game.toString());
//            }



    }
}

