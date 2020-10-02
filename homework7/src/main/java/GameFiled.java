import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFiled {

    private static final char INCREASE_LIVE = 'o';
    private static final char INCREASE_DEAD = 't';

    private final char ALIVE;
    private final char DEAD;
    private final int SIZE;
    private final char[][] FIELD;
    private int stepCount;
    /**
     * Counter which check updated FIELD or no
     */
    private final AtomicInteger isMoveStepCount = new AtomicInteger();

    public GameFiled(char ALIVE, char DEAD, int SIZE) {
        this.ALIVE = ALIVE;
        this.DEAD = DEAD;
        this.SIZE = SIZE;
        FIELD = new char[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(FIELD[i], DEAD);
        }
    }

    public void setLive(int x, int y){
        FIELD[x][y] = ALIVE;
    }

    public void setDead(int x, int y){
        FIELD[x][y] = DEAD;
    }

    public boolean isIncreaseLive(int x, int y){
        return FIELD[x][y] == INCREASE_LIVE;
    }

    public boolean isIncreaseDead(int x, int y){
        return FIELD[x][y] == INCREASE_DEAD;
    }

    public boolean isAlive(int x, int y){
        return FIELD[x][y] != DEAD;
    }

    public int getIsMoveStepCount() {
        return isMoveStepCount.get();
    }

    public int getStepCount() {
        return stepCount;
    }

    public int oneStep(int startPosition, int endPosition){
        isMoveStepCount.set(-1);
        for (int i = startPosition; i < endPosition; i++) {
            for (int j = 0; j < SIZE; j++) {
                checkCurrentCell(i, j);
            }
        }
        return ++stepCount;
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
                if (!(j == 0 && i == 0)
                        && isAccept(x + i, y + j)
                        && (FIELD[x + i][y + j] == ALIVE || FIELD[x + i][y + j] == INCREASE_DEAD)) {
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
