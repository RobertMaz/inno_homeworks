import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    /**
     * Start position for game.
     */
    private List<Integer[]> startValues;

    /**
     * Max step count which user set in start game,
     * if not set, then game play to end.
     */
    private int maxStepCount = Integer.MAX_VALUE;

    GameFiled gameFiled;


    private int threadCount = 1;

    private int SIZE;

    /**
     * Constructor by default, accept two chars for game.
     *
     * @param live
     * @param dead
     */
    public Game(char live, char dead, String fileName) throws IOException {
        if (!fileName.endsWith(".properties")) {
            throw new IllegalArgumentException("File not properties");
        }
        readFileWithProperties(new File(fileName));
        gameFiled = new GameFiled(live, dead, SIZE);
        fill(startValues);
    }

    /**
     * Method check continue game or finish.
     *
     * @return
     */
    public boolean isPlay() {
        return gameFiled.getStepCount() != maxStepCount && gameFiled.getIsMoveStepCount() >= 0;
    }

    /**
     * One step in the game. We check and fill in all the cells of these loops.
     * The game starts with two threads. After calculating the average time,
     * if the current average time is less than two steps in a row,
     * then the number of threads increases, otherwise,
     * if the current average value is more than two steps in a row, then the number of threads decreases.
     * return current step time.
     */
    public long nextStep() throws InterruptedException {

        long beforeTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(() -> {
                gameFiled.oneStep((SIZE / threadCount) * finalI, (SIZE / threadCount) * (finalI + 1));
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        long afterTime = System.currentTimeMillis();
        long currentTime = afterTime - beforeTime;
        fillWithLive();
        return currentTime;

    }

    /**
     * Write to file end parameters game.
     *
     * @param fileName
     * @throws FileNotFoundException
     */
    public void writeToFileProperties(String fileName) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            Properties properties = getLiveCellsPosition();
            properties.list(printWriter);
        }
    }

    /**
     * Set max step count
     *
     * @param maxStepCount
     */
    public void setMaxStepCount(int maxStepCount) {
        this.maxStepCount = maxStepCount;
    }

    /**
     * Read file with properties, and save parameters for fill array.
     *
     * @param file properties
     */
    private void readFileWithProperties(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        SIZE = Integer.parseInt(properties.getProperty("size"));
        startValues = new ArrayList<>();
        for (int i = 1; i < properties.size() / 2 + 1; i++) {
            int x = Integer.parseInt(properties.getProperty("x" + i));
            int y = Integer.parseInt(properties.getProperty("y" + i));
            startValues.add(new Integer[]{x, y});
        }

    }

    /**
     * Fill array given parameters in start game
     *
     * @param arg arguments from file
     */
    private void fill(List<Integer[]> arg) {
        for (Integer[] ar : startValues) {
            gameFiled.setLive(ar[0], ar[1]);
        }
    }

    /**
     * Current step fill all cell which filled INCREASE_LIVE and INCREASE_DEAD
     */
    private void fillWithLive() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameFiled.isIncreaseLive(i, j)) {
                    gameFiled.setLive(i, j);
                } else if (gameFiled.isIncreaseDead(i, j)) {
                    gameFiled.setDead(i, j);
                }
            }
        }
    }


    @Override
    public String toString() {
        return gameFiled.toString();
    }

    public void setThreadCount(int threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count couldn't lower 1");
        }
        this.threadCount = threadCount;
    }


    public Properties getLiveCellsPosition() {
        Properties properties = new Properties();
        int x = 1;
        int y = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameFiled.isAlive(i, j)) {
                    properties.setProperty("x" + x++, String.valueOf(i));
                    properties.setProperty("y" + y++, String.valueOf(j));
                }
            }
        }
        return properties;
    }
}
