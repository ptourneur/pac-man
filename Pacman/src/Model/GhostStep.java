package Model;

public class GhostStep {

    private int x;

    private int y;

    private double probability=0.0;
    private Direction direction;

    public double getProbability() {
        return probability;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public GhostStep(int x, int y, double probability,Direction direction) {
        this.x = x;
        this.y = y;
        this.probability = probability;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
