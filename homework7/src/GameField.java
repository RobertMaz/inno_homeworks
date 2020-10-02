public interface GameField {
    void setLive(int x, int y);

    void setDead(int x, int y);

    boolean isIncreaseLive(int x, int y);

    boolean isIncreaseDead(int x, int y);

    boolean isAlive(int x, int y);

    void oneStep(int startPosition, int endPosition);
}
