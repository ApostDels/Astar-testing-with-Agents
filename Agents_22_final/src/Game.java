import java.util.*;

import static java.lang.System.out;

public class Game {
    public static final int SIZE = 6;
    public static final Random PRNG = new Random();
    public static final Node[][] grid = new Node[SIZE][SIZE];
    private static Cleaner cleaner;
    private static Detector detector;

    public static Node Exit;




    public Game() {
        initGrid();
    }





    public static void initGrid() {

        // Set Random clean or dirty grid cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Node(i, j, manhattanDistance(i, j), dirtyOrClean());
            }
        }

        // mark [1, 1] as EXIT
        grid[1][1].setExit();

        Exit=(grid[1][1]);
        placeCleaner();

        placeDetector();
    }

    private static int dirtyOrClean() {
        // return DIRTY = 0 or CLEAN = 1
        return PRNG.nextInt(2);
    }

    private static void placeCleaner() {
        int x, y;

        // find x, y that are not EXIT
        do {
            x = PRNG.nextInt(SIZE);
            y = PRNG.nextInt(SIZE);
        } while (x == 1 && y == 1);

        grid[x][y].setCleaner();
        Cleaner.position = grid[x][y];
        Cleaner.energy = (int) Math.pow(SIZE, 2) - SIZE ;//grid[x][y], (int) Math.pow(SIZE, 2) - SIZE
        System.out.println("Cleaner's position ["+x+"]"+"["+y+"]");
    }

    private static void placeDetector() {
        int x, y;

        // find x, y that are not EXIT or CLEANER
        do {
            x = PRNG.nextInt(SIZE);
            y = PRNG.nextInt(SIZE);
        } while ((x == 1 && y == 1) || (x == cleaner.getX() && y != cleaner.getY()));

        grid[x][y].setDetector();
        Detector.position = grid[x][y];
        Detector.energy = (int) Math.pow(SIZE, 2); //grid[x][y], (int) Math.pow(SIZE, 2)

        System.out.println("Detector's position ["+x+"]"+"["+y+"]");
    }

    // x2 and y2 are always 1 since EXIT is at [1, 1]
    private static int manhattanDistance(int x, int y) {
        return Math.abs(1 - x) + Math.abs(1 - y);
    }

    private static int manhattanDistance(Node current, Node end) {
        return Math.abs(end.getX() - current.getX()) + Math.abs(end.getY() - current.getY());
    }

    private static List<Node> getSolutionPath(Node node) {
        List<Node> path = new ArrayList<>();
        Node current = node;

        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);

        out.println("solution");
        return path;
    }

    private static List<Node> getNeighbours(Node node) {
        List<Node> neighbours = new ArrayList<>();
        Node topNeighbour, botNeighbour, leftNeighbour, rightNeighbour;

        if (node.getX() - 1 >= 0) {
            topNeighbour = grid[node.getX() - 1][node.getY()];
            neighbours.add(topNeighbour);
        }
        if (node.getX() + 1 < SIZE) {
            botNeighbour = grid[node.getX() + 1][node.getY()];
            neighbours.add(botNeighbour);
        }
        if (node.getY() - 1 >= 0) {
            leftNeighbour = grid[node.getX()][node.getY() - 1];
            neighbours.add(leftNeighbour);
        }
        if (node.getY() + 1 < SIZE) {
            rightNeighbour = grid[node.getX()][node.getY() + 1];
            neighbours.add(rightNeighbour);
        }

        return neighbours;
    }

    public static void setAllHN(Node end) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j].setHN(manhattanDistance(grid[i][j], end));
            }
        }
    }

    // https://en.wikipedia.org/wiki/A*_search_algorithm
    public static List<Node> aStar(Node start, Node end) {
        softReset();
        setAllHN(end);

        Queue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFN));
        start.setGN(0);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.peek();

            if (current.getX() == end.getX() && current.getY() == end.getY()) {
                return getSolutionPath(current);
            }
            current = openSet.remove();

            List<Node> neighbours = getNeighbours(current);
            for (Node neighbour : neighbours) {
                int tentativeGN = current.getGN() + 1; // energy cost per move is always 1

                if (tentativeGN < neighbour.getGN()) {
                    neighbour.setParent(current);
                    neighbour.setGN(tentativeGN);
                    neighbour.setHN(manhattanDistance(neighbour, end));
                    neighbour.setFN();
                    if (!openSet.contains(neighbour))
                        openSet.add(neighbour);
                }
            }
        }

        return new ArrayList<>();
    }

    public static void softReset() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j].setParent(null);
                grid[i][j].setFN(Integer.MAX_VALUE);
                grid[i][j].setGN(Integer.MAX_VALUE);
            }
        }
    }

    public static void printGridContent() {
        String ANSI_PURPLE = "\033[0;35m";
        String ANSI_RED = "\033[0;31m";
        String ANSI_YELLOW = "\033[0;33m";
        String ANSI_GREEN = "\u001B[32m";

        String ANSI_RESET = "\u001B[0m";

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                switch (grid[i][j].getContent()) {
                    case 0 -> out.print(ANSI_YELLOW + "[*]" + ANSI_RESET);
                    case 1 -> out.print("[ ]");
                    case 2 -> out.print(ANSI_PURPLE + "[E]" + ANSI_RESET);
                    case 3 -> out.print(ANSI_RED + "[D]" + ANSI_RESET);
                    case 4 -> out.print(ANSI_GREEN + "[C]" + ANSI_RESET);
                    case 30 -> out.print(ANSI_RED + "[D" + ANSI_RESET + ANSI_YELLOW + " *]" + ANSI_RESET);
                    case 34 -> out.print(ANSI_GREEN + "[C" + ANSI_RESET + ANSI_RED + " D]" + ANSI_RESET);
                    case 40 -> out.print(ANSI_GREEN + "[C" + ANSI_RESET + ANSI_YELLOW + " *]" + ANSI_RESET);
                }
            }
            out.println();
        }
        out.println();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Game{\n");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                output.append('\t')
                        .append(grid[i][j].toString())
                        .append('\n');
            }
        }

        output.append("}");

        return output.toString();
    }
}

