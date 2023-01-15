import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Game.printGridContent();
//        System.out.println(game);

        Game.aStar(Game.grid[0][2], Game.grid[3][1]).forEach(out::println);
        Game.aStar(Game.grid[1][2], Game.grid[2][2]).forEach(out::println);
//        out.println();
//        Game.softReset();
//        Game.aStar(Game.grid[5][0]).forEach(out::println);
    }
}