package Model;

import java.util.Random;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Pick a random value of the BaseColor enum.
     * @return a random BaseColor.
     */
    private final static Random random = new Random();
    public static Direction getRandomDirection() {
        return values()[Direction.random.nextInt(values().length)];
    }
}