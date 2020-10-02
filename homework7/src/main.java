import java.io.*;
import java.util.Scanner;

public class main {
    private final static char LIVE = '☻';
    private final static char DEAD = ' ';

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter file name for read properties");
        String fileName = scanner.nextLine();
        System.out.println("Please enter max step count");
        int stepCount = scanner.nextInt();

        try {
            Game game = new Game(LIVE, DEAD, fileName);
            game.setMaxStepCount(stepCount);
            while (game.isPlay()) {
                game.nextStep();
            }
            System.out.println(game.toString());
            game.writeToFileProperties("game_end.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The end game");

    }
}

