import java.util.Arrays;

public class Game {
    private char[][] field;
    private char live;
    private char dead;
    private char increaseLive = 'o';//born
    private char increaseDead = 't';//dead
    private int counter = 0;

    public Game(char live, char dead, int size) {
        this.live = live;
        this.dead = dead;
        this.field = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = dead;
            }
        }
    }


    public String toString() {
        fillWithLive();
        StringBuilder sb = new StringBuilder();
        for (char[] chars : field) {
            for (int f = 0; f < field.length; f++) {
                sb.append(chars[f] == increaseLive ? live : chars[f]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void fill(int[]... arg) {
        for (int[] ar : arg) {
            field[ar[0]][ar[1]] = live;
        }
    }

    public void fillWithLive() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == increaseLive) {
                    field[i][j] = live;
                } else if (field[i][j] == increaseDead) {
                    field[i][j] = dead;
                }
            }
        }
        counter = 0;
    }

    public boolean check(int x, int y) {
        int positive = 0;
        int negative = 0;
        if (counter == field.length * field.length) {
            fillWithLive();
        }
        if (isAccept(x + 1, y + 1) &&
                field[x + 1][y + 1] != increaseLive
                && field[x + 1][y + 1] != increaseDead) {
            if (field[x + 1][y + 1] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x, y + 1) &&
                field[x][y + 1] != increaseLive
                && field[x][y + 1] != increaseDead) {
            if (field[x][y + 1] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x + 1, y) &&
                field[x + 1][y] != increaseLive
                && field[x + 1][y] != increaseDead) {
            if (field[x + 1][y] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x - 1, y + 1) &&
                field[x - 1][y + 1] != increaseLive
                && field[x - 1][y + 1] != increaseDead) {
            if (field[x - 1][y + 1] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x - 1, y - 1) &&
                field[x - 1][y - 1] != increaseLive
                && field[x - 1][y - 1] != increaseDead) {
            if (field[x - 1][y - 1] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x + 1, y - 1) &&
                field[x + 1][y - 1] != increaseLive
                && field[x + 1][y - 1] != increaseDead) {
            if (field[x + 1][y - 1] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x - 1, y) &&
                field[x - 1][y] != increaseLive
                && field[x - 1][y] != increaseDead) {
            if (field[x - 1][y] == live) {
                ++positive;
            } else {
                negative++;
            }

        }
        if (isAccept(x, y - 1) &&
                field[x][y - 1] != increaseLive
                && field[x][y - 1] != increaseDead) {
            if (field[x][y - 1] == live) {
                ++positive;
            } else {
                negative++;
            }
        }
        counter++;
//        System.out.println("negative = " + negative);
//        System.out.println("positive = " + positive);
        if (positive == 3 && field[x][y] == dead) {
            field[x][y] = increaseLive;
//            System.out.println(toString());
            counter = 0;
            return true;
        }
        if ((positive < 2 || positive > 3) && field[x][y] == live) {
            field[x][y] = increaseDead;
//            System.out.println(toString());
            counter = 0;
            return true;
        }
        return false;
    }

    public boolean isAccept(int x, int y) {
        return x >= 0 && y >= 0 && y < field.length && x < field.length;
    }


}
