public class main {
    public static void main(String[] args) {
        Game game = new Game('_', 'X', 5);
        game.fill(new int[]{1,1}, new int[]{2,2}, new int[]{3,2},new int[]{1,3}, new int[]{2, 3});
        System.out.println(game.toString());
        for (int i = 0; i < 5; i++) {
            if (step(game)){
                System.out.println(game.toString());
                break;
            }
        }
    }

    private static boolean step(Game game) {
        for (int i = 0; i < 5 ; i--) {
            for (int j = 0; j < 5; j--) {
                return game.check(i, j);
            }
        }
        return false;
    }
}
