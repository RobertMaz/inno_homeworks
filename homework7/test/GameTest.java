


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;


class GameTest {
    char LIVE = '☻';
    char DEAD = ' ';
    String fileName = "data.properties";

    @Test
    public void oneThread() throws IOException, InterruptedException {
        Game game = new Game(LIVE, DEAD, fileName);
        game.setThreadCount(1);
        long allTime = 0;
        while (game.isPlay()) {
            allTime += game.nextStep();
        }
        System.out.println("One thread time : " + allTime);
        Properties firstGame = game.getLiveCellsPosition();

        Game game2 = new Game(LIVE, DEAD, fileName);
        game2.setThreadCount(4);
        long allTime2 = 0;
        while (game2.isPlay()) {
            allTime2 += game2.nextStep();
        }

        System.out.println("Multithreading time : " + allTime2);
        Properties secondGame = game2.getLiveCellsPosition();
        Assertions.assertEquals(firstGame, secondGame);
    }
}