import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private static final char INCREASE_LIVE = 'o';
    private static final char INCREASE_DEAD = 't';

    private final char ALIVE;
    private final char DEAD;
    private int SIZE;
    private final char[][] FIELD;
    private List<Integer[]> startValues;

    /**
     * Max step count which user set in start game,
     * if not set, then game play to end.
     */
    private int maxStepCount = Integer.MAX_VALUE;

    /**
     * Step counter for stop game which max step equals stepCounter.
     */
    private int stepCounter;

    /**
     * Counter which check updated FIELD or no
     */
    private final AtomicInteger isMoveStepCount = new AtomicInteger();

    /**
     * Thread count in start game.
     */
    private int threadCount = 2;

    /**
     * First average time for up thread count.
     */
    private int previousAvgTime = 10000;

    /**
     * Current average time.
     */
    private int avgTime;

    /**
     * current time sum for average time.
     */
    private int timeSum;

    /**
     * Current step count with equals thread count,
     * for current average time
     */
    private int countForTime;

    /**
     * Counter for up or low thread count.
     * If this equals 2, then up thread count, else if -2 then will lower.
     */
    private int stepCountForUpdate;


    /**
     * Constructor by default, accept two chars for game.
     *
     * @param live
     * @param dead
     */
    public Game(char live, char dead, String fileName) throws IOException {
        this.ALIVE = live;
        this.DEAD = dead;
        if (!fileName.endsWith(".properties")) {
            throw new IllegalArgumentException("File not properties");
        }
        readFileWithProperties(new File(fileName));
        this.FIELD = new char[SIZE][SIZE];
        fill(startValues);
    }

    /**
     * Method check continue game or finish.
     *
     * @return
     */
    public boolean isPlay() {
        return stepCounter != maxStepCount && isMoveStepCount.get() >= 0;
    }

    /**
     * One step in the game. We check and fill in all the cells of these loops.
     * The game starts with two threads. After calculating the average time,
     * if the current average time is less than two steps in a row,
     * then the number of threads increases, otherwise,
     * if the current average value is more than two steps in a row, then the number of threads decreases.
     */
    public void nextStep() throws InterruptedException {
        stepCounter++;
        isMoveStepCount.set(-1);

        long beforeTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(() -> {
                nextForThread((FIELD.length / threadCount) * finalI, (FIELD.length / threadCount) * (finalI + 1));
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        long afterTime = System.currentTimeMillis();
        long currentTime = afterTime - beforeTime;
        fillWithLive();

        System.out.println("Thread count: " + threadCount);
        System.out.println("Current time: " + currentTime);

        countForTime++;
        timeSum += currentTime;
        avgTime = timeSum / countForTime;

        if (avgTime <= previousAvgTime) {
            stepCountForUpdate++;
        } else {
            stepCountForUpdate--;
        }

        if (stepCountForUpdate == 2) {
            threadCountUp();
            stepCountForUpdate = 0;

        } else if (stepCountForUpdate == -2) {
            threadCountDown();
            stepCountForUpdate = 0;
        }
    }

    /**
     * Thread count down.
     */
    private void threadCountDown() {
        for (int i = threadCount - 1; i > 0; i--) {
            if (FIELD.length % i == 0) {
                threadCount = i;
                break;
            }
        }
        previousAvgTime = avgTime;
        timeSum = 0;
        countForTime = 0;
    }


    /**
     * Thread count up.
     */
    private void threadCountUp() {
        for (int i = threadCount + 1; i < FIELD.length; i++) {
            if (FIELD.length % i == 0) {
                threadCount = i;
                break;
            }
        }
        previousAvgTime = avgTime;
        timeSum = 0;
        countForTime = 0;
    }

    /**
     * Next step for thread which go by his row.
     * Different thread couldn't go by same row.
     *
     * @param startPosition for thread
     * @param endPosition   for thread
     */
    private void nextForThread(int startPosition, int endPosition) {
        for (int i = startPosition; i < endPosition; i++) {
            for (int j = 0; j < FIELD.length; j++) {
                checkCurrentCell(i, j);
            }
        }
    }

    /**
     * nice output
     *
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : FIELD) {
            for (char current : chars) {
                sb.append(current);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Write to file end parameters game.
     *
     * @param fileName
     * @throws FileNotFoundException
     */
    public void writeToFileProperties(String fileName) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            Properties properties = new Properties();
            int x = 1;
            int y = 1;
            for (int i = 0; i < FIELD.length; i++) {
                for (int j = 0; j < FIELD.length; j++) {
                    if (FIELD[i][j] == ALIVE) {
                        properties.setProperty("x" + x++, String.valueOf(i));
                        properties.setProperty("y" + y++, String.valueOf(j));
                    }
                }
            }
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
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(FIELD[i], DEAD);
        }

        for (Integer[] ar : startValues) {
            FIELD[ar[0]][ar[1]] = ALIVE;
        }
    }

    /**
     * Current step fill all cell which filled INCREASE_LIVE and INCREASE_DEAD
     */
    private void fillWithLive() {
        for (int i = 0; i < FIELD.length; i++) {
            for (int j = 0; j < FIELD[i].length; j++) {
                if (FIELD[i][j] == INCREASE_LIVE) {
                    FIELD[i][j] = ALIVE;
                } else if (FIELD[i][j] == INCREASE_DEAD) {
                    FIELD[i][j] = DEAD;
                }
            }
        }
    }

    /**
     * Check all neighbors in array.
     * if current cell is empty and we it's has exactly 3 neighbors,
     * this cell will be alive in next step.
     * If current cell is  emty and it's has more then 3  neighbors or less then 2 neighbors.
     * this cell will be dead in next step.
     * If current cell is alive and has 3 or 2 neighbors,
     * this cell will be alive in next step.
     *
     * @param x
     * @param y
     */
    private void checkCurrentCell(int x, int y) {
        int positive = 0;
        int[] neighbors = {-1, 0, 1};
        for (int neighbor1 : neighbors) {
            for (int neighbor2 : neighbors) {
                if (!(neighbor2 == 0 && neighbor1 == 0) &&
                        isAccept(x + neighbor1, y + neighbor2)
                        &&
                        (FIELD[x + neighbor1][y + neighbor2] == ALIVE || FIELD[x + neighbor1][y + neighbor2] == INCREASE_DEAD)
                ) {
                    positive++;
                }
            }
        }

        if (positive == 3 && FIELD[x][y] == DEAD && isAccept(x, y)) {
            FIELD[x][y] = INCREASE_LIVE;
            isMoveStepCount.getAndIncrement();
            return;
        }

        if ((positive < 2 || positive > 3) && FIELD[x][y] == ALIVE && isAccept(x, y)) {
            FIELD[x][y] = INCREASE_DEAD;
            isMoveStepCount.getAndIncrement();
        }
    }

    /**
     * Check index from array on ArrayIndexOutOfBoundsException.
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isAccept(int x, int y) {
        return (x >= 0 && y >= 0 && y < FIELD.length && x < FIELD.length)
//                && (FIELD[x][y] == ALIVE || FIELD[x][y] == INCREASE_DEAD)
                ;
    }

}
