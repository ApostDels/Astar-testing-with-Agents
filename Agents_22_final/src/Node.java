public class Node {
    private final int x;
    private final int y;
    private Node parent;

    // Dirty = 0
    // Clean = 1
    // Exit = 2
    // Detector = 3
    // Cleaner = 4
    private int content;
    private int GN;
    private int HN;
    private int FN;

    public Node(int x, int y, int HN, int content) {
        this.x = x;
        this.y = y;
        this.HN = HN;
        this.GN = Integer.MAX_VALUE;
        this.FN = Integer.MAX_VALUE;
        this.content = content;
        this.parent = null;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isExit(){
        return this.x == 1 && this.y == 1;

    }

    public Node getParent() {
        return parent;
    }

    public void setFN() {
        this.FN = GN + HN;
    }

    public void setFN(int v) {
        this.FN = v;
    }

    public int getFN() {
        return this.FN;
    }

    public void setHN(int v) {
        this.HN =  v;
    }

    public void setGN(int GN) {
        this.GN = GN;
    }

    public int getGN() {
        return this.GN;
    }

    public void setExit() {
        this.content = 2;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public void setDetector() {
        this.content = 3;
    }

    public void setCleaner() {
        this.content = 4;
    }

    public int getContent() {
        return content;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", content=" + content +
                ", GN=" + GN +
                ", HN=" + HN +
                ", FN=" + FN +
                '}';
    }
}


