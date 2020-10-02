import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFiledImpl  implements  GameField{

    /**
     * Cell which will born on next step.
     */
    private static final char INCREASE_LIVE = 'o';

    /**
     * Cell which will dead on next step.
     */
    private static final char INCREASE_DEAD = 't';

    /**
     * Cell which live.
     */
    private final char ALIVE;

    /**
     * Cell where nobody not live.
     */
    private final char DEAD;

    private final int SIZE;
    private final char[][] FIELD;

    /**
     * Counter which check updated FIELD or no
     */
    private final AtomicInteger isMoveStepCount = new AtomicInteger();

    /**
     * When make game field, set alive and dead char, and field size.
     * And fill all cell how dead.
     */
    public GameFiledImpl(char ALIVE, char DEAD, int SIZE) {
        this.ALIVE = ALIVE;
        this.DEAD = DEAD;
        this.SIZE = SIZE;
        FIELD = new char[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(FIELD[i], DEAD);
        }
    }

    /**
     * Set cell live
     */
    public void setLive(int x, int y) {
        FIELD[x][y] = ALIVE;
    }

    /**
     * Set cell dead
     */
    public void setDead(int x, int y) {
        FIELD[x][y] = DEAD;
    }

    /**
     * Checking the cell that is born in the next step
     */
    public boolean isIncreaseLive(int x, int y) {
        return FIELD[x][y] == INCREASE_LIVE;
    }

    /**
     * Checking the cell that is dead in the next step
     */
    public boolean isIncreaseDead(int x, int y) {
        return FIELD[x][y] == INCREASE_DEAD;
    }

    /**
     * Check cell is it live or not
     */
    public boolean isAlive(int x, int y) {
        return FIELD[x][y] != DEAD;
    }

    /**
     * Set -1 in start next step, for check movement
     */
    public void setIsMoveStepCount(int i) {
        isMoveStepCount.set(i);
    }

    /**
     * If this number = -1, then stop game.
     *
     * @return
     */
    public int getIsMoveStepCount() {
        return isMoveStepCount.get();
    }

    /**
     * One complete traversal along the line, and filling the mutable cells
     *
     * @param startPosition - for multithreading, for once thread go by one row
     * @param endPosition   - for multithreading, for once thread go by one row
     */
    public void oneStep(int startPosition, int endPosition) {
        for (int i = startPosition; i < endPosition; i++) {
            for (int j = 0; j < SIZE; j++) {
                checkCurrentCell(i, j);
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
        for (int i : neighbors) {
            for (int j : neighbors) {
                if (j == 0 && i == 0) {
                    continue;
                }
                if (isAccept(x + i, y + j)
                        && (FIELD[x + i][y + j] == ALIVE || FIELD[x + i][y + j] == INCREASE_DEAD))
                    positive++;
            }
        }

        if (isAccept(x, y)) {
            if (positive == 3 && FIELD[x][y] == DEAD) {
                FIELD[x][y] = INCREASE_LIVE;
                isMoveStepCount.getAndIncrement();
                return;
            }

            if ((positive < 2 || positive > 3) && FIELD[x][y] == ALIVE) {
                FIELD[x][y] = INCREASE_DEAD;
                isMoveStepCount.getAndIncrement();
            }
        }
    }

    /**
     * Check index from array on ArrayIndexOutOfBoundsException.
     */
    private boolean isAccept(int x, int y) {
        return (x >= 0 && y >= 0 && y < FIELD.length && x < FIELD.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FIELD.length; i++) {
            for (int j = 0; j < FIELD.length; j++) {
                sb.append(FIELD[i][j] == ALIVE ? ALIVE : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
