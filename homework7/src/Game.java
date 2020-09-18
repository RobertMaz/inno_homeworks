import java.io.*;
import java.util.*;

public class Game implements Runnable {
    private final char[][] FIELD;
    private final char ALIVE;
    private final char DEAD;
    private final char INCREASE_LIVE = 'o';//will born next step
    private final char INCREASE_DEAD = 't';//will dead next step
    private int counter;
    private int stepCounter;
    private int maxStepCount;
    private int SIZE;
    private List<Integer[]> startValues;
    private int isMoveStepCount;

    /**
     * Constructor by default, accept two chars for game.
     *
     * @param live
     * @param dead
     */
    public Game(char live, char dead) {
        readSettings();
        this.ALIVE = live;
        this.DEAD = dead;
        this.FIELD = new char[SIZE][SIZE];
        fill(startValues);
    }

    /**
     * Method check continue game or finish.
     *
     * @return
     */
    public boolean isPlay() {
        return stepCounter != maxStepCount && isMoveStepCount >= 0;
    }

    /**
     * One step in game. Check and fill all cell in these loops.
     */
    public void nextStep() {
        isMoveStepCount = -1;
        for (int i = 0; i < FIELD.length; i++) {
            for (int j = 0; j < FIELD.length; j++) {
                checkCurrentCell(i, j);
            }
        }
        stepCounter++;
    }

    /**
     * For multithreading. I don't know how it do, and why.
     * I thought, step start in some thread, by main thread sleep,
     * but thread not start repeat in loop.
     */
    @Override
    public void run() {
        nextStep();
    }

    /**
     * nice output
     *
     * @return
     */
    public String toString() {
        fillWithLive();
        StringBuilder sb = new StringBuilder();
        for (char[] chars : FIELD) {
            for (int i = 0; i < FIELD.length; i++) {
                sb.append("____");
            }
            sb.append("\n");
            for (int f = 0; f < FIELD.length; f++) {
                sb.append(chars[f] == INCREASE_LIVE ? ALIVE : chars[f]).append(" ").append("|").append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Write to file end parameters game.
     * @param fileName
     * @throws FileNotFoundException
     */
    public void writeToFileProperties(String fileName) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            Properties properties = new Properties();
            int x = 1;
            int y = 2;
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
     * Settings from start game.
     * FileName - enter from console
     * max step count - enter from console
     */
    private void readSettings() {
        try (Scanner scanner = new Scanner(System.in)) {
            File file;
            String fileName;
            do {
                System.out.println("Please enter file name with properties");
                fileName = scanner.nextLine().trim();
                file = new File(fileName);

                try {
                    readFileWithProperties(file);
                    break;
                } catch (IOException e) {
                    continue;
                }
            }
            while (!file.isFile() && !fileName.endsWith(".properties"));


            System.out.println("Enter steps count");
            while (true) {
                try {
                    maxStepCount = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Entered illegal argument, please try again");
                }
            }
        }
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
     * Fill array given parameters
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
        counter = 0;
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

        if (counter == FIELD.length * FIELD.length) {
            fillWithLive();
        }

        if (isAccept(x + 1, y + 1)
                && (FIELD[x + 1][y + 1] == ALIVE || FIELD[x + 1][y + 1] == INCREASE_DEAD)) {
            positive++;
        }
        if (isAccept(x, y + 1)
                && (FIELD[x][y + 1] == ALIVE || FIELD[x][y + 1] == INCREASE_DEAD)
        ) {
            positive++;
        }
        if (isAccept(x + 1, y)
                && (FIELD[x + 1][y] == ALIVE || FIELD[x + 1][y] == INCREASE_DEAD)
        ) {
            positive++;
        }
        if (isAccept(x - 1, y + 1)
                && (FIELD[x - 1][y + 1] == ALIVE || FIELD[x - 1][y + 1] == INCREASE_DEAD)
        ) {
            positive++;
        }
        if (isAccept(x - 1, y - 1)
                && (FIELD[x - 1][y - 1] == ALIVE || FIELD[x - 1][y - 1] == INCREASE_DEAD)
        ) {
            positive++;
        }
        if (isAccept(x + 1, y - 1)
                && (FIELD[x + 1][y - 1] == ALIVE || FIELD[x + 1][y - 1] == INCREASE_DEAD)
        ) {
            positive++;
        }
        if (isAccept(x - 1, y)
                && (FIELD[x - 1][y] == ALIVE || FIELD[x - 1][y] == INCREASE_DEAD)) {
            positive++;
        }
        if (isAccept(x, y - 1)
                && (FIELD[x][y - 1] == ALIVE || FIELD[x][y - 1] == INCREASE_DEAD)) {
            positive++;
        }
        counter++;

        if (positive == 3 && FIELD[x][y] == DEAD && isAccept(x, y)) {
            FIELD[x][y] = INCREASE_LIVE;
            isMoveStepCount++;
            return;
        }

        if ((positive < 2 || positive > 3) && FIELD[x][y] == ALIVE && isAccept(x, y)) {
            FIELD[x][y] = INCREASE_DEAD;
            isMoveStepCount++;
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
        return (x >= 0 && y >= 0 && y < FIELD.length && x < FIELD.length);
//                && (FIELD[x][y] == ALIVE || FIELD[x][y] == INCREASE_DEAD)
    }

}
