import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    char LIVE = '☻';
    char DEAD = ' ';

    @Test
    public void oneThread() throws IOException, InterruptedException {
        Game game = new Game(LIVE, DEAD, "C:\\Users\\user\\IdeaProjects\\inno_homeworks\\data.properties");
        game.setThreadCount(1);
        long allTime = 0;
        while (game.isPlay()) {
            allTime += game.nextStep();
        }
        System.out.println(allTime);
        Properties firstGame = game.getLiveCellsPosition();

        Game game2 = new Game(LIVE, DEAD, "C:\\Users\\user\\IdeaProjects\\inno_homeworks\\data.properties");
        game2.setThreadCount(10);
        long allTime2 = 0;
        while (game2.isPlay()) {
            allTime2 += game2.nextStep();
        }
        System.out.println(allTime2);
        Properties secondGame = game2.getLiveCellsPosition();
        Assertions.assertEquals(firstGame, secondGame);
    }

    @Test
    void fourThread() throws InterruptedException, IOException {

    }
}