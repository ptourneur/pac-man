package Model;

import java.util.Random;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Pick a random value of the Direction enum.
     * @return a random Direction.
     */
    private final static Random random = new Random();
    public static Direction getRandomDirection() {
        return values()[Direction.random.nextInt(values().length)];
    }
}